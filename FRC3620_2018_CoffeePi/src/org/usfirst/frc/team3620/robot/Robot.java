/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3620.robot;

import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Preferences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.autonomous.AutonomousDescriptor;
import org.usfirst.frc.team3620.robot.autonomous.AutonomousDescriptorMaker;
import org.usfirst.frc.team3620.robot.autonomous.FakeCommand;
import org.usfirst.frc.team3620.robot.autonomous.WhereToPutCube;
import org.usfirst.frc.team3620.robot.commands.*;
import org.usfirst.frc.team3620.robot.paths.Path_BackUpFromScale;
import org.usfirst.frc.team3620.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.LightSubsystem;
import org.usfirst.frc3620.logger.DataLogger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.usfirst.frc3620.misc.AverageSendableChooser2018;
import org.usfirst.frc3620.misc.CANDeviceFinder;
import org.usfirst.frc3620.misc.RobotMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	// Team 3620 custom stuff
	static RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode;
	static Logger logger;
	public static DataLogger robotDataLogger;
	
	// subsystems
	public static ExampleSubsystem kExampleSubsystem;
	public static DriveSubsystem driveSubsystem;
	public static LightSubsystem lightSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static LiftSubsystem liftSubsystem;
	public static Preferences preferences;
	
	// non subsystem globals
	public static OperatorView operatorView;
	public static CANDeviceFinder canDeviceFinder;
	
	// OI
	public static OI m_oi;

	Command autonomousCommand;
	
	static AverageSendableChooser2018<String> posChooser = new AverageSendableChooser2018<>();
	static AverageSendableChooser2018<Boolean> trustChooser = new AverageSendableChooser2018<>();
	static AverageSendableChooser2018<Integer> delayChooser = new AverageSendableChooser2018<>();

	@Override
	protected void loopFunc()
	{
		try
		{
			// calls user code
			super.loopFunc();
		} // catch all the things
		catch(Throwable throwable)
		{
			DriverStation.reportError("Unhandled exception: " + throwable.toString(),
					throwable.getStackTrace());
			System.exit(1); // kill the program so it can restart
		}
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		preferences = Preferences.getInstance();
		// set up logging
		logger = EventLogging.getLogger(Robot.class, Level.INFO);

		// let's see what's on the CAN bus
		canDeviceFinder = new CANDeviceFinder();
		logger.info("CAN bus: {}", canDeviceFinder.getDeviceList());

		// initialize hardware
		RobotMap.init();

		// initialize subsystems
		kExampleSubsystem = new ExampleSubsystem();
		driveSubsystem = new DriveSubsystem();
		lightSubsystem = new LightSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		liftSubsystem = new LiftSubsystem();
		operatorView = new OperatorView();
		operatorView.operatorViewInit();

		// Initialize Operator Interface 
		m_oi = new OI(); 

		posChooser.addDefault("Left",  "L");
		posChooser.addObject("Center",  "C");
		posChooser.addObject("Right",  "R");
		SmartDashboard.putData ("Position chooser", posChooser);

		trustChooser.addDefault("Yes", true);
		trustChooser.addObject("No", false);
		SmartDashboard.putData ("trust chooser", trustChooser);
		
		delayChooser.addDefault("0", 0);
		delayChooser.addObject("1", 1);
		delayChooser.addObject("2", 2);
		delayChooser.addObject("3", 3);
		delayChooser.addObject("4", 4);
		delayChooser.addObject("5", 5);
		SmartDashboard.putData ("delay chooser", delayChooser);
		
		// start the thingy that keeps the operator console and the chooser in
		// sync
		new ControlPanelWatcher();

		// get data logging going
		robotDataLogger = new DataLogger();
		new RobotDataLoggingSetup(robotDataLogger, canDeviceFinder);
		robotDataLogger.setInterval(1.000);
		robotDataLogger.start();
	}

	

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		processRobotModeChange(RobotMode.DISABLED);
	}

	@Override
	public void disabledPeriodic() {
		beginPeriodic();
		Scheduler.getInstance().run();
		endPeriodic();
	}
	
	boolean autonomousCommandIsStarted = false;
	Timer autonomousTimer = new Timer();

	/**
	 * 
	 */
	@Override
	public void autonomousInit() {
		processRobotModeChange(RobotMode.AUTONOMOUS);
		
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		autonomousCommand = null;
		
		autonomousCommandIsStarted = false;
		autonomousTimer.reset();
		autonomousTimer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		beginPeriodic();
		
		double elapsedTime = autonomousTimer.get();
		
		String gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		
		// do we have game data yet?
		if (gameMessage != null && gameMessage.length() >= 3) {
			// yes, we do. have we calculated our autonomous?
			if(autonomousCommand == null) {
				// no, we don't. calculate autonomous.
				logger.info("Game Message = {}, Delay = {}, Trust = {}, pos = {}", gameMessage,
						delayChooser.getSelected(), trustChooser.getSelected(), posChooser.getSelected());
				char startingPos = posChooser.getSelected().charAt(0);
				AutonomousDescriptor autonomousDescriptor = AutonomousDescriptorMaker.makeAutonomousDescriptor(posChooser.getSelected().charAt(0), gameMessage.substring(0).charAt(0), gameMessage.substring(1).charAt(0), trustChooser.getSelected());
				WhereToPutCube whereToPutCube = autonomousDescriptor.getWhereToPutCube();
				logger.info("Autonomous descriptor = {} ", autonomousDescriptor);
				
				CommandGroup commandGroup = new CommandGroup();
				commandGroup.addSequential(new LiftShiftHighGear());
				if(startingPos != 'C') {
					CommandGroup unfoldandlift = new CommandGroup();
					unfoldandlift.addSequential(new PivotDownCommand());
					if(whereToPutCube == whereToPutCube.SCALE) {
						unfoldandlift.addSequential(new AutoMoveLiftUpToScaleHeight());
					} else {
						unfoldandlift.addSequential(new AutoMoveLiftUpToSwitchHeight());
						
					}
					commandGroup.addParallel(unfoldandlift);
					
				}
				
				commandGroup.addSequential(autonomousDescriptor.getPath());
				
				if (whereToPutCube !=WhereToPutCube.NOWHERE) {
					commandGroup.addSequential(new AutonomousPukeCubeCommand());
				}
				if(whereToPutCube == whereToPutCube.SCALE) {
					commandGroup.addSequential(new LiftShiftLowGear());
					commandGroup.addSequential(new Path_BackUpFromScale());
				}
				
				commandGroup.addSequential(new AllDoneCommand());
				autonomousCommand = commandGroup;
				
			}
		}
		
		// do we have a calculated autonomous, but we have not started it yet?
		if(autonomousCommand != null && !autonomousCommandIsStarted) {
			// yes. is it time to start yet?
			int delay = delayChooser.getSelected();
			if(elapsedTime > delay) {
				// yes. start it.
				logger.info("Starting {}", autonomousCommand);
				autonomousCommand.start();
				autonomousCommandIsStarted = true;
			}
		}
		 
		// has a long time gone without any game data?
		if(autonomousCommand == null && elapsedTime > 10) {
			// yes. just advance to line
			autonomousCommand = new AutonomousBailCommand();
			logger.info("Starting {}", autonomousCommand);
			autonomousCommand.start();
			autonomousCommandIsStarted = true;
		}
		
		// now do autonomous stuff
		Scheduler.getInstance().run();
		endPeriodic();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		
		processRobotModeChange(RobotMode.TELEOP);
		liftSubsystem.setHighGear();
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		beginPeriodic();
		Scheduler.getInstance().run();
		endPeriodic();
	}
	
	public void testInit() {
		// This makes sure that the autonomous stops running when
		// test starts running.
		if (autonomousCommand != null)
			((Command) autonomousCommand).cancel();
		processRobotModeChange(RobotMode.TEST);
	}


	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		beginPeriodic();
		//LiveWindow.run();
		endPeriodic();
	}
	
	/************************************************************************
	 * here are the 3620 goodies
	 ************************************************************************/
	/*
	 * this routine gets called whenever we change modes
	 */
	void processRobotModeChange(RobotMode newMode) {
		logger.info("Switching from {} to {}", currentRobotMode, newMode);
		
		if (currentRobotMode == RobotMode.INIT) {
			RobotMap.checkTheCANBus();
		}
		
		previousRobotMode = currentRobotMode;
		currentRobotMode = newMode;

		// if any subsystems need to know about mode changes, let
		// them know here.
		// exampleSubsystem.processRobotModeChange(newMode);
		lightSubsystem.modeChange(newMode, previousRobotMode);
		
	}

	/*
	 * these routines get called at the beginning and end of all periodics.
	 */
	void beginPeriodic() {
		// if some subsystems need to get called in all modes at the beginning
		// of periodic, do it here
		SmartDashboard.putNumber("NavX", driveSubsystem.getAngle());
    	SmartDashboard.putNumber("Left Encoder", Robot.driveSubsystem.readLeftEncRaw() );
    	SmartDashboard.putNumber("Right Encoder", Robot.driveSubsystem.readRightEncRaw() );
    	
    	liftSubsystem.beginPeriodic();

		// don't need to do anything
	}

	void endPeriodic() {
		// if some subsystems need to get called in all modes at the end
		// of periodic, do it here
		//gearSubsystem.updateDashboard();
    	liftSubsystem.endPeriodic();

		// and log data!
		updateDashboard();
		
	}
	
	void updateDashboard() {
		//SmartDashboard.putNumber("driver y joystick", -Robot.m_oi.driveJoystick.getRawAxis(1));
		//SmartDashboard.putNumber("driver x joystick", Robot.m_oi.driveJoystick.getRawAxis(4));
	}
	
}

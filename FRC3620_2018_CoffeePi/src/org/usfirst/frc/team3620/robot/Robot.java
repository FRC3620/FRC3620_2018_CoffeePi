/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3620.robot;

import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Preferences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.autonomous.AutonomousDescriptorMaker;
import org.usfirst.frc.team3620.robot.autonomous.PathPicker;
import org.usfirst.frc.team3620.robot.commands.*;
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

	Command m_autonomousCommand;
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
		
		/*
		 * Runs to check Kai box for robot position
		 */

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		processRobotModeChange(RobotMode.AUTONOMOUS);
		
		String gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		
		int delay = delayChooser.getSelected();
		logger.info("delay = {}, Trust = {}, pos = {}", delay, trustChooser.getSelected(), posChooser.getSelected());
		
		AutonomousDelayCommand delayCommand = new AutonomousDelayCommand(delay);

		Class<? extends Command> chosenOne = AutonomousDescriptorMaker.pickFirstPath(posChooser.getSelected().charAt(0), gameMessage.substring(0).charAt(0), gameMessage.substring(1).charAt(0), trustChooser.getSelected());
		logger.info("chosen path = {} ", chosenOne);
		Command chosen = null;
		try {
			chosen = (Command) chosenOne.newInstance();
			//chosen.start();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CommandGroup commandGroup = new CommandGroup();
		commandGroup.addSequential(delayCommand);
		if (chosen != null) {
			commandGroup.addSequential(chosen);
		}
	
		commandGroup.addSequential(new AutonomousPukeCubeCommand());
		
		commandGroup.start();
		
		//autoCommand.start();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		beginPeriodic();
		Scheduler.getInstance().run();
		endPeriodic();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		processRobotModeChange(RobotMode.TELEOP);
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
		if (m_autonomousCommand != null)
			((Command) m_autonomousCommand).cancel();
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

		// don't need to do anything
	}

	void endPeriodic() {
		// if some subsystems need to get called in all modes at the end
		// of periodic, do it here
		//gearSubsystem.updateDashboard();

		// and log data!
		updateDashboard();
		
	}
	
	void updateDashboard() {
		//SmartDashboard.putNumber("driver y joystick", -Robot.m_oi.driveJoystick.getRawAxis(1));
		//SmartDashboard.putNumber("driver x joystick", Robot.m_oi.driveJoystick.getRawAxis(4));
	}
	
}

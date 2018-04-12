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
import edu.wpi.first.wpilibj.Preferences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.autonomous.AutonomousDescriptor;
import org.usfirst.frc.team3620.robot.autonomous.AutonomousDescriptorMaker;
import org.usfirst.frc.team3620.robot.autonomous.WhereToPutCube;
import org.usfirst.frc.team3620.robot.commands.*;
import org.usfirst.frc.team3620.robot.paths.AbstractPath;
import org.usfirst.frc.team3620.robot.paths.Path1_LeftStart_DriveAcrossLine;
import org.usfirst.frc.team3620.robot.paths.Path1_LeftStart_LeftScaleSide;
import org.usfirst.frc.team3620.robot.paths.Path1_RightStart_DriveAcrossLine;
import org.usfirst.frc.team3620.robot.paths.Path2_AlleyCube_LeftScaleSide;
import org.usfirst.frc.team3620.robot.paths.Path2_AlleyCube_RightScaleSide;
import org.usfirst.frc.team3620.robot.paths.Path2_LeftScaleSide_AlleyCube;
import org.usfirst.frc.team3620.robot.paths.Path2_RightScaleSide_AlleyCube;
import org.usfirst.frc.team3620.robot.paths.Path2_TurnALittle;
import org.usfirst.frc.team3620.robot.paths.Path_BackUpFromScale;
import org.usfirst.frc.team3620.robot.paths.Path_LineUpForCrossLeft;
import org.usfirst.frc.team3620.robot.paths.Path_LineUpForCrossRight;
import org.usfirst.frc.team3620.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.LightSubsystem;
import org.usfirst.frc.team3620.robot.subsystems.RampSubsystem;
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
	boolean goForTwoScale = false;
	boolean goForTwoSwitch = false;
	
	
	// subsystems
	public static ExampleSubsystem kExampleSubsystem;
	public static DriveSubsystem driveSubsystem;
	public static LightSubsystem lightSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static LiftSubsystem liftSubsystem;
	public static RampSubsystem rampSubsystem;
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
		SmartDashboard.putData("LiftSubsystem",liftSubsystem);
		rampSubsystem = new RampSubsystem();
		operatorView = new OperatorView();
		operatorView.operatorViewInit();

		// Initialize Operator Interface 
		m_oi = new OI(); 

		posChooser.addDefault("Null", "N");
		posChooser.addObject("Left",  "L");
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
	goForTwoScale = true;
	goForTwoSwitch = false;
		
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
				boolean trust = trustChooser.getSelected();
				AutonomousDescriptor autonomousDescriptor = AutonomousDescriptorMaker.makeAutonomousDescriptor(posChooser.getSelected().charAt(0), gameMessage.substring(0).charAt(0), gameMessage.substring(1).charAt(0), trustChooser.getSelected());
				logger.info("Autonomous descriptor = {} ", autonomousDescriptor);


				if (autonomousDescriptor != null) {
			
					WhereToPutCube whereToPutCube = autonomousDescriptor.getWhereToPutCube();
					CommandGroup commandGroup = new CommandGroup();

					commandGroup.addSequential(new LiftShiftHighGear());
					commandGroup.addSequential(new ClampCommand());
					CommandGroup unfoldandlift = new CommandGroup();
					CommandGroup unfoldandlift2 = new CommandGroup();
					CommandGroup liftDownAndUnfold = new CommandGroup();
					if((trust == true) && (startingPos != gameMessage.substring(1).charAt(0)) && (whereToPutCube == whereToPutCube.SCALE)) {
						if(gameMessage.substring(1).charAt(0) == 'L') {
							commandGroup.addSequential(new Path_LineUpForCrossLeft());
							commandGroup.addSequential(new AllDoneCommand());
							autonomousCommand = commandGroup;
						}else if(gameMessage.substring(1).charAt(0) == 'R') {
							commandGroup.addSequential(new Path_LineUpForCrossRight());
							commandGroup.addSequential(new AllDoneCommand());
							autonomousCommand = commandGroup;
						}
						
					} else {
						

					
					if(startingPos != 'C') {
						
						unfoldandlift.addSequential(new PivotUpCommand());
						unfoldandlift2.addSequential(new PivotUpCommand());
						if(whereToPutCube == whereToPutCube.SCALE) {
							if(startingPos != gameMessage.substring(1).charAt(0)) {
								unfoldandlift.addSequential(new WaitJustALittle(4));
								unfoldandlift.addSequential(new AutoMoveLiftUpToScaleHeight());
							}
							else{
								unfoldandlift.addSequential(new AutoMoveLiftUpToScaleHeight());
							}
							unfoldandlift2.addSequential(new AutoMoveLiftUpToScaleHeight());
							
						} else {
							unfoldandlift.addSequential(new AutoMoveLiftUpToSwitchHeight());
							unfoldandlift2.addSequential(new AutoMoveLiftUpToScaleHeight());
						}

						unfoldandlift.addSequential(new HoldLift());
						
						commandGroup.addParallel(unfoldandlift);

					}
					liftDownAndUnfold.addSequential(new AutoMoveLiftDown());
					liftDownAndUnfold.addSequential(new PivotDownCommand());
					liftDownAndUnfold.addSequential(new UnClampCommand());
					AbstractPath path = autonomousDescriptor.getPath();
					commandGroup.addSequential(path);
				
				if (whereToPutCube !=WhereToPutCube.NOWHERE) {
					commandGroup.addSequential(new AutonomousPukeCubeCommand());
				}
				//Add Boolean for shooting for two
				if(whereToPutCube == whereToPutCube.SCALE) {
					CommandGroup unfoldAndDrop = new CommandGroup();

					if(goForTwoScale == true){
						
					
						
						unfoldAndDrop.addSequential(liftDownAndUnfold);
						unfoldAndDrop.addParallel(new AutonomousIntakeCubeCommand());
						if(gameMessage.substring(1).charAt(0) == 'L') {
							unfoldAndDrop.addSequential(new Path2_LeftScaleSide_AlleyCube());
						} else if(gameMessage.substring(1).charAt(0) == 'R') {
							unfoldAndDrop.addSequential(new Path2_RightScaleSide_AlleyCube());
						} 
						
						unfoldAndDrop.addSequential(new ClampCommand());
						unfoldAndDrop.addParallel(unfoldandlift2);
						if(gameMessage.substring(1).charAt(0) == 'L') {
							unfoldAndDrop.addSequential(new Path2_AlleyCube_LeftScaleSide());
							unfoldAndDrop.addSequential(new Path2_TurnALittle(30.0, true));
						} else if(gameMessage.substring(1).charAt(0) == 'R') {
							unfoldAndDrop.addSequential(new Path2_AlleyCube_RightScaleSide());
							unfoldAndDrop.addSequential(new Path2_TurnALittle(30.0, false));
						}
						
						
					/*	unfoldAndDrop.addSequential(new Path_BackUpFromScale());
						unfoldAndDrop.addSequential(new AutoMoveLiftDown()); */
					
						
						
						
						//DOES UNFOLDANDLIFT EXIST OUTSIDE OF THE IF STATEMENT?
						
						
						
					} else{								
						
													//Needs to be run forwards
						unfoldAndDrop.addSequential(new Path_BackUpFromScale());
						unfoldAndDrop.addSequential(new AutoMoveLiftDown());
					} 
					
					
					commandGroup.addSequential(unfoldAndDrop);
					if(whereToPutCube == WhereToPutCube.SCALE) {
						commandGroup.addSequential(new AutonomousPukeCubeCommand());
					}
						
					/*	unfoldAndDrop.addSequential(new Path_BackUpFromScale());
						unfoldAndDrop.addSequential(new AutoMoveLiftDown()); */
					goForTwoScale = false;
					
					
					
				} else if(whereToPutCube == whereToPutCube.SWITCH && goForTwoSwitch == true) {
					CommandGroup switchUnfoldAndUnclamp = new CommandGroup();
					switchUnfoldAndUnclamp.addSequential(new PivotDownCommand());
					switchUnfoldAndUnclamp.addSequential(new UnClampCommand());
					commandGroup.addParallel(switchUnfoldAndUnclamp);
					if(gameMessage.substring(0).charAt(0) == 'L') {
			//			commandGroup.addSequential(new Path2_RightSwitch_CubeZone());
					}
					
					CommandGroup clampAndFoldUp = new CommandGroup();
					clampAndFoldUp.addSequential(new ClampCommand());
					commandGroup.addSequential(clampAndFoldUp);
					commandGroup.addParallel(new PivotUpCommand());
			//		commandGroup.addSequential(new Path2_CubeZone_RightSwitch());
					commandGroup.addSequential(new AutonomousPukeCubeCommand());
				}
				
				commandGroup.addSequential(new AllDoneCommand());
				autonomousCommand = commandGroup;
				goForTwoScale = false;
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
				if(posChooser.getSelected().charAt(0) == 'L') {
					autonomousCommand = new Path1_LeftStart_DriveAcrossLine();
				} else if(posChooser.getSelected().charAt(0) == 'R') {
					autonomousCommand = new Path1_RightStart_DriveAcrossLine();
				}
				autonomousCommand = new AutonomousBailCommand();
				logger.info("Starting {}", autonomousCommand);
				autonomousCommand.start();
				autonomousCommandIsStarted = true;
			}

			// now do autonomous stuff
			Scheduler.getInstance().run();
			endPeriodic();
		}

		 
		// has a long time gone without any game data?
		if(autonomousCommand == null && elapsedTime > 10) {
			// yes. just advance to line
			autonomousCommand = new AutonomousBailCommand();
			logger.info("Starting {}", autonomousCommand);
			autonomousCommand.start();
			autonomousCommandIsStarted = true;
		}
	}
		
	/*	CommandGroup autoCommandTester = new CommandGroup();
		autonomousCommand = autoCommandTester; */
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
		
		liftSubsystem.setHighGear(); 
		logger.info("Lift set to high gear");
		intakeSubsystem.clampCube(); 
		logger.info("Clamper closed");
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
	//	lightSubsystem.modeChange(newMode, previousRobotMode);
		
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

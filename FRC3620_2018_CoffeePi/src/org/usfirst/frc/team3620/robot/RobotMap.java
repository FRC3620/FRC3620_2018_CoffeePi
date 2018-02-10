// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc.team3620.robot;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.ErrorCode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	static Logger logger = EventLogging.getLogger(RobotMap.class, Level.INFO);

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static DigitalInput exampleSubsystemDigitalInput0;
	public static SpeedController driveSubsystemLeftSpeedController;
	public static SpeedController driveSubsystemRightSpeedController;
	public static DifferentialDrive driveSubsystemPWMDifferentialDrive;
	public static WPI_TalonSRX driveSubsystemTalonSRX1;
	public static WPI_VictorSPX driveSubsystemVictorSPX2;
	public static WPI_VictorSPX driveSubsystemVictorSPX3;
	public static SpeedControllerGroup driveSubsystemLeftSpeedControllerGroup;
	public static WPI_TalonSRX driveSubsystemTalonSRX4;
	public static WPI_VictorSPX driveSubsystemVictorSPX5;
	public static WPI_VictorSPX driveSubsystemVictorSPX6;
	public static SpeedControllerGroup driveSubsystemRightSpeedControllerGroup;
	public static DifferentialDrive driveSubsystemCANDifferentialDrive;
	public static SpeedController lightSubsystemLightPWM9;
	public static WPI_TalonSRX liftSubsystemTalonSRX7;
	public static WPI_VictorSPX liftSubsystemVictorSPX8;
	public static WPI_VictorSPX liftSubsystemVictorSPX9;
	public static SpeedControllerGroup liftSubsystemClimbSpeedControllerGroup;
	public static DigitalInput liftSubsystemElevatorHomeSwitch;
	public static DigitalInput liftSubsystemIntakeInPos;
	public static DigitalInput liftSubsystemIntakeFacingBack;
	public static DigitalInput intakeSubsystemCubeIntook;
	public static DigitalInput intakeSubsystemScaleSensed;
	public static SpeedController intakeSubsystemIntakeRoller1;
	public static SpeedController intakeSubsystemIntakeRoller2;
	public static WPI_TalonSRX intakeSubsystemIntakePivot;
	public static DoubleSolenoid intakeSubsystemIntakeClamperSolenoid;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	
	static final double PEAK_DRIVE_VOLTAGE = 10;
	static final int FILTER_WINDOW_SAMPLES = 32;
	
	public static void init() {
		exampleSubsystemDigitalInput0 = new DigitalInput(0);
		LiveWindow.addSensor("ExampleSubsystem", "Digital Input 0", exampleSubsystemDigitalInput0);

		driveSubsystemLeftSpeedController = new Spark(0);
		LiveWindow.addActuator("DriveSubsystem", "LeftSpeedController", (Spark) driveSubsystemLeftSpeedController);
		driveSubsystemLeftSpeedController.setInverted(false);
		driveSubsystemRightSpeedController = new Spark(1);
		LiveWindow.addActuator("DriveSubsystem", "RightSpeedController", (Spark) driveSubsystemRightSpeedController);
		driveSubsystemRightSpeedController.setInverted(false);
		driveSubsystemPWMDifferentialDrive = new DifferentialDrive(driveSubsystemLeftSpeedController,
				driveSubsystemRightSpeedController);
		LiveWindow.addActuator("DriveSubsystem", "PWMDifferentialDrive", driveSubsystemPWMDifferentialDrive);
		driveSubsystemPWMDifferentialDrive.setSafetyEnabled(true);
		driveSubsystemPWMDifferentialDrive.setExpiration(0.1);
		driveSubsystemPWMDifferentialDrive.setMaxOutput(1.0);

		ErrorCode ctreErrorCode;

			// TODO - make this absolutely bulletproof
		if (Robot.canDeviceFinder.isSRXPresent(1)) {
			driveSubsystemTalonSRX1 = new WPI_TalonSRX(1);
			ctreErrorCode = driveSubsystemTalonSRX1.configVoltageCompSaturation(PEAK_DRIVE_VOLTAGE, 0);
			if (ctreErrorCode != ErrorCode.OK)
				logger.warn("trouble setting voltageComp on SRX1: %s", ctreErrorCode);
			driveSubsystemTalonSRX1.enableVoltageCompensation(true);
			driveSubsystemTalonSRX1.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			
			driveSubsystemVictorSPX2 = new WPI_VictorSPX(2);
			driveSubsystemVictorSPX3 = new WPI_VictorSPX(3);

			driveSubsystemLeftSpeedControllerGroup = new SpeedControllerGroup(driveSubsystemTalonSRX1,
					driveSubsystemVictorSPX2, driveSubsystemVictorSPX3);
			LiveWindow.addActuator("DriveSubsystem", "LeftSpeedControllerGroup",
					driveSubsystemLeftSpeedControllerGroup);

			driveSubsystemTalonSRX4 = new WPI_TalonSRX(4);
			ctreErrorCode = driveSubsystemTalonSRX4.configVoltageCompSaturation(PEAK_DRIVE_VOLTAGE, 0);
			if (ctreErrorCode != ErrorCode.OK)
				logger.warn("trouble setting voltageComp on SRX4: %s", ctreErrorCode);
			driveSubsystemTalonSRX4.enableVoltageCompensation(true);
			driveSubsystemTalonSRX4.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			
			driveSubsystemVictorSPX5 = new WPI_VictorSPX(5);
			driveSubsystemVictorSPX6 = new WPI_VictorSPX(6);

			driveSubsystemRightSpeedControllerGroup = new SpeedControllerGroup(driveSubsystemTalonSRX4,
					driveSubsystemVictorSPX5, driveSubsystemVictorSPX6);
			LiveWindow.addActuator("DriveSubsystem", "RightSpeedControllerGroup",
					driveSubsystemRightSpeedControllerGroup);

			driveSubsystemCANDifferentialDrive = new DifferentialDrive(driveSubsystemLeftSpeedControllerGroup,
					driveSubsystemRightSpeedControllerGroup);
			LiveWindow.addActuator("DriveSubsystem", "CANDifferentialDrive", driveSubsystemCANDifferentialDrive);
			driveSubsystemCANDifferentialDrive.setSafetyEnabled(true);
			driveSubsystemCANDifferentialDrive.setExpiration(0.1);
			driveSubsystemCANDifferentialDrive.setMaxOutput(1.0);
		} else {
			logger.info ("Talon SRX 1 is missing, disabling CAN drive");
		}

		lightSubsystemLightPWM9 = new Spark(9);
		LiveWindow.addActuator("LightSubsystem", "LightPWM9", (Spark) lightSubsystemLightPWM9);
		lightSubsystemLightPWM9.setInverted(false);
		
		liftSubsystemTalonSRX7 = new WPI_TalonSRX(7);

		liftSubsystemVictorSPX8 = new WPI_VictorSPX(8);

		liftSubsystemVictorSPX9 = new WPI_VictorSPX(9);

		liftSubsystemClimbSpeedControllerGroup = new SpeedControllerGroup(liftSubsystemTalonSRX7,
				liftSubsystemVictorSPX8, liftSubsystemVictorSPX9);
		LiveWindow.addActuator("LiftSubsystem", "ClimbSpeedControllerGroup", liftSubsystemClimbSpeedControllerGroup);

		liftSubsystemElevatorHomeSwitch = new DigitalInput(1);
		LiveWindow.addSensor("LiftSubsystem", "ElevatorHomeSwitch", liftSubsystemElevatorHomeSwitch);

		liftSubsystemIntakeInPos = new DigitalInput(2);
		LiveWindow.addSensor("LiftSubsystem", "IntakeInPos", liftSubsystemIntakeInPos);

		liftSubsystemIntakeFacingBack = new DigitalInput(3);
		LiveWindow.addSensor("LiftSubsystem", "IntakeFacingBack", liftSubsystemIntakeFacingBack);

		intakeSubsystemCubeIntook = new DigitalInput(4);
		LiveWindow.addSensor("IntakeSubsystem", "CubeIntook", intakeSubsystemCubeIntook);

		intakeSubsystemScaleSensed = new DigitalInput(5);
		LiveWindow.addSensor("IntakeSubsystem", "ScaleSensed", intakeSubsystemScaleSensed);

		intakeSubsystemIntakeRoller1 = new Spark(2);
		LiveWindow.addActuator("IntakeSubsystem", "IntakeRoller1", (Spark) intakeSubsystemIntakeRoller1);
		intakeSubsystemIntakeRoller1.setInverted(false);
		intakeSubsystemIntakeRoller2 = new Spark(3);
		LiveWindow.addActuator("IntakeSubsystem", "IntakeRoller2", (Spark) intakeSubsystemIntakeRoller2);
		intakeSubsystemIntakeRoller2.setInverted(false);
		intakeSubsystemIntakePivot = new WPI_TalonSRX(5);
		if(Robot.canDeviceFinder.isPCMPresent(0)) {
		intakeSubsystemIntakeClamperSolenoid = new DoubleSolenoid(0, 0, 1);
		LiveWindow.addActuator("IntakeSubsystem", "IntakeClamperSolenoid", intakeSubsystemIntakeClamperSolenoid);
		} else {
		logger.info("No Solenoid detected");
		}
		

	}

}

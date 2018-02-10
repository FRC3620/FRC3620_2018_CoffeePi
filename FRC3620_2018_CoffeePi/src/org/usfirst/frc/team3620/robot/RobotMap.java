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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
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
	public static WPI_TalonSRX driveSubsystemTalonLeft1;
	public static WPI_VictorSPX driveSubsystemVictorLeft2;
	public static WPI_VictorSPX driveSubsystemVictorLeft3;
	public static WPI_VictorSPX driveSubsystemVictorLeft4;
	public static WPI_TalonSRX driveSubsystemTalonRight1;
	public static WPI_VictorSPX driveSubsystemVictorRight2;
	public static WPI_VictorSPX driveSubsystemVictorRight3;
	public static WPI_VictorSPX driveSubsystemVictorRight4;
	public static DifferentialDrive driveSubsystemCANDifferentialDrive;
	public static SpeedController lightSubsystemLightPWM9;
	public static WPI_TalonSRX liftSubsystemTalon1;
	public static WPI_VictorSPX liftSubsystemVictor2;
	public static WPI_VictorSPX liftSubsystemVictor3;
	public static WPI_VictorSPX liftSubsystemVictor4;
	public static DigitalInput liftSubsystemElevatorHomeSwitch;
	public static DigitalInput liftSubsystemIntakeInPos;
	public static DigitalInput liftSubsystemIntakeFacingBack;
	public static DigitalInput intakeSubsystemCubeIntook;
	public static DigitalInput intakeSubsystemScaleSensed;
	public static WPI_TalonSRX intakeSubsystemIntakeRoller1;
	public static WPI_TalonSRX intakeSubsystemIntakeRoller2;
	public static WPI_TalonSRX intakeSubsystemIntakePivot;
	public static DoubleSolenoid intakeSubsystemIntakeClamperSolenoid;
	public static AHRS driveSubsystemAHRS;	//Needs testing. -Kai

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	
	static final double PEAK_DRIVE_VOLTAGE = 10;
	static final int FILTER_WINDOW_SAMPLES = 32;
	
	public static void init() {
		exampleSubsystemDigitalInput0 = new DigitalInput(0);
		LiveWindow.addSensor("ExampleSubsystem", "Digital Input 0", exampleSubsystemDigitalInput0);
		
		// stuff for Drive Subsystem


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

			driveSubsystemTalonLeft1 = new WPI_TalonSRX(1);
			resetControllerToKnownState(driveSubsystemTalonLeft1);
			ctreErrorCode = driveSubsystemTalonLeft1.configVoltageCompSaturation(PEAK_DRIVE_VOLTAGE, 0);
			if (ctreErrorCode != ErrorCode.OK)
				logger.warn("trouble setting voltageComp on SRX Left: %s", ctreErrorCode);
			driveSubsystemTalonLeft1.enableVoltageCompensation(true);
			driveSubsystemTalonLeft1.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			
			driveSubsystemVictorLeft2 = new WPI_VictorSPX(2);
			resetControllerToKnownState(driveSubsystemVictorLeft2);
			driveSubsystemVictorLeft2.follow(driveSubsystemTalonLeft1);
			
			driveSubsystemVictorLeft3 = new WPI_VictorSPX(3);
			resetControllerToKnownState(driveSubsystemVictorLeft3);
			driveSubsystemVictorLeft3.follow(driveSubsystemTalonLeft1);
			
			driveSubsystemVictorLeft4 = new WPI_VictorSPX(4);
			resetControllerToKnownState(driveSubsystemVictorLeft4);
			driveSubsystemVictorLeft4.follow(driveSubsystemTalonLeft1);

			driveSubsystemTalonRight1 = new WPI_TalonSRX(5);
			resetControllerToKnownState(driveSubsystemTalonRight1);
			driveSubsystemTalonRight1.setInverted(true);
			ctreErrorCode = driveSubsystemTalonRight1.configVoltageCompSaturation(PEAK_DRIVE_VOLTAGE, 0);
			if (ctreErrorCode != ErrorCode.OK)
				logger.warn("trouble setting voltageComp on SRX Right: %s", ctreErrorCode);
			driveSubsystemTalonRight1.enableVoltageCompensation(true);
			driveSubsystemTalonRight1.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			
			driveSubsystemVictorRight2 = new WPI_VictorSPX(6);
			resetControllerToKnownState(driveSubsystemVictorRight2);
			driveSubsystemVictorRight2.follow(driveSubsystemTalonRight1);
			driveSubsystemVictorRight2.setInverted(true);
			
			driveSubsystemVictorRight3 = new WPI_VictorSPX(7);
			resetControllerToKnownState(driveSubsystemVictorRight3);
			driveSubsystemVictorRight3.follow(driveSubsystemTalonRight1);
			driveSubsystemVictorRight3.setInverted(true);
			
			driveSubsystemVictorRight4 = new WPI_VictorSPX(8);
			resetControllerToKnownState(driveSubsystemVictorRight4);
			driveSubsystemVictorRight4.follow(driveSubsystemTalonRight1);
			driveSubsystemVictorRight4.setInverted(true);

			driveSubsystemCANDifferentialDrive = new DifferentialDrive(driveSubsystemTalonLeft1,
					driveSubsystemTalonRight1);

			LiveWindow.addActuator("DriveSubsystem", "CANDifferentialDrive", driveSubsystemCANDifferentialDrive);
			driveSubsystemCANDifferentialDrive.setSafetyEnabled(true);
			driveSubsystemCANDifferentialDrive.setExpiration(0.1);
			driveSubsystemCANDifferentialDrive.setMaxOutput(1.0);
		} else {
			logger.info("Talon SRX 1 is missing, disabling CAN drive");
		}

		// stuff for Lift
		
		liftSubsystemTalon1 = new WPI_TalonSRX(9);
		resetControllerToKnownState(liftSubsystemTalon1);
		
		liftSubsystemVictor2 = new WPI_VictorSPX(10);
		resetControllerToKnownState(liftSubsystemVictor2);
		liftSubsystemVictor2.follow(liftSubsystemTalon1);
		
		liftSubsystemVictor3 = new WPI_VictorSPX(11);
		resetControllerToKnownState(liftSubsystemVictor3);
		liftSubsystemVictor3.follow(liftSubsystemTalon1);
		
		liftSubsystemVictor4 = new WPI_VictorSPX(12);
		resetControllerToKnownState(liftSubsystemVictor4);
		liftSubsystemVictor4.follow(liftSubsystemTalon1);
		
		liftSubsystemElevatorHomeSwitch = new DigitalInput(1);
		LiveWindow.addSensor("LiftSubsystem", "ElevatorHomeSwitch", liftSubsystemElevatorHomeSwitch);

		liftSubsystemIntakeInPos = new DigitalInput(2);
		LiveWindow.addSensor("LiftSubsystem", "IntakeInPos", liftSubsystemIntakeInPos);

		liftSubsystemIntakeFacingBack = new DigitalInput(3);
		LiveWindow.addSensor("LiftSubsystem", "IntakeFacingBack", liftSubsystemIntakeFacingBack);
		
		// stuff for Intake
		
		intakeSubsystemIntakeRoller1 = new WPI_TalonSRX(13);
		resetControllerToKnownState(intakeSubsystemIntakeRoller1);
		LiveWindow.addActuator("IntakeSubsystem", "IntakeRoller1", (WPI_TalonSRX) intakeSubsystemIntakeRoller1);
		
		intakeSubsystemIntakeRoller2 = new WPI_TalonSRX(14);
		resetControllerToKnownState(intakeSubsystemIntakeRoller2);
		LiveWindow.addActuator("IntakeSubsystem", "IntakeRoller2", (WPI_TalonSRX) intakeSubsystemIntakeRoller2);
		
		intakeSubsystemIntakePivot = new WPI_TalonSRX(15);
		resetControllerToKnownState(intakeSubsystemIntakePivot);
		LiveWindow.addActuator("IntakeSubsystem", "IntakePivot", (WPI_TalonSRX) intakeSubsystemIntakePivot);
		
		intakeSubsystemCubeIntook = new DigitalInput(4);
		LiveWindow.addSensor("IntakeSubsystem", "CubeIntook", intakeSubsystemCubeIntook);

		intakeSubsystemScaleSensed = new DigitalInput(5);
		LiveWindow.addSensor("IntakeSubsystem", "ScaleSensed", intakeSubsystemScaleSensed);

		lightSubsystemLightPWM9 = new Spark(9);
		LiveWindow.addActuator("LightSubsystem", "LightPWM9", (Spark) lightSubsystemLightPWM9);
		lightSubsystemLightPWM9.setInverted(false);

		if (Robot.canDeviceFinder.isPCMPresent(0)) {
			intakeSubsystemIntakeClamperSolenoid = new DoubleSolenoid(0, 0, 1);
			LiveWindow.addActuator("IntakeSubsystem", "IntakeClamperSolenoid", intakeSubsystemIntakeClamperSolenoid);
		} else {
			logger.info("No Solenoid detected");
		}
		
		driveSubsystemAHRS = new AHRS(edu.wpi.first.wpilibj.SPI.Port.kMXP);	//Needs testing. -Kai
		LiveWindow.addSensor("Drivetrain", "AHRS", driveSubsystemAHRS);

	}
	
	static void resetControllerToKnownState (BaseMotorController x) {
		x.setInverted(false);
		x.setNeutralMode(NeutralMode.Coast);
		x.set(ControlMode.PercentOutput, 0);
	}

}

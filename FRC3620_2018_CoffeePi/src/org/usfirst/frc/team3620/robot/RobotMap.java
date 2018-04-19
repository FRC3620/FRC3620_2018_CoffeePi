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
import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	private static boolean competitionRobot = true;
	static Logger logger = EventLogging.getLogger(RobotMap.class, Level.INFO);

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static DigitalInput exampleSubsystemDigitalInput0;
	public static WPI_TalonSRX driveSubsystemTalonLeft1;
	public static WPI_VictorSPX driveSubsystemVictorLeft3;
	public static WPI_VictorSPX driveSubsystemVictorLeft4;
	public static WPI_TalonSRX driveSubsystemTalonRight1;
	public static WPI_VictorSPX driveSubsystemVictorRight3;
	public static WPI_VictorSPX driveSubsystemVictorRight4;
	public static DifferentialDrive driveSubsystemCANDifferentialDrive;
	public static SpeedController lightSubsystemLightPWM9;
	public static WPI_TalonSRX liftSubsystemTalon1;
	public static WPI_VictorSPX liftSubsystemVictor2;
	public static WPI_VictorSPX liftSubsystemVictor3;
	public static WPI_VictorSPX liftSubsystemVictor4;
	public static WPI_TalonSRX intakeSubsystemIntakeRoller1;
	public static WPI_TalonSRX intakeSubsystemIntakeRoller2;
	public static WPI_TalonSRX intakeSubsystemIntakePivot;
	public static DoubleSolenoid intakeSubsystemIntakeClamperSolenoid;
	public static AHRS driveSubsystemAHRS;	//Needs testing. -Kai
	public static DoubleSolenoid liftSubsystemGearShifter;
	
	public static Servo rampServo;

	public static DoubleSolenoid climbingJawEngager;
	
	public static Encoder driveSubsystemRightEncoder;
    public static Encoder driveSubsystemLeftEncoder;
    
    public static DigitalInput practiceBotJumper;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	
	static final double PEAK_DRIVE_VOLTAGE = 10;
	static final int FILTER_WINDOW_SAMPLES = 32;
	
	public static void init() {
		exampleSubsystemDigitalInput0 = new DigitalInput(0);
		LiveWindow.addSensor("ExampleSubsystem", "Digital Input 0", exampleSubsystemDigitalInput0);
		
		if (exampleSubsystemDigitalInput0.get() == false) {
			// there is a jumper
			competitionRobot = false;
		}
		
		// if competitionRobot is true, we try to instantiate everything. It might
		// be missing when we fire up, and come back to life because the wiring got jiggled.
		//
		// if competitionRobot is false, then don't instantiate missing hardware; that will
		// minimize the amount of console noise.
		// stuff for Drive Subsystem

		ErrorCode ctreErrorCode;
		
		// TODO - make this absolutely bulletproof
		if (competitionRobot || Robot.canDeviceFinder.isSRXPresent(1)) {

			driveSubsystemTalonLeft1 = new WPI_TalonSRX(1);
			resetControllerToKnownState(driveSubsystemTalonLeft1);
			
			ctreErrorCode = driveSubsystemTalonLeft1.configVoltageCompSaturation(PEAK_DRIVE_VOLTAGE, 0);
			if (ctreErrorCode != ErrorCode.OK)
				logger.warn("trouble setting voltageComp on SRX Left: %s", ctreErrorCode);
			
			driveSubsystemTalonLeft1.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			
			/* change period to 5 ms */
//			driveSubsystemTalonLeft1.setControlFramePeriod(ControlFrame.Control_3_General, 5);
//			driveSubsystemTalonLeft1.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 5, 10);
			
			driveSubsystemTalonLeft1.enableVoltageCompensation(true);
			driveSubsystemTalonLeft1.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			driveSubsystemTalonLeft1.configPeakCurrentLimit(0, 0);
			driveSubsystemTalonLeft1.configPeakCurrentDuration(0, 0);
			driveSubsystemTalonLeft1.configContinuousCurrentLimit(30, 100);
			driveSubsystemTalonLeft1.enableCurrentLimit(true);
			/* DANGER WILL ROBINSON: Make sure this is handled correctly for auto as well as tele. */
			driveSubsystemTalonLeft1.configOpenloopRamp(.1, 10);
			
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
			
			driveSubsystemTalonRight1.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 10, 10);
			
			/* change period to 5 ms */
//			driveSubsystemTalonRight1.setControlFramePeriod(ControlFrame.Control_3_General, 5);
//			driveSubsystemTalonRight1.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 5, 10);
			
			driveSubsystemTalonRight1.enableVoltageCompensation(true);
			driveSubsystemTalonRight1.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			driveSubsystemTalonRight1.configPeakCurrentLimit(0, 0);
			driveSubsystemTalonRight1.configPeakCurrentDuration(0, 0);
			driveSubsystemTalonRight1.configContinuousCurrentLimit(30, 100);
			driveSubsystemTalonRight1.enableCurrentLimit(true);
			
			/* DANGER WILL ROBINSON: Make sure this is handled correctly for auto as well as tele. */
			driveSubsystemTalonRight1.configOpenloopRamp(.1, 10);
			
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
		
		driveSubsystemLeftEncoder = new Encoder(6, 7, false, EncodingType.k4X);
		SmartDashboard.putData(driveSubsystemLeftEncoder);
	    //driveSubsystemLeftEncoder.setDistancePerPulse(0.105);
	    //driveSubsystemLeftEncoder.setPIDSourceType(PIDSourceType.kRate);
	    driveSubsystemRightEncoder = new Encoder(8, 9, true, EncodingType.k4X);
		SmartDashboard.putData(driveSubsystemRightEncoder);

	    //LiveWindow.addSensor("DriveSubsystem", "Right Encoder", driveSubsystemRightEncoder);
	    //driveSubsystemRightEncoder.setDistancePerPulse(0.105);
	    //driveSubsystemRightEncoder.setPIDSourceType(PIDSourceType.kRate);

		// stuff for Lift

		if (competitionRobot || Robot.canDeviceFinder.isSRXPresent(9)) {
			liftSubsystemTalon1 = new WPI_TalonSRX(9);
			resetControllerToKnownState(liftSubsystemTalon1);
			liftSubsystemTalon1.configPeakCurrentLimit(0, 10);
			liftSubsystemTalon1.configPeakCurrentDuration(0, 10);
			liftSubsystemTalon1.configContinuousCurrentLimit(25, 10);
			liftSubsystemTalon1.enableCurrentLimit(true);
			liftSubsystemTalon1.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
			liftSubsystemTalon1.overrideLimitSwitchesEnable(true);
			ctreErrorCode = liftSubsystemTalon1.configVoltageCompSaturation(12, 0);
			if (ctreErrorCode != ErrorCode.OK)
				logger.warn("trouble setting voltageComp on SRX Left: %s", ctreErrorCode);
			liftSubsystemTalon1.enableVoltageCompensation(true);
			liftSubsystemTalon1.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			liftSubsystemTalon1.configOpenloopRamp(0.25, 10); //1 second for 0 to max voltage
			liftSubsystemTalon1.configNeutralDeadband(0.0001, 0);


			// one of the mule board can have the Lift SRX, but not the SPXes, so check for those separately
			if (competitionRobot || Robot.canDeviceFinder.isSPXPresent(10)) {
				liftSubsystemVictor2 = new WPI_VictorSPX(10);
				resetControllerToKnownState(liftSubsystemVictor2);
				liftSubsystemVictor2.configNeutralDeadband(0.0001, 0);
				liftSubsystemVictor2.follow(liftSubsystemTalon1);

				liftSubsystemVictor3 = new WPI_VictorSPX(11);
				resetControllerToKnownState(liftSubsystemVictor3);
				liftSubsystemVictor3.configNeutralDeadband(0.0001, 0);
				liftSubsystemVictor3.follow(liftSubsystemTalon1);

				liftSubsystemVictor4 = new WPI_VictorSPX(12);
				resetControllerToKnownState(liftSubsystemVictor4);
				liftSubsystemVictor4.configNeutralDeadband(0.0001, 0);
				liftSubsystemVictor4.follow(liftSubsystemTalon1);
			}
		} else {
			logger.info("Talon SRX 9 is missing, disabling CAN lift");
		}
		
		
		// stuff for Intake

		if (competitionRobot|| Robot.canDeviceFinder.isSRXPresent(13)) {
			intakeSubsystemIntakeRoller1 = new WPI_TalonSRX(13);
			resetControllerToKnownState(intakeSubsystemIntakeRoller1);
			LiveWindow.addActuator("IntakeSubsystem", "IntakeRoller1", (WPI_TalonSRX) intakeSubsystemIntakeRoller1);
			intakeSubsystemIntakeRoller1.configOpenloopRamp(0, 10);

			intakeSubsystemIntakeRoller2 = new WPI_TalonSRX(14);
			resetControllerToKnownState(intakeSubsystemIntakeRoller2);
			LiveWindow.addActuator("IntakeSubsystem", "IntakeRoller2", (WPI_TalonSRX) intakeSubsystemIntakeRoller2);
			intakeSubsystemIntakeRoller2.configOpenloopRamp(0, 10);
		} else {
			logger.info("Talon SRX 13 is missing, disabling CAN intake");
		}
		
		if (competitionRobot|| Robot.canDeviceFinder.isSRXPresent(15)) {
			intakeSubsystemIntakePivot = new WPI_TalonSRX(15);
			resetControllerToKnownState(intakeSubsystemIntakePivot);
			intakeSubsystemIntakePivot.setNeutralMode(NeutralMode.Brake);
			intakeSubsystemIntakePivot.overrideLimitSwitchesEnable(false);
			intakeSubsystemIntakePivot.configNeutralDeadband(0.0001, 0);
			intakeSubsystemIntakePivot.configVoltageCompSaturation(12, 0);
			intakeSubsystemIntakePivot.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			intakeSubsystemIntakePivot.enableVoltageCompensation(true);
			LiveWindow.addActuator("IntakeSubsystem", "IntakePivot", (WPI_TalonSRX) intakeSubsystemIntakePivot);
			//intakeSubsystemIntakePivot.configVoltageMeasurementFilter(FILTER_WINDOW_SAMPLES, 0);
			//intakeSubsystemIntakePivot.configOpenloopRamp(1, 10); //1 second for 0 to max voltage
		} else {
			logger.info("Talon SRX 15 is missing, disabling CAN pivot");
		}

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
		
		if (Robot.canDeviceFinder.isPCMPresent(0)) {
			liftSubsystemGearShifter = new DoubleSolenoid(0, 2, 3);
			LiveWindow.addActuator("LiftSubsystem", "LiftSubsystemGearShifter", liftSubsystemGearShifter);
			climbingJawEngager = new DoubleSolenoid(0, 4, 5);
			LiveWindow.addActuator("LiftSubsystem", "ClimbJawEngage", climbingJawEngager);
		} else {
			logger.info("No Solenoid detected");
		}
		
		practiceBotJumper = new DigitalInput(1);
		SmartDashboard.putData(practiceBotJumper);
		
		rampServo = new Servo(1);
		rampServo.setDisabled();
		rampServo.setName("RampSubsystem",  "Servo");
	}
	
	static void resetControllerToKnownState (BaseMotorController x) {
		x.setInverted(false);
		x.setNeutralMode(NeutralMode.Coast);
		x.set(ControlMode.PercentOutput, 0);
		x.configNominalOutputForward(0, 0);
		x.configNominalOutputReverse(0, 0);
		x.configPeakOutputForward(1, 0);
		x.configPeakOutputReverse(-1, 0);
		x.configNeutralDeadband(0.04, 0);
	}
	
	public static void checkTheCANBus() {
		if (competitionRobot) {
			String[] devicesIWantToSee = new String[] {
					"SRX 1", "SPX 2", "SPX 3", "SPX 4",
					"SRX 5", "SPX 6", "SPX 7", "SPX 8",
					"SRX 9", "SPX 10", "SPX 11", "SPX 12",
					"SRX 13", "SRX 14", "SRX 15", 
					"PCM 0", "PDP 0"};
			for (String device : devicesIWantToSee) {
				if (!Robot.canDeviceFinder.isDevicePresent(device)) {
					logger.error("Device {} cannot be found on the CAN bus", device);
				}
			}
		} else {
			logger.info("mule board: skipping CAN bus check");
		}
	}

    public static boolean amIACompetitionRobot() {
    	return competitionRobot;
    }
}

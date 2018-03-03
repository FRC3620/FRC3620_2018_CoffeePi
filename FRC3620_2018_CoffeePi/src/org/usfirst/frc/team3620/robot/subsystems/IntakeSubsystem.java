package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team3620.robot.commands.ManualCubeCommand;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.slf4j.Logger;
/**
 *
 */
public class IntakeSubsystem extends Subsystem {

    private final SpeedController intakeRoller1 = RobotMap.intakeSubsystemIntakeRoller1;
    private final SpeedController intakeRoller2 = RobotMap.intakeSubsystemIntakeRoller2;
    private final WPI_TalonSRX intakePivot = RobotMap.intakeSubsystemIntakePivot;
    private final DoubleSolenoid intakeClamperSolenoid = RobotMap.intakeSubsystemIntakeClamperSolenoid;
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public static final int kSpeedPIDLoopIdx = 0;
	public static final int kTimeoutMs = 0;
	public static final boolean kMotorInvert = false;
	public static double kPSpeed = 0;
	public static double kISpeed = 0;
	public static double kDSpeed = 0;
	public static double kFSpeed = 0;
	public static double kIZoneSpeed = 0;
	public static int peakSpeedHigh = 0;
	public static int positionErrorMargin = 50;
	public int motionMagicCruiseVel;
	public int motionMagicAccel;
	public int homeSetPoint = 0;
	public int bottomSetPoint = 1330;
	public double encoderAt180 = 1000;
	public double encoderAt90 = 330;
	public double startingPivotAngle;
	public double pivotAngle;
	public double maxPivotSpeed = 0.3;
	public double encoderErrorMargin = 50;
	public double pivotAngleDeg;
	public double cosMultiplier;
	public double finalSpeed;
	public boolean haveCube;
	
	public IntakeSubsystem() {
		super();
		logger.info("Initializing intake subsystem");
		if(intakePivot != null) {
			intakePivot.config_kF(kSpeedPIDLoopIdx, kFSpeed, kTimeoutMs);
			intakePivot.config_kP(kSpeedPIDLoopIdx, kPSpeed, kTimeoutMs);
			intakePivot.config_kI(kSpeedPIDLoopIdx, kISpeed, kTimeoutMs);
			intakePivot.config_kD(kSpeedPIDLoopIdx, kDSpeed, kTimeoutMs);

			// Setting feedback device type
			intakePivot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			intakePivot.setSensorPhase(false);
			intakePivot.setNeutralMode(NeutralMode.Brake);
		}
		
		//set starting angle position
		startingPivotAngle = 90-(encoderAt90*(180/encoderAt180));
		logger.info("Starting pivot angle = " + startingPivotAngle);
		
		//TODO come up with a method to check if encoder is valid and calibrate it
		if (homeButtonIsPressed()) {		//change this condition for something to check if the the arm is calibrated
			
			isEncoderValid = true;
			logger.info("Pivot home, reseting encoder");
			resetEncoder();
			
			
		}

	}
	
	public boolean isEncoderValid;
	
	public boolean getEncoderIsValid(){
		return isEncoderValid;
	}
	
	public boolean homeButtonIsPressed() {
		if (intakePivot != null) {
    		
    		return intakePivot.getSensorCollection().isRevLimitSwitchClosed();
    		
    	}
    	return false; 
	}
	
	public double readEncoder() {
		if(intakePivot != null) {
			//int encoderPos = intakePivot.getSensorCollection().getQuadraturePosition();
			int encoderPos = intakePivot.getSelectedSensorPosition(0);
			return encoderPos;
		}
		return 0;
	}
	
	public void resetEncoder() {
		if(intakePivot != null) {

			intakePivot.setSelectedSensorPosition(0, kSpeedPIDLoopIdx, 10);
			logger.info("Resetting Encoder!");
		}
	}

	public void setPIDParameters(double P, double I, double D, double F) {
		 if(intakePivot != null) {
			 intakePivot.config_kP(0, P, 10);
			 intakePivot.config_kI(0, I, 10);
			 intakePivot.config_kD(0, D, 10);
			 intakePivot.config_kF(0, F, 10);
		 }
	}

	public void configMotionMagic(int acceleration, int velocity) {
		 if(intakePivot != null) {
			 motionMagicAccel = acceleration;
			 motionMagicCruiseVel = velocity;

			 intakePivot.configMotionCruiseVelocity(kSpeedPIDLoopIdx, motionMagicCruiseVel);
			 intakePivot.configMotionAcceleration(kSpeedPIDLoopIdx, motionMagicAccel);
		 }
	}
	
	public void movePivotDown(int position) {
		
		if (isEncoderValid) {
			if (intakePivot != null) {
				if(readEncoder()< position) {
					intakePivot.set(ControlMode.Position, position);
				}
			}
		}
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ManualCubeCommand());
    	
    }
    
    boolean haveICrabbedAtThemAboutMissingTalons = false;
    
    //bring the cube in by spinning the motors backwards
    public void bringCubeIn(double intakeSpeed){
	   if(intakeRoller1 != null) {
	    	intakeRoller1.set(-intakeSpeed);
	    	intakeRoller2.set(intakeSpeed); 
	   } else {
		   if (intakeSpeed != 0) {
			   if (!haveICrabbedAtThemAboutMissingTalons) {
				   logger.warn("Tried to bring in cube - no CANTalons!");
				   haveICrabbedAtThemAboutMissingTalons = true;
			   }
		   }
	   }
    }
   
   	//push cube out by spinning motor out
   public void pushCubeOut(double intakeSpeed) {
	   if(intakeRoller1 != null) {
			intakeRoller1.set(intakeSpeed);
		   	intakeRoller2.set(-intakeSpeed);
	   } else {
		   if (intakeSpeed !=0) {
			   if(!haveICrabbedAtThemAboutMissingTalons) {
				   logger.warn("Tried to push cube out - no CANTalons!");
				   haveICrabbedAtThemAboutMissingTalons = true;
			   }
		   }
	   }
	   
   }
   public void pivotUp(double speed){
	   if(intakePivot != null) {
		   intakePivot.set(ControlMode.PercentOutput, speed);
	   } else { 
		   
		  logger.info("Tried to pivot up - no CANTalons!");
	   }
   }
   
   public void pivotDown(double speed){
	   if(intakePivot != null) {
		   intakePivot.set(ControlMode.PercentOutput, -speed);
	   } else {
		  logger.info("Tried to pivot down - no CANTalons!");
	   }
   }
   
   //clamps cube
   public void clampCube () {
	   if(intakeClamperSolenoid != null) {
	   intakeClamperSolenoid.set(Value.kForward);
	   haveCube = true;
	   } else {
		  logger.info("Tried to clamp - no solenoid!");
	   }
   }
   
   //releases clamp
   public void clampRelease() {
	   if(intakeClamperSolenoid != null) {
		   intakeClamperSolenoid.set(Value.kReverse);
		   haveCube = false;
	   } else {
		  logger.info("Tried to unclamp - no solenoid!");
	   }
	   
   }
   
   public void findPivotAngle() {
	   double encoderPositon = readEncoder();
	   pivotAngleDeg = (encoderPositon * (180/encoderAt180)) + startingPivotAngle;
	   pivotAngle = pivotAngleDeg * (Math.PI/180); //in radians
	   SmartDashboard.putNumber("Pivot Angle in radians", pivotAngle);
	   	   
   }
   public void findFinalSpeed() {
	   cosMultiplier = Math.cos(pivotAngle);
	   finalSpeed = maxPivotSpeed * cosMultiplier;
	   
   }
   
   public void trigonPivotDown() {
	   if (intakePivot != null) {
		  // if (isEncoderValid) {
			   if (readEncoder() < bottomSetPoint - 200) {
				   findPivotAngle();
				   findFinalSpeed();
				   logger.info("ready to set " + finalSpeed);
				   
				   intakePivot.set(ControlMode.PercentOutput, finalSpeed);
				   logger.info("Started robot at " + finalSpeed);
				   
			   }
			   else {
				   logger.info("We are at setpoint!");
			   }
		  // }
		  // else {
		//	   logger.info("Tried to pivot up, but we reached home button!");
		 //  }
	   }
	   else {
		   
		   logger.info("Tried to pivot down, no CANTalons!");
	   }
   }
   
   public void trigonPivotUp() {
	   if (intakePivot != null) {
		   if (readEncoder() > homeSetPoint + encoderErrorMargin) {
			   findPivotAngle();
			   findFinalSpeed();
			   intakePivot.set(ControlMode.PercentOutput, -(finalSpeed));
		   }
		   else {
			   intakePivot.set(ControlMode.PercentOutput, 0);
			   logger.info("We are at setpoint!");
		   }   
	   }
	   else {
		   logger.info("Tried to pivot down, no CANTalons!");
	   }
   }
   
   @Override
   public void periodic() {
	   if (intakePivot != null) {
		   SmartDashboard.putNumber("Pivot current output: ", intakePivot.getOutputCurrent());
		   SmartDashboard.putNumber("Pivot Encoder Position: ", readEncoder());
		   findPivotAngle();
		   findFinalSpeed();
		   SmartDashboard.putNumber("Pivot Angle in radians", pivotAngle);
		   SmartDashboard.putNumber("Pivot Angle in degrees", pivotAngleDeg);
		   SmartDashboard.putNumber("Cosine multipliers", cosMultiplier);
		   SmartDashboard.putNumber("Final Speed", finalSpeed);
	   }
   }
   
}


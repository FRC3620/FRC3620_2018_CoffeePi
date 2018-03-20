package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.ManualCubeCommand;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

	public static final int kSpeedPositionLoopIdx = 0;
	public static final int kTimeoutMs = 0;
	public static final boolean kMotorInvert = false;
	public static int peakSpeedHigh = 0;
	public static int positionErrorMargin = 50;
	public int homeSetPoint = 0;
	public int bottomSetPoint = 1330;
	public double encoderAt180 = 1330;
	//public double encoderAt90 = 330;
	//public double startingPivotAngle;
	public double maxPivotSpeed = 0.3; //WAS 0.3 CHANGED UNTIL ENCODER IS FIXED
	public double encoderErrorMargin = 50;
	public double pivotAngleDeg;
	public double cosMultiplier;
	public double finalSpeed;
	public boolean haveCube;
	public boolean gotCompBot;
	
	
	public IntakeSubsystem() {
		super();
		logger.info("Initializing intake subsystem");
		if(intakePivot != null) {			
			// Setting feedback device type
			intakePivot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, kSpeedPositionLoopIdx, 0);
			intakePivot.setSensorPhase(false);
			intakePivot.setNeutralMode(NeutralMode.Brake);
		}
		resetEncoder();
		gotCompBot = RobotMap.practiceBotJumper.get();
	}
	
	public boolean isEncoderValid;
	public boolean isArmDown;
	
	public boolean getEncoderIsValid(){
		return isEncoderValid;
	}
	
	public boolean homeButtonIsPressed() {
		if (intakePivot != null) {
    		//Should be reverse limit but we're hoping this works.
			if (intakePivot.getSensorCollection().isRevLimitSwitchClosed()) {
				isArmDown = false;
				return true;
			}
		}
			else {
    		return false;
    	}
    	return false; 
	}
	
	public boolean gotCompBot() {
		return gotCompBot;
	}
	
	
	double readEncoder() {
		
		if(intakePivot != null) {
			int encoderPos = intakePivot.getSelectedSensorPosition(kSpeedPositionLoopIdx);
			
			return encoderPos;
		}
		
		return 0;
	}
	
	public void resetEncoder() {
		if(intakePivot != null) {

			intakePivot.setSelectedSensorPosition(0, kSpeedPositionLoopIdx, 10);
			
			//logger.info("Resetting Encoder!");
		}
	}

	/*public void movePivotDown(int position) {
		
		if (isEncoderValid) {
			if (intakePivot != null) {
				if(readEncoder()< position) {
					intakePivot.set(ControlMode.Position, position);
				}
			}
		}
		
	}*/

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
		   if(speed == 0) {
			   intakePivot.neutralOutput();
		   }
		   else {
			   intakePivot.set(ControlMode.PercentOutput, speed);
		   }
	   } else { 
		   
		  logger.info("Tried to pivot up - no CANTalons!");
	   }
   }
   
   public void pivotDown(double speed){
	   if(intakePivot != null) {
		   if(speed == 0) {
			   intakePivot.neutralOutput();
		   }
		   else {
			   intakePivot.set(ControlMode.PercentOutput, -speed);
		   }
	   } else {
		  logger.info("Tried to pivot down - no CANTalons!");
	   }
   }
   
   //clamps cube
   public void clampCube () {
	   if(intakeClamperSolenoid != null) {
	   intakeClamperSolenoid.set(Value.kForward);
	   haveCube = true;
	   
	   //LightSubsystem Call
		//new LightSubsystem().setEvent("cube", true);
	   } else {
		  logger.info("Tried to clamp - no solenoid!");
	   }
   }
   
   //releases clamp
   public void clampRelease() {
	   if(intakeClamperSolenoid != null) {
		   intakeClamperSolenoid.set(Value.kReverse);
		   haveCube = false;
		   
		   //LightSubsystem Call
		   //new LightSubsystem().setEvent("lift", false);
	   } else {
		  logger.info("Tried to unclamp - no solenoid!");
	   }
	   
   }
   
   public boolean isClampClosed() {
	   if (intakeClamperSolenoid != null) {
		   Value intakeStatus = intakeClamperSolenoid.get(); 
		//   logger.info("Clamper status: " + intakeStatus);
		    if (intakeStatus == Value.kForward) {
		    //	logger.info("Clamper is closed");
		    	return true;
		    }
	   }
		   logger.info("Clamper is not closed");
		   return false;  
   }
 
   public double readPivotAngleInDegress() {
	   double encoderPositon = readEncoder();
	   double pivotAngleDeg = (encoderPositon * (180.0/encoderAt180));
	   if(pivotAngleDeg >= 120) {
			isArmDown = true;
		//int encoderPos = intakePivot.getSensorCollection().getQuadraturePosition();
		
		
		}
	   return pivotAngleDeg;
   }
   
   @Override
   public void periodic() {
	   if (homeButtonIsPressed()) {
			if (!isEncoderValid) {
				logger.info("Pivot home, reseting encoder");
			}
			isEncoderValid = true;
			resetEncoder();
		}
	   if (intakePivot != null) {
		   SmartDashboard.putNumber("Pivot current output: ", intakePivot.getOutputCurrent());
		   SmartDashboard.putNumber("Pivot Encoder Position: ", readEncoder());
		   SmartDashboard.putNumber("Pivot Angle in degrees", readPivotAngleInDegress());
		   SmartDashboard.putBoolean
		   ("Pivot home is pressed", homeButtonIsPressed());
		   SmartDashboard.putBoolean("Pivot encoder valid", isEncoderValid);
	   }
   }
   
}


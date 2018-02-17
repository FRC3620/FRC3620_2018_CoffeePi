package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.OI;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.ManualLiftOperatorCommand;
import org.usfirst.frc.team3620.robot.commands.TeleOpDriveCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
	private final WPI_TalonSRX talon = RobotMap.liftSubsystemTalon1;
    private final WPI_VictorSPX victor1 = RobotMap.liftSubsystemVictor2;
    private final WPI_VictorSPX victor2 = RobotMap.liftSubsystemVictor3;
    private final WPI_VictorSPX victor3 = RobotMap.liftSubsystemVictor4;

    private final DigitalInput elevatorHomeSwitch = RobotMap.liftSubsystemElevatorHomeSwitch;
    private final DigitalInput intakeInPos = RobotMap.liftSubsystemIntakeInPos;
    private final DigitalInput intakeFacingBack = RobotMap.liftSubsystemIntakeFacingBack;
    private final DoubleSolenoid liftGearShifter = RobotMap.liftSubsystemGearShifter;
    
    public static final int kSlotIdxHigh = 0;
    public static final int kTimeoutMs = 0;
    public static final boolean kMotorInvert  = false;
    public static boolean isHome = false;
    public static final int homePosition = 0;
    public static final int scalePosition = 0;
    public static final int switchPosition = 0;
    public static double kPHigh = 0;
    public static double kIHigh = 0;
    public static double kDHigh = 0;
    public static double kFHigh = 0;
    public static double kIZonehigh = 0;
    public static int peakSpeedHigh = 0;
    public static int positionErrorMargin = 50;
    public static int motionMagicCruiseVel = 0;
    public static int motionMagicAccel = 0;
    
    
    public LiftSubsystem() {
    	super();
    	
    	talon.config_kF(kSlotIdxHigh, kFHigh, kTimeoutMs);
    	talon.config_kP(kSlotIdxHigh, kPHigh, kTimeoutMs);
    	talon.config_kI(kSlotIdxHigh, kIHigh, kTimeoutMs);
    	talon.config_kD(kSlotIdxHigh, kDHigh, kTimeoutMs);
    	talon.configMotionCruiseVelocity(kSlotIdxHigh, motionMagicCruiseVel);
    	talon.configMotionAcceleration(kSlotIdxHigh, motionMagicAccel);
		
		//Setting feedback device type
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
    		
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public void initDefaultCommand() {
        // Set the default000000000 command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ManualLiftOperatorCommand());
    }
    
    //reads encoder
    public double readEncoder() {
    	
    	int encoderPos = talon.getSensorCollection().getQuadraturePosition();
		return encoderPos;
    	
    }
    
    //resets encoder value
    public void resetEncoder() {
    	
    	int sensorPos = 0;
    	talon.setSelectedSensorPosition(sensorPos, kSlotIdxHigh, 10);
    }
    
    
    //moves elevator motor vertSpeed
    public void moveElevator(double joyPos) {
    	//runs lift motor for vertSpeed    	
    
    	talon.set(ControlMode.Velocity, joyPos * peakSpeedHigh);
    	
    }
    
    public void moveElevatorTestUp() {
    	talon.set(ControlMode.PercentOutput, .50);
    }
    public void moveElevatorTestDown() {
    	talon.set(ControlMode.PercentOutput, -.50);
    }
    
    //checks to see if lift is at lowest position
    public boolean isAtHome() {
    	
    	int encoderPos = talon.getSensorCollection().getQuadraturePosition();
    	if  (encoderPos > homePosition - positionErrorMargin && encoderPos < homePosition + positionErrorMargin) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public boolean isAtScale() {
    	
    	int encoderPos = talon.getSensorCollection().getQuadraturePosition();
    	if  (encoderPos > scalePosition - positionErrorMargin && encoderPos < scalePosition + positionErrorMargin) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public boolean isAtSwitch() {
    	
    	int encoderPos = talon.getSensorCollection().getQuadraturePosition();
    	if  (encoderPos > switchPosition - positionErrorMargin && encoderPos < switchPosition + positionErrorMargin) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public boolean getHomeSwitch() {
    	boolean home = elevatorHomeSwitch.get();
    	return home;
  
    }
    public void moveToScale() {
    	talon.set(ControlMode.MotionMagic, scalePosition);
    	
    }
    public void setHighGear() {
    	liftGearShifter.set(Value.kForward);
    }
    public void setLowGear() {
    	liftGearShifter.set(Value.kReverse); 
    	
    }
    public void deadenShifter() {
    	liftGearShifter.set(Value.kOff);
    }
}




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
    
    public static final int kSlotIdx = 0;
    public static final int kTimeoutMs = 0;
    public static final boolean kMotorInvert  = false;
    public static boolean isHome = false;
    public static final int homePosition = 0;
    public static final int scalePosition = 0;
    public static final int switchPosition = 0;
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double kIZone = 0;
    public static int peakSpeed = 0;
    public static int positionErrorMargin = 50;
    
    
    public LiftSubsystem() {
    	super();
    	
    	kP = 0;
		kI = 0;
		kD = 0;
		kF = 0;
		
		//Setting feedback device type
		talon.set(ControlMode.Position, 0);
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
    	talon.setSelectedSensorPosition(sensorPos, kSlotIdx, 10);
    }
    
    
    //moves elevator motor vertSpeed
    public void moveElevator(double joyPos) {
    	//runs lift motor for vertSpeed    	
    
    	talon.set(ControlMode.Velocity, joyPos * peakSpeed);
    	
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
}


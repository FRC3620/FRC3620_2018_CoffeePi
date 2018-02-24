package org.usfirst.frc.team3620.robot.subsystems;

import org.usfirst.frc.team3620.robot.OI;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.HoldLift;
import org.usfirst.frc.team3620.robot.commands.ManualLiftOperatorCommand;
import org.usfirst.frc.team3620.robot.commands.TeleOpDriveCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	public static final int kSpeedPIDLoopIdx = 0;
	public static final int kTimeoutMs = 0;
	public static final boolean kMotorInvert = false;
	public static boolean isHome = false;
	public static final int homePosition = 0;
	public static final int scalePosition = 4949;
	public static double kPSpeed = 0;
	public static double kISpeed = 0;
	public static double kDSpeed = 0;
	public static double kFSpeed = .65;
	public static double kIZoneSpeed = 0;
	public static double peakSpeedHigh = 0.60;
	public static double lowestSpeed = 0.03;
	public static int positionErrorMargin = 50;
	public static int motionMagicCruiseVel;
	public static int motionMagicAccel;
	public static double bracingVoltage = 0.08;
	public static double peakVoltageHigh = peakSpeedHigh - bracingVoltage;
	public static double minVoltageHigh = bracingVoltage - lowestSpeed;
	

	public LiftSubsystem() {
		super();

		talon.config_kF(kSpeedPIDLoopIdx, kFSpeed, kTimeoutMs);
		talon.config_kP(kSpeedPIDLoopIdx, kPSpeed, kTimeoutMs);
		talon.config_kI(kSpeedPIDLoopIdx, kISpeed, kTimeoutMs);
		talon.config_kD(kSpeedPIDLoopIdx, kDSpeed, kTimeoutMs);
		
			//Setting feedback device type
			talon.set(ControlMode.Position, 0);
			talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
			
			// Setting feedback device type
			talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			talon.setSensorPhase(true);
			
	}
		// Put methods for controlling this subsystem
		// here. Call these from Commands.
    
    public void moveElevatorTestUp() {
    	if (talon != null) {
    		talon.set(ControlMode.PercentOutput, .50);
    	}
    }
    public void moveElevatorTestDown() {
    	if (talon != null) {
    		talon.set(ControlMode.PercentOutput, -.50);
    	}
    }
    
    //checks to see if lift is at lowest position
    public boolean isAtHome() {
    	
    	if (talon != null) {
    		int encoderPos = talon.getSensorCollection().getQuadraturePosition();
    		if  (encoderPos > homePosition - positionErrorMargin && encoderPos < homePosition + positionErrorMargin) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	return true; //we are faking it
    }
    
    public boolean isBottomLimitDepressed(){
    	
    	if (talon != null) {
    		
    		return talon.getSensorCollection().isRevLimitSwitchClosed();
    		
    	}
    	return false; 
    	
    }
    
    public boolean isTopLimitDepressed(){
    	
    	if (talon != null) {
    		
    		return talon.getSensorCollection().isFwdLimitSwitchClosed();
    		
    	}
    	return false; 
    	
    }
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default000000000 command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new HoldLift());
	}

	// reads encoder
	public double readEncoder() {

		int encoderPos = talon.getSensorCollection().getQuadraturePosition();
		return encoderPos;

	}

	// resets encoder value
	public void resetEncoder() {

		int sensorPos = 0;
		talon.setSelectedSensorPosition(sensorPos, kSpeedPIDLoopIdx, 10);
	}

	// moves elevator motor vertSpeed
	public void moveElevatorUp(double joyPos) {
		// runs lift motor for vertSpeed

		talon.set(ControlMode.PercentOutput, bracingVoltage + (joyPos * peakVoltageHigh));

	}
	public void moveElevatorDown(double joyPos) {
		talon.set(ControlMode.PercentOutput, bracingVoltage - (minVoltageHigh*joyPos));
	}

	public void moveElevatorTestUp(double voltage) {
		talon.set(ControlMode.PercentOutput, voltage);
	}

	public void moveElevatorTestDown(double voltage) {
		talon.set(ControlMode.PercentOutput, voltage);
	}

	public void setElevatorVelocity(double speed) {
		talon.set(ControlMode.Velocity, speed);

	}
	
	public void fallSlowly() {
		talon.set(ControlMode.PercentOutput, 0.08);
	}

	

	public boolean isAtScale() {

		int encoderPos = talon.getSensorCollection().getQuadraturePosition();
		if (encoderPos > scalePosition - positionErrorMargin && encoderPos < scalePosition + positionErrorMargin) {
			return true;
		} else {
			return false;
		}
	}

	/*
	public boolean isHomeSwitchDepressed() {
		boolean home = elevatorHomeSwitch.get();
		return home;

	}
	*/

	public void moveToScale() {
		talon.set(ControlMode.MotionMagic, scalePosition);

	}

	public void setHighGear() {
		liftGearShifter.set(Value.kReverse);
	}

	public void setLowGear() {
		liftGearShifter.set(Value.kForward);

	}

	public void deadenShifter() {
		liftGearShifter.set(Value.kOff);
	}

	double lastSetPoint = 0;
	boolean weAreCalibrated = false;

	@Override
	public void periodic() {
		/*
		if (!weAreCalibrated) {
			if (isHomeSwitchDepressed()) {
				resetEncoder();
				calculateNewPIDParameters();
				talon.set(ControlMode.Position, lastSetPoint);
				weAreCalibrated = true;
			} else {
				// not home yet, creep down
				talon.set(ControlMode.PercentOutput, 0.1);
			}
		}*/
		SmartDashboard.putBoolean("Lift Bottom limit", isBottomLimitDepressed());
		SmartDashboard.putBoolean("Lift Top limit", isTopLimitDepressed());
		SmartDashboard.putNumber("Lift encoder position: ", readEncoder());

		if (talon != null) {
			SmartDashboard.putNumber("Lift Talon 1 Current Output: ", talon.getOutputCurrent());
		}
		if (talon != null) {
			SmartDashboard.putNumber("Lift Talon 1 Percent Output: ", talon.getMotorOutputVoltage());
		}
		SmartDashboard.putNumber("Lift Joystick Value", Robot.m_oi.getLiftJoystick());
	}

	public void setSetpoint(double positionInInches) {
		// TODO do this calculation correctly
		double ticks = 85.33 * positionInInches;
		lastSetPoint = ticks;
		if (weAreCalibrated) {
			calculateNewPIDParameters();
			talon.set(ControlMode.Position, lastSetPoint);
		}
	}
	
	public void calculateNewPIDParameters() {
		// look at the current position and lastSetPoint,
		if (readEncoder() > lastSetPoint) {
			
		}
		// if we are going up or down, and load the correct PID
		// parameters into the Talon.
		//
		// should probably zero out any integrators in the PID? 
	}

	public void setPIDParameters(double P, double I, double D, double F) {
		
		talon.config_kP(0, P, 10);
		talon.config_kI(0, I, 10);
		talon.config_kD(0, D, 10);
		talon.config_kF(0, F, 10);
		
		
		
	}
	
	public void configMotionMagic(int acceleration, int velocity) {
		
		motionMagicAccel = acceleration;
		motionMagicCruiseVel = velocity;
		
		talon.configMotionCruiseVelocity(kSpeedPIDLoopIdx, motionMagicCruiseVel);
		talon.configMotionAcceleration(kSpeedPIDLoopIdx, motionMagicAccel);
		
	}
	
	public void moveToSetPoint(int position) {
		
		talon.set(ControlMode.MotionMagic, position);
		
	}
	
	public void moveAtManualSpeedGiven(double speed) {
		
		talon.set(ControlMode.PercentOutput, -speed);
	}
	
	public void brace() {
		
		talon.set(ControlMode.PercentOutput, bracingVoltage);
	}

}



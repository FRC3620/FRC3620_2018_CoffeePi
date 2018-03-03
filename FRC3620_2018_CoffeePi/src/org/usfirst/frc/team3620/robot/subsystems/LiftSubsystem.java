package org.usfirst.frc.team3620.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.HoldLift;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	private final WPI_TalonSRX talon = RobotMap.liftSubsystemTalon1;
	private final DoubleSolenoid liftGearShifter = RobotMap.liftSubsystemGearShifter;
	private boolean gotCompBot;

	public final int kSpeedPIDLoopIdx = 0;
	public final int kTimeoutMs = 0;
	public  final boolean kMotorInvert = false;
	public boolean isHome = false;
	public final int homePosition = 0;
	public  final int scalePosition = 4949;
	public double kPSpeed = 0;
	public double kISpeed = 0;
	public double kDSpeed = 0;
	public  double kFSpeed = 0;
	public  double kIZoneSpeed = 0;
	public double peakSpeedHigh = 0.60;
	public double lowestSpeed = 0.01;
	public int positionErrorMargin = 50;
	public int motionMagicCruiseVel;
	public int motionMagicAccel;
	public final double bracingVoltage = 0.08;
	public final double peakVoltageHigh = peakSpeedHigh - bracingVoltage;
	public final double minVoltageHigh = bracingVoltage - lowestSpeed;
	

	public LiftSubsystem() {
		super();
		if (talon != null) {
			talon.config_kF(kSpeedPIDLoopIdx, kFSpeed, kTimeoutMs);
			talon.config_kP(kSpeedPIDLoopIdx, kPSpeed, kTimeoutMs);
			talon.config_kI(kSpeedPIDLoopIdx, kISpeed, kTimeoutMs);
			talon.config_kD(kSpeedPIDLoopIdx, kDSpeed, kTimeoutMs);
		
			//Setting feedback device type
			setLiftTalon(ControlMode.Position, 0);
			talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
			
			// Setting feedback device type
			talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			talon.setSensorPhase(true);
			
		
		}
		gotCompBot = RobotMap.practiceBotJumper.get();
	}
		// Put methods for controlling this subsystem
		// here. Call these from Commands.
    //checks to see if lift is at lowest position
    public boolean isAtHome() {
    	if (talon != null) {
    		int encoderPos = readEncoderInTics();
    		if  (encoderPos > homePosition - positionErrorMargin && encoderPos < homePosition + positionErrorMargin) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	return true; //we are faking it
    }
    
	// reads encoder
	public int readEncoderInTics() {
		if (talon != null) {
			int encoderPos = (talon.getSelectedSensorPosition(kSpeedPIDLoopIdx));
			return encoderPos;
		}
		return 0;
	}

	public double readEncoderInInches() {
		double conversionFactor = 1.0/73.02;
		double competitionMultiplier = 0.5;
		double encoderPosInInches;
		if(gotCompBot == true) {
			encoderPosInInches = competitionMultiplier*conversionFactor*-readEncoderInTics();
			return encoderPosInInches;
		} else {
			encoderPosInInches = conversionFactor*-readEncoderInTics();
			return encoderPosInInches;
		}
		
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

	// resets encoder value
	public void resetEncoder() {
		if (talon != null) {
			int sensorPos = 0;
			talon.setSelectedSensorPosition(sensorPos, kSpeedPIDLoopIdx, 10);
		}
	}

	// moves elevator motor vertSpeed
	public void moveElevatorUp(double joyPos) {
		// runs lift motor for vertSpeed
		setLiftTalon(ControlMode.PercentOutput, bracingVoltage + (joyPos * peakVoltageHigh));
	}
	
	public void moveElevatorDown(double joyPos) {
		setLiftTalon(ControlMode.PercentOutput, bracingVoltage - (minVoltageHigh*joyPos));
	}
	
	public void autoMoveElevatorUp(double voltage) {
		setLiftTalon(ControlMode.PercentOutput, voltage);
	}

	public void autoMoveElevatorDown(double voltage) {
		setLiftTalon(ControlMode.PercentOutput, voltage);
	}

	public void setElevatorVelocity(double speed) {
		setLiftTalon(ControlMode.PercentOutput, speed);
	}
	
	public void fallSlowly() {
		setLiftTalon(ControlMode.PercentOutput, 0.08);
	}
//All this does is use the math library to add an arccosh function to our repertoire for the hyperbolic calculation
	public double calculateArcCosH(double input) {
		double output = Math.log1p((input + Math.sqrt((input*input) -1)) - 1);
		return output;
	}
			
	
	/* Welcome to the mathematical magic. The hyperbolic secant function is a nice bell curve, which is why we're
	 * using it to find percent output so we don't go anywhere TOO fast. Finding k and the equation for the return
	 * statement can be easily verified by starting with equating the percent output with a hyperbolic secant
	 * function that has ((x-point)/k) inside of it; this is to translate the function over to a positive domain 
	 * and then scale it for our purposes.
	 * 
	 * What is returned should fall within the desiredStartingPower and 1. I apologize for the parameters.
	 * 
	 * The purpose of this function is to allow one to change the specific parameters of how the lift moves
	 * (i.e. where it's supposed to go, it's max speed in going there, etc.) without having to recalculate
	 * everything oneself and plugging everything in -- such an approach will eventually screw you over.
	 * 
	 * Hopefully, one shouldn't have to touch this. Now, you can go back to AutoMoveLiftUp: 59
	 */
	public double calculatePowerHyperbolic(double power, double x, double startingPoint, double point, double maxPower){
		double k = ((startingPoint - point)/(calculateArcCosH(maxPower/power)));
		return (maxPower/(Math.cosh((x - point)/k)));
	}

	

	public boolean isAtScale() {
		if (talon != null) {
			int encoderPos = readEncoderInTics();
			if (encoderPos > scalePosition - positionErrorMargin && encoderPos < scalePosition + positionErrorMargin) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/*
	public boolean isHomeSwitchDepressed() {
		boolean home = elevatorHomeSwitch.get();
		return home;

	}
	*/

	public void moveToScale() {
		setLiftTalon(ControlMode.MotionMagic, scalePosition);
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
		SmartDashboard.putBoolean("Lift Top limit", false);
		SmartDashboard.putNumber("Lift encoder position: ", readEncoderInTics());
		SmartDashboard.putNumber("Lift Encoder in Inches", readEncoderInInches());

		if (talon != null) {
			SmartDashboard.putNumber("Lift Talon 1 Current Output: ", talon.getOutputCurrent());
		}
		if (talon != null) {
			SmartDashboard.putNumber("Lift Talon 1 Percent Output: ", talon.getMotorOutputVoltage());
		}
		SmartDashboard.putNumber("Lift Joystick Value", Robot.m_oi.getLiftJoystick());
	
	//	SmartDashboard.putNumber("Lift Percent Output",)
	}

	public void setSetpoint(double positionInInches) {
		// TODO do this calculation correctly
		double ticks = 85.33 * positionInInches;
		lastSetPoint = ticks;
		if (weAreCalibrated) {
			if (talon != null) {
				calculateNewPIDParameters();
				setLiftTalon(ControlMode.Position, lastSetPoint);
			}
		}
	}
	
	public void setPosition(double desiredEncoderPos) {
		setLiftTalon(ControlMode.Position, desiredEncoderPos);
	}
	
	public void calculateNewPIDParameters() {
		// look at the current position and lastSetPoint,
		if (readEncoderInTics() > lastSetPoint) {
			
		}
		// if we are going up or down, and load the correct PID
		// parameters into the Talon.
		//
		// should probably zero out any integrators in the PID? 
	}

	public void setPIDParameters(double P, double I, double D, double F) {
		if (talon != null) {
			talon.config_kP(0, P, 10);
			talon.config_kI(0, I, 10);
			talon.config_kD(0, D, 10);
			talon.config_kF(0, F, 10);
		}
	}
	
	public void configMotionMagic(int acceleration, int velocity) {
		
		motionMagicAccel = acceleration;
		motionMagicCruiseVel = velocity;
		if (talon != null) {
			talon.configMotionCruiseVelocity(kSpeedPIDLoopIdx, motionMagicCruiseVel);
			talon.configMotionAcceleration(kSpeedPIDLoopIdx, motionMagicAccel);
		}
	}
	
	public void moveToSetPoint(int position) {
			setLiftTalon(ControlMode.MotionMagic, position);
	}
	
	public void moveAtManualSpeedGiven(double speed) {
			setLiftTalon(ControlMode.PercentOutput, -speed);
	}
	
	public void brace(double addedBangBangPower) {
			setLiftTalon(ControlMode.PercentOutput, bracingVoltage);
	}
	
	void setLiftTalon(ControlMode controlMode, double value) {
		if (talon != null) {
			talon.set(controlMode, value);
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			StackTraceElement command = stackTrace[3];
			CommandRecord commandRecord = new CommandRecord(controlMode, value, command);
			recording.add(commandRecord);
			lastPower = value;
			SmartDashboard.putNumber("liftPower", value);
		}
	}
	
	public class CommandRecord {
		public CommandRecord(ControlMode controlMode, double value, StackTraceElement where) {
			this.controlMode = controlMode;
			this.value = value;
			this.where = where;
		}
		ControlMode controlMode;
		double value;
		StackTraceElement where;
		@Override
		public String toString() {
			return "CommandRecord [controlMode=" + controlMode + ", value=" + value + ", where = " + where.getClassName() + "." + where.getMethodName() + "()]";
		}
	}
	
	List<CommandRecord> recording = new ArrayList<>();
	double lastPower = 0;
	
	public void beginPeriodic() {
	    recording.clear();	
	}

	public void endPeriodic() {
		if (recording.size() > 1) {
			logger.warn ("lift got set too much: {}", recording);
		}
		//System.out.println (recording + " " /* + getCurrentPower() */);
		
	}
	
/*	double getCurrentPower() {
		if (talon != null) {
			return talon.get();
		}
		return 0;
	} */
}
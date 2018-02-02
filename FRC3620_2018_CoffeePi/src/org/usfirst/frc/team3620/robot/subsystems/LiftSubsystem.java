package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.ManualLiftOperatorCommand;
import org.usfirst.frc.team3620.robot.commands.TeleOpDriveCommand;

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
    private final SpeedControllerGroup climbSpeedControllerGroup = RobotMap.liftSubsystemClimbSpeedControllerGroup;
    private final DigitalInput elevatorHomeSwitch = RobotMap.liftSubsystemElevatorHomeSwitch;
    private final DigitalInput intakeInPos = RobotMap.liftSubsystemIntakeInPos;
    private final DigitalInput intakeFacingBack = RobotMap.liftSubsystemIntakeFacingBack;
    
    public static final int kSlotIdx = 0;
    public static final int kTimeoutMs = 0;
    public static final boolean kMotorInvert  = false;
    public static final int homePosition = 0;
    public static final int maxPosition = 0;
    
    public LiftSubsystem() {
    	super();
    	
    	double kP = 0;
		double kI = 0;
		double kD = 0;
		double kF = 0;
		
		//Setting feedback device type
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		
		//Set SPX's as slaves
		victor1.follow(talon);
		victor2.follow(talon);
		victor3.follow(talon);
		
		

	
    		
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

   

    
    public void initDefaultCommand() {
        // Set the default000000000 command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ManualLiftOperatorCommand());
    }
    
    //reads encoder
    double readEncoder() {
    	
    	int encoderPos = talon.getSensorCollection().getQuadraturePosition();
		return encoderPos;
    	
    }
    
    //resets encoder value
    void resetEncoder() {
    	
    	int sensorPos = 0;
    	talon.setSelectedSensorPosition(sensorPos, kSlotIdx, 10);
    }
    
    public void moveLiftToSetpoint(double liftPosition, double p, double i, double d, double f, int iZone, int talonIDX) {
    	
    	
    	talon.config_kP(talonIDX,p,0);
    	talon.config_kI(talonIDX,i,0);
    	talon.config_kD(talonIDX,d,0);
    	talon.config_IntegralZone(talonIDX, iZone, 0);
    	
    	//talon.getClosedLoopTarget(pidIdx)
    	
    }
    
    //moves elevator motor vertSpeed
    public void moveElevator(double vertSpeed) {
    	//runs lift motor for vertSpeed
    	climbSpeedControllerGroup.set(vertSpeed);
    	
    }
    
    
    
    //checks to see if lift is at lowest position
    boolean isAtHome() {
    	
    	return true;
    	
    }
}


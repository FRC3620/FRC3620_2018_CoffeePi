package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {
	private final DigitalInput cubeIntook = RobotMap.intakeSubsystemCubeIntook;
    private final DigitalInput scaleSensed = RobotMap.intakeSubsystemScaleSensed;
    private final SpeedController intakeRoller1 = RobotMap.intakeSubsystemIntakeRoller1;
    private final SpeedController intakeRoller2 = RobotMap.intakeSubsystemIntakeRoller2;
    private final static WPI_TalonSRX intakePivot = RobotMap.intakeSubsystemIntakePivot;
    
    public static final int kSlotIdx = 0;
    public static final int kTimeoutms = 0;
    public static final boolean kMotorInvert = false;
    public static boolean isHome = false;
    public static boolean isMax = false;
    public static final int homePosition = 0;
    public static final int maxPostion = 0;
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double kIZone = 0;
  
    
    public IntakeSubsystem() {
    	super();
    	
    	kP = 0;
    	kI = 0;
    	kD = 0;
    	kF = 0;
    	
    	intakePivot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
    }
   
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //bring the cube in by spinning the motors backwards
   public void bringCubeIn(double intakeSpeed){
    	intakeRoller1.set(-intakeSpeed);
    	intakeRoller2.set(intakeSpeed);
    	
    }
   
   public void pivot() {
	   
   }
   
   //push cube out by spinning motor out
   public void pushCubeOut(double intakeSpeed) {
	intakeRoller1.set(intakeSpeed);
   	intakeRoller2.set(-intakeSpeed);
	   
   }
   public boolean checkHome() {
	   int readEncoder = intakePivot.getSensorCollection().getQuadraturePosition(); 
	   if(readEncoder == homePosition) {
		   isHome = true; 
	   }
	   else {
		   isHome = false;
	   }
	   return isHome; 
   }
   public boolean checkMax() {
	   int readEncoder = intakePivot.getSensorCollection().getQuadraturePosition(); 
	   if (readEncoder == maxPostion) {
		   isMax = true;
	   }
	   else {
		   isMax = true;
	   }
	   return isMax; 
   }
   public void moveToHome() {
	    
   }
}


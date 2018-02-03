package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.IntakeCubeCommand;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.slf4j.Logger;
/**
 *
 */
public class IntakeSubsystem extends Subsystem {
	private final DigitalInput cubeIntook = RobotMap.intakeSubsystemCubeIntook;
    private final DigitalInput scaleSensed = RobotMap.intakeSubsystemScaleSensed;
    private final SpeedController intakeRoller1 = RobotMap.intakeSubsystemIntakeRoller1;
    private final SpeedController intakeRoller2 = RobotMap.intakeSubsystemIntakeRoller2;
    private final WPI_TalonSRX intakePivot = RobotMap.intakeSubsystemIntakePivot;
    private final DoubleSolenoid intakeClamperSolenoid = RobotMap.intakeSubsystemIntakeClamperSolenoid;
    private final 
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

   
    
    public static final int kSlotIdx = 0;
    public static final int kTimeoutms = 0;
    public static final boolean kMotorInvert = false;
    public static boolean isHome = false;
    public static boolean isMax = false;
    public static final int homePosition = 0;
    public static final int maxPosition = 8300;
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double kIZone = 0;
  
    
    public IntakeSubsystem() {
    	super();
    	
    	//set motor sensor
    	intakePivot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
    	intakePivot.setSensorPhase(true);
    	intakePivot.setInverted(false);
    	
    	//set PID values
    	intakePivot.config_kF(kSlotIdx, kF, kTimeoutms);
    	intakePivot.config_kP(kSlotIdx, kP, kTimeoutms);
    	intakePivot.config_kI(kSlotIdx, kI, kTimeoutms);
    	intakePivot.config_kD(kSlotIdx, kD, kTimeoutms);
    	intakePivot.config_kF(kSlotIdx, kF, kTimeoutms);
    	
    }
   

    

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    //bring the cube in by spinning the motors backwards
   public void bringCubeIn(double intakeSpeed){
	   if(intakeRoller1 != null) {
	    	intakeRoller1.set(-intakeSpeed);
	    	intakeRoller2.set(intakeSpeed); 
	   } else {
		  logger.info("Tried to bring in cube - no CANTalons!");
	   }
    }
   
   public void pivotUp(double position){
	   
	   
	   if(intakePivot != null) {
		   while (position < maxPosition) {
			   intakePivot.set(ControlMode.Position, position);
			   logger.info("Moving pivot Up");
		   }
		   logger.info("Pivot at max position");
		   
	   } else {
		  logger.info("Tried to pivot up - no CANTalons!");
	   }
   }
   
   public void pivotDown(double speed){
	   if(intakePivot != null) {
		   intakePivot.set(-speed);
	   } else {
		  logger.info("Tried to pivot down - no CANTalons!");
	   }
   }
   
   //push cube out by spinning motor out
   public void pushCubeOut(double intakeSpeed) {
	   if(intakeRoller1 != null) {
			intakeRoller1.set(intakeSpeed);
		   	intakeRoller2.set(-intakeSpeed);
	   } else {
		  logger.info("Tried to push cube out - no CANTalons!");
	   }
	   
   }

   public boolean checkHome() {
	   int readEncoder = intakePivot.getSensorCollection().getQuadraturePosition(); 
	   if(readEncoder < homePosition + 50 && readEncoder > homePosition - 50) {
		   isHome = true;
	   }
	   else {
		   isHome = false;
	   }
	   return isHome; 
   }
   public boolean checkMax() {
	   int readEncoder = intakePivot.getSensorCollection().getQuadraturePosition(); 
	   if (readEncoder < maxPosition + 50 && readEncoder > maxPosition -50) {
		   isMax = true;
	   }
	   else {
		   isMax = false;
	   }
	   return isMax; 
   }
  
   public int getRawEncoderPosition() {
	   int readEncoder = intakePivot.getSensorCollection().getQuadraturePosition();
	   return readEncoder;
   }
   
   //clamps cube
   public void clampCube () {
	   if(intakeClamperSolenoid != null) {
	   intakeClamperSolenoid.set(Value.kForward);
	   } else {
		  logger.info("Tried to clamp - no solenoid!");
	   }
   }
   
   //releases clamp
   public void clampRelease() {
	   if(intakeClamperSolenoid != null) {
		   intakeClamperSolenoid.set(Value.kReverse);
	   } else {
		  logger.info("Tried to unclamp - no solenoid!");
	   }
	   
   }
   
   

}


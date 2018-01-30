package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.IntakeCubeCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PWMTalonSRX;
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
    private final WPI_TalonSRX intakePivot = RobotMap.intakeSubsystemIntakePivot;
    private final DoubleSolenoid intakeClamperSolenoid = RobotMap.intakeSubsystemIntakeClamperSolenoid;

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
   
   public void pivotUp(double speed){
	   intakePivot.set(speed);
   }
   
   public void pivotDown(double speed){
	   intakePivot.set(-speed);
   }
   
   //push cube out by spinning motor out
   public void pushCubeOut(double intakeSpeed) {
	intakeRoller1.set(intakeSpeed);
   	intakeRoller2.set(-intakeSpeed);
	   
   }
   
   //clamps cube
   public void clampCube () {
	   intakeClamperSolenoid.set(Value.kForward);
   }
   
   //releases clamp
   public void clampRelease() {
	   intakeClamperSolenoid.set(Value.kReverse);
	   
   }
   
   
}


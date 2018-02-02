package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
	private final WPI_TalonSRX talonSRX7 = RobotMap.liftSubsystemTalon1;
    private final WPI_VictorSPX victorSPX8 = RobotMap.liftSubsystemVictor2;
    private final WPI_VictorSPX victorSPX9 = RobotMap.liftSubsystemVictor3;
    private final DigitalInput elevatorHomeSwitch = RobotMap.liftSubsystemElevatorHomeSwitch;
    private final DigitalInput intakeInPos = RobotMap.liftSubsystemIntakeInPos;
    private final DigitalInput intakeFacingBack = RobotMap.liftSubsystemIntakeFacingBack;

    
  

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default000000000 command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //reads encoder
    double readEncoder() {
		return 0;
    	
    }
    
    //resets encoder value
    void resetEncoder() {
    	
    }
    
    //moves elevator motor vertSpeed
    void moveElevator(double vertSpeed) {
    	//runs lift motor for vertSpeed
    	// TODO FIX MEclimbSpeedControllerGroup.set(vertSpeed);
    	
    	
    }
    
    //checks to see if lift is at lowest position
    boolean isAtHome() {
    	return true;
    	
    }
}


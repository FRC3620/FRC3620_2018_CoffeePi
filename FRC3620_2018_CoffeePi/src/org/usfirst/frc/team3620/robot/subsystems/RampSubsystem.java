package org.usfirst.frc.team3620.robot.subsystems;

import org.usfirst.frc.team3620.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RampSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    Servo rampServo = RobotMap.rampServo;
    
    public void actuateRampServo() {
    	rampServo.setAngle(180);
    }
    
    public void resetRampServo() {
    	rampServo.setAngle(0);
    }
    
    public void disableRampServo() {
    	rampServo.setDisabled();
    }
}


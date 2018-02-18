package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToScale extends Command {

    public LiftToScale() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.liftSubsystem.setPIDParameters(0, 0, 0, 5.002);
    	Robot.liftSubsystem.configMotionMagic(102, 51);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.liftSubsystem.moveToSetPoint(4949);
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.liftSubsystem.isHomeSwitchDepressed() == true) {
        return true;
    
    	}
    	else {
    		return false;
    	}
    }	

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

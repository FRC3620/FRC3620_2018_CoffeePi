package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToHome extends Command {

    public LiftToHome() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 if(Robot.liftSubsystem.readEncoder() > -2048) {
    		 Robot.liftSubsystem.setElevatorVelocity(-0.20);
    	 }
    	 else {
    		 Robot.liftSubsystem.fallSlowly();
    	 }
    		
    	}
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.isBottomLimitDepressed() || (Robot.liftSubsystem.readEncoder() < -2048)) {
    		Robot.liftSubsystem.resetEncoder();
    		return true;
    	}
    	else {
    		return false;
    	}
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.liftSubsystem.brace(0.17);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.liftSubsystem.brace(0.17);
    }
}

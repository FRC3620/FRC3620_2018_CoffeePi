package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoMoveLiftDown extends Command {
	double encoderPos;
	double slowDownPoint;
    public AutoMoveLiftDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
	//1440 ticks = 16.875 inches
    protected void initialize() {
    	slowDownPoint = 1024;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoder();
    	if(encoderPos >= slowDownPoint) {
    		Robot.liftSubsystem.moveElevatorDown(0.04);
    		System.out.println("Coming down at speed");
    	}
    	else if(encoderPos < slowDownPoint && encoderPos > -171) {
    		Robot.liftSubsystem.moveElevatorDown(0.04 + ((slowDownPoint - encoderPos)/(slowDownPoint/0.05)));
    	} else if(encoderPos < -512) {
    		Robot.liftSubsystem.brace();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.isBottomLimitDepressed()) {
    		Robot.liftSubsystem.resetEncoder();
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.liftSubsystem.brace();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.liftSubsystem.brace();
    }
}
package org.usfirst.frc.team3620.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitJustALittle extends Command {
	double liftDelay;
	Timer timer = new Timer();
    public WaitJustALittle(double liftDelaySeconds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.liftDelay = liftDelaySeconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(timer.get() > 4) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

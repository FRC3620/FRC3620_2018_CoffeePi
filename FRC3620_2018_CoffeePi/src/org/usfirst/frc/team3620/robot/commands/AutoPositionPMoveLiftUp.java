package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/** Overall, hopefully this should prove to be a simple solution to autonomously moving the lift up.
 * 	However, the big danger is that Position PID doesn't have a great track record of accelerating smoothly and
 * 	decelerating to be right on target, leading to fluctuations, leading to bad things.
 * 
 * 	This should be able to be addressed using the D after P is all taken care of. But maybe not. The hyperbolic
 * 	secant will hopefully work; if not, then we need to be prepared to use this quickly.
 * 
 * The AutoPositionPMoveLiftDown is similar enough, except that its just a constant voltage. Actually, it's
 * worthless. Use AutoMoveLiftDown instead.
 *
 */
public class AutoPositionPMoveLiftUp extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double desiredEncoderPos;
	double encoderPos;
    public AutoPositionPMoveLiftUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Starting AutoPositionPMoveLiftUp Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoderInTics();
    	Robot.liftSubsystem.setPosition(desiredEncoderPos);
    	System.out.println("We're going to " + desiredEncoderPos);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if((encoderPos > (desiredEncoderPos - 50) && encoderPos < (desiredEncoderPos + 50)) || Robot.liftSubsystem.isTopLimitDepressed()){
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending AutoPositionPMoveLiftUp Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting AutoPositionPMoveLiftUp Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }
}

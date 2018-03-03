package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoPositionPMoveLiftDown extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double oneFootInTics;
	double encoderPos;
	double desiredPower;
    public AutoPositionPMoveLiftDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Starting AutoPositionPMoveLiftDown Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoderInTics();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if((encoderPos > (oneFootInTics - 50) && encoderPos < (oneFootInTics + 50)) || Robot.liftSubsystem.isTopLimitDepressed()){
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending AutoPositionPMoveLiftDown Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting AutoPositionPMoveLiftDown Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }
}

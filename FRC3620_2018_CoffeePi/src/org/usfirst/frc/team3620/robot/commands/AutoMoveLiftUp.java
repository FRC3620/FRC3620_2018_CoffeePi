package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.slf4j.Logger;
/**
 *
 */
public class AutoMoveLiftUp extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double encoderPos;
	int startingEncoderPos;
	double requestedEncoderPos;
	int oneFootInTics;
	double slowDownPoint = requestedEncoderPos - oneFootInTics;
	int speedUpPoint;
	double desiredStartingPower;
	double desiredEndingPower;
    public AutoMoveLiftUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    //1440 ticks of encoder = 16.875 inches
    protected void initialize() {
    	logger.info("Starting AutoMoveLiftUp Command");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending AutoMoveLiftUp Command");
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting AutoMoveLiftUp Command");
    	
    }
}

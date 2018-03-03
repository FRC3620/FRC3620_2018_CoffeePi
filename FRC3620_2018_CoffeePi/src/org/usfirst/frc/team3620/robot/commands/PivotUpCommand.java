package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotUpCommand extends Command {
	int lowerLiftWindowLimit;
	int upperLiftWindowLimit;
	double liftEncoderPos;
	double pivotEncoder;
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

    public PivotUpCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	EventLogging.commandMessage(logger);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	liftEncoderPos = Robot.liftSubsystem.readEncoderInTics();
    	pivotEncoder = Robot.intakeSubsystem.readEncoder();
    //	if(liftEncoderPos > upperLiftWindowLimit && liftEncoderPos < upperLiftWindowLimit) {
    	if (pivotEncoder > 300) {
    		Robot.intakeSubsystem.pivotUp(0.5);
    	}
    	else {
    		Robot.intakeSubsystem.pivotUp(.1);
    	}
    	//}
    /*	else {
    		Robot.intakeSubsystem.pivotUp(0);
    	} */
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
   /* 	if(liftEncoderPos > upperLiftWindowLimit) {
    		return true;
    	} else if(liftEncoderPos < lowerLiftWindowLimit) {
    		return true;
    	} */
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSubsystem.pivotUp(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intakeSubsystem.pivotUp(0);
    }
}

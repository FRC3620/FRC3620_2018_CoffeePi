package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotDownCommand extends Command {
	int lowerLiftWindowLimit;
	int upperLiftWindowLimit;
	double liftEncoderPos;
	double pivotEncoder;
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
    public PivotDownCommand() {
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
    	//if(liftEncoderPos > upperLiftWindowLimit && liftEncoderPos < upperLiftWindowLimit) {
    	if (pivotEncoder <1000) {
    		Robot.intakeSubsystem.pivotDown(0.4);
    	}
    	else if (pivotEncoder > 1000 && pivotEncoder < 1100) {
    		Robot.intakeSubsystem.pivotDown(0.1);
    	}
    	else {
    		Robot.intakeSubsystem.pivotDown(0);
    	}
   // 	}
    /*	else {
    		Robot.intakeSubsystem.pivotDown(0);
    	} */
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
   /* 	if(liftEncoderPos > upperLiftWindowLimit) {
    		return true;
    	} else if(liftEncoderPos < lowerLiftWindowLimit) {
    		return true;
    	} */
    	if (pivotEncoder > 900) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeSubsystem.pivotDown(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intakeSubsystem.pivotDown(0);
    }
}

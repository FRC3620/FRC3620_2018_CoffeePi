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
	double lowerLiftWindowLimit = 14.0;
	double upperLiftWindowLimit = 75.0;
	double liftEncoderPos;
	double pivotEncoder;
	boolean haveCube;
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
    	double pivotEncoder = Robot.intakeSubsystem.readPivotAngleInDegress();
    	haveCube = Robot.intakeSubsystem.haveCube;
    	liftEncoderPos = Robot.liftSubsystem.readEncoderInTics();
    	if (pivotEncoder > 85) {
    		Robot.intakeSubsystem.pivotUp(0.5);
    	}
    	else {
    		Robot.intakeSubsystem.pivotUp(0.35);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double pivotEncoder = Robot.intakeSubsystem.readPivotAngleInDegress();
    	if (pivotEncoder < 45) {
    		return true;
    	}

    	if(liftEncoderPos > upperLiftWindowLimit) {
    		return true;
    	} else if(liftEncoderPos < lowerLiftWindowLimit) {
    		return true;
    	}
    	if(haveCube == false) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	EventLogging.commandMessage(logger);
    	Robot.intakeSubsystem.pivotUp(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	EventLogging.commandMessage(logger);
    	Robot.intakeSubsystem.pivotUp(0);
    }
}
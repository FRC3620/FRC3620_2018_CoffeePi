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
	double maxPower;
	double desiredCutoffPower;
	double cutoffEncoderPos;
	double slowDownPoint;
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

    public PivotUpCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	EventLogging.commandMessage(logger);
    	maxPower = 0.5;
    	desiredCutoffPower = 0.10;
    	cutoffEncoderPos = 20;
    	slowDownPoint = 82;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double pivotEncoder = Robot.intakeSubsystem.readPivotAngleInDegress();
    	boolean isClampClosed = Robot.intakeSubsystem.isClampClosed();
    	
    	liftEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	if (isClampClosed) {
    		if(pivotEncoder <= slowDownPoint) {
    			Robot.intakeSubsystem.pivotUp(maxPower - ((maxPower - desiredCutoffPower)*((slowDownPoint - pivotEncoder)/(slowDownPoint - cutoffEncoderPos))));
    		} else {
    			Robot.intakeSubsystem.pivotUp(maxPower);
    		}
    	}else {
    			Robot.intakeSubsystem.pivotUp(0);
    		}
    	}
    	
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double pivotEncoder = Robot.intakeSubsystem.readPivotAngleInDegress();
    	boolean haveCube = Robot.intakeSubsystem.haveCube ;
    	boolean isClampClosed = Robot.intakeSubsystem.isClampClosed();
    
    	if (pivotEncoder < cutoffEncoderPos) {
    		return true;
    	}
    	if (!isClampClosed) {
    		return true;
    	}

    /*	if(liftEncoderPos > lowerLiftWindowLimit && liftEncoderPos < upperLiftWindowLimit) {
    		return true;
    	} */
    /*	if(haveCube == false) {
    		return true;
    	} */
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	EventLogging.commandMessage(logger);
    	Robot.intakeSubsystem.pivotUp(0);
    	Robot.intakeSubsystem.isArmDown = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	EventLogging.commandMessage(logger);
    	Robot.intakeSubsystem.pivotUp(0);
    }
}
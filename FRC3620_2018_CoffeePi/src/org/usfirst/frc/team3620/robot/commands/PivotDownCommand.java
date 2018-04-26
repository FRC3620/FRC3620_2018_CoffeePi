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
	double liftEncoderPos;
	double pivotEncoder;
	double maxPower;
	double desiredCutoffPower;
	double cutoffEncoderPos;
	double slowDownPoint;
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
    public PivotDownCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Pivoting Down Initialized");
    	//Practice MaxPower = 0.6
    	//Practice cutoffPower = 0.02
    	//Practice cutoffPos = 130
    	//Practice slowDownPoint = 74.0
    	maxPower = 0.7;
    	desiredCutoffPower = 0.05;
    	cutoffEncoderPos = 150;
    	slowDownPoint = 30.0;
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double pivotEncoder = Robot.intakeSubsystem.readPivotAngleInDegress();
    	boolean isClampClosed = Robot.intakeSubsystem.isClampClosed();

    	if (isClampClosed) {

    		if(pivotEncoder >= slowDownPoint) {
    			Robot.intakeSubsystem.pivotDown(maxPower - ((maxPower - desiredCutoffPower)*((pivotEncoder - slowDownPoint)/(cutoffEncoderPos - slowDownPoint))));
    		} else {
    			Robot.intakeSubsystem.pivotDown(maxPower);

    		}

    	}else {

    			Robot.intakeSubsystem.pivotDown(0);
    		}
    	}
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	double liftEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	boolean isClampClosed = Robot.intakeSubsystem.isClampClosed();
    	if (!Robot.intakeSubsystem.isEncoderValid) {
    		logger.warn("I can't pivit down: encoder is not valid!");
    		return true;
    	}
    	if (Robot.intakeSubsystem.readPivotAngleInDegress() > cutoffEncoderPos) {
    		logger.info("Ending Pivot because we're too far");
    		return true;
    	}
    	if (!isClampClosed) {
    		logger.info("Ending Pivot cause we aren't closed");
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	EventLogging.commandMessage(logger); 
    	logger.info("end at pivot angle {}", Robot.liftSubsystem.readEncoderInInches());
    	
    	Robot.intakeSubsystem.pivotDown(0);
    	Robot.intakeSubsystem.isArmDown = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	EventLogging.commandMessage(logger);
    	logger.info("interrupted at pivot angle {}", Robot.liftSubsystem.readEncoderInInches());
    	Robot.intakeSubsystem.pivotDown(0);
    }
}
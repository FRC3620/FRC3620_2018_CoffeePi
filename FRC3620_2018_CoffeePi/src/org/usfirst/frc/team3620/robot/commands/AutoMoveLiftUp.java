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
	double requestedEncoderPos;
	double slowDownPoint = requestedEncoderPos - 768;
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
    	requestedEncoderPos = 4949;
    	desiredStartingPower = 0.125;
    	desiredEndingPower = 0.1625;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoder();
    	if(encoderPos <= 512) {
    		Robot.liftSubsystem.moveElevatorUp(0.8*(1/Math.cosh(((encoderPos - 512)/-233.38))));
    		System.out.println("We're accelerating to Speed");
    	} else if(encoderPos > 512 && encoderPos < slowDownPoint) {
    		Robot.liftSubsystem.moveElevatorUp(0.8);
    		System.out.println("At Cruise Altitude and Speed");
    	} else if(encoderPos >= slowDownPoint && encoderPos < requestedEncoderPos) {
    		Robot.liftSubsystem.moveElevatorUp(0.8*(1/Math.cosh(((encoderPos - requestedEncoderPos)/281.32))));
    		System.out.println("Nearing Destination");
    	} else if(encoderPos >= requestedEncoderPos) {
    		
    		System.out.println("We overshot.");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.readEncoder() > requestedEncoderPos /* || Robot.liftSubsystem.isTopLimitDepressed() */) {
    		
    		System.out.println("Exiting Moving UP");
    		return true;
    	}
    	else{
    		return false;
    	}
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

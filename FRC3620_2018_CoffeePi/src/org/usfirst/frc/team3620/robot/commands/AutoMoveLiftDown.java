package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.slf4j.Logger;
/**
 *
 */
public class AutoMoveLiftDown extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double encoderPos;
	double slowDownPoint;
	double fallingPower = 0.02;
    public AutoMoveLiftDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
	//1440 ticks = 16.875 inches
    protected void initialize() {
    	logger.info("Starting AutoMoveLiftDown Command");
    	slowDownPoint = -892;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoderInTics();
    	if(encoderPos <= slowDownPoint) {
    		Robot.liftSubsystem.setElevatorVelocity(fallingPower);
    		System.out.println("Coming down at speed");
    	}
    	else if(encoderPos > slowDownPoint) {
    		Robot.liftSubsystem.setElevatorVelocity(fallingPower + ((slowDownPoint - encoderPos)/slowDownPoint)*0.03);
    	} else if(encoderPos < -171) {
    		Robot.liftSubsystem.setElevatorVelocity(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.isBottomLimitDepressed()) {
    		Robot.liftSubsystem.resetEncoder();
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending AutoMoveLiftDown Command");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting AutoMoveLiftDown Command");
    	
    }
}

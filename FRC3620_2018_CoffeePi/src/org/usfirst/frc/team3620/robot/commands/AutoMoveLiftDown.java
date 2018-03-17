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
	double fallingPower = -0.40;
    public AutoMoveLiftDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
	//1440 ticks = 16.875 inches
    protected void initialize() {
    	logger.info("Starting AutoMoveLiftDown Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoderInTics();
    	Robot.liftSubsystem.setElevatorVelocity(fallingPower);
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.isBottomLimitDepressed()){
    		Robot.liftSubsystem.resetEncoder();
    		 return true;
    	} else if(Robot.liftSubsystem.readEncoderInInches() < 30) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending AutoMoveLiftDown Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting AutoMoveLiftDown Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    	
    }
}

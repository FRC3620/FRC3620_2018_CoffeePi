package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.slf4j.Logger;
/**
 *
 */
public class HoldLift extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double currentEncoderPos;
	double variedEncoderPos;
	double addedBangBangPower;
    public HoldLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    	addedBangBangPower = 0.25;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Starting HoldLift Command");
    	currentEncoderPos = Robot.liftSubsystem.readEncoder();
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	variedEncoderPos = Robot.liftSubsystem.readEncoder();
    	if(variedEncoderPos < currentEncoderPos) {
    	Robot.liftSubsystem.brace(addedBangBangPower*(currentEncoderPos - variedEncoderPos));
    	} else if(currentEncoderPos < variedEncoderPos) {
    		Robot.liftSubsystem.brace(-addedBangBangPower*(variedEncoderPos-currentEncoderPos));
    	} else if(currentEncoderPos == variedEncoderPos) {
    		Robot.liftSubsystem.brace(0);
    	}
  
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending HoldLift Command");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting HoldLift Command");
    }
}

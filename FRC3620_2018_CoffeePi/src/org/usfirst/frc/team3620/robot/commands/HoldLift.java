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
	double addedBangBangMultiplier;
	double addedBangBangPower = 0.03;
	double lowerBangBangPowerLimit = -Robot.liftSubsystem.bracingVoltage;
	double upperBangBangPowerLimit = 0.23;
	boolean noPowerToHold;
    public HoldLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Starting HoldLift Command at {}", currentEncoderPos);
    	currentEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	if(currentEncoderPos > 3) {
    		noPowerToHold = false;
    		
    	} else {
    		noPowerToHold = true;
    	}
    	
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double variedEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	if(noPowerToHold) {
    		Robot.liftSubsystem.moveAtManualSpeedGiven(0);
    		return;
    	}
    	if(Robot.liftSubsystem.isBottomLimitDepressed() == true) {
    		Robot.liftSubsystem.resetEncoder();
    	} else if(variedEncoderPos < currentEncoderPos) {
    		addedBangBangPower = addedBangBangMultiplier*(currentEncoderPos - variedEncoderPos);
    	} else if(variedEncoderPos > currentEncoderPos) {
    		addedBangBangPower = addedBangBangMultiplier*(currentEncoderPos - variedEncoderPos);
    	} else {
    		addedBangBangPower = 0;
    	}
    	
    	if(addedBangBangPower > lowerBangBangPowerLimit && addedBangBangPower < upperBangBangPowerLimit) {
    		Robot.liftSubsystem.brace(addedBangBangPower);
    	} else {
    		Robot.liftSubsystem.brace(0);
    	}
    	 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
  
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending HoldLift Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting HoldLift Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }
}

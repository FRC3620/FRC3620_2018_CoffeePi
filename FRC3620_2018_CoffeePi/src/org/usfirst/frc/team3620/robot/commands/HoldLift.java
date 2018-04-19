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
	double initialEncoderPos;
	double addedBangBangMultiplier = 0.02;
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
    	initialEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	logger.info("Starting HoldLift Command at {}", initialEncoderPos);
    	
    	if(Robot.liftSubsystem.isBottomLimitDepressed() && initialEncoderPos < 1) {
    		noPowerToHold = true;
    		
    	} else {
    		noPowerToHold = false;
    	}
    	
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	if(noPowerToHold) {
    		Robot.liftSubsystem.moveAtManualSpeedGiven(0);
    		return;
    	}
    	if(Robot.liftSubsystem.isBottomLimitDepressed() == true) {
    		Robot.liftSubsystem.resetEncoder();
    	} else if(currentEncoderPos < initialEncoderPos) {
    		addedBangBangPower = addedBangBangMultiplier*(initialEncoderPos - currentEncoderPos);
    	} else if(currentEncoderPos > initialEncoderPos) {
    		addedBangBangPower = addedBangBangMultiplier*(initialEncoderPos - currentEncoderPos);
    	} else {
    		addedBangBangPower = 0;
    	}
    	
    
    		Robot.liftSubsystem.brace(addedBangBangPower);
    		logger.info("AddedBangBangPower = {}, Height = {}", addedBangBangPower, currentEncoderPos);
    	
    	
    	
    	 
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

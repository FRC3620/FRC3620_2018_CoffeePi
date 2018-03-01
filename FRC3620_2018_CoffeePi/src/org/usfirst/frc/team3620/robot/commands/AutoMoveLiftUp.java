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
	double startingEncoderPos;
	double requestedEncoderPos;
	int oneFootInTics;
	double slowDownPoint = requestedEncoderPos - oneFootInTics;
	double speedUpPoint = startingEncoderPos + oneFootInTics;
	double desiredStartingPower;
	double maxPower;
	double desiredEndingPower;
	
	boolean weAreDoneSenor = false;
    public AutoMoveLiftUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    //1440 ticks of encoder = 16.875 inches
    protected void initialize() {
    	logger.info("Starting AutoMoveLiftUp Command");
    	startingEncoderPos =  Robot.liftSubsystem.readEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Math.abs(Robot.liftSubsystem.readEncoder());
    	if(encoderPos <= (speedUpPoint) || Robot.liftSubsystem.isBottomLimitDepressed()){
    		Robot.liftSubsystem.autoMoveElevatorUp(
    				Robot.liftSubsystem.calculatePowerHyperbolic(desiredStartingPower, encoderPos, speedUpPoint, maxPower));
    	} else if(encoderPos > speedUpPoint && encoderPos < slowDownPoint){
    		Robot.liftSubsystem.setElevatorVelocity(maxPower);
    	} else if(encoderPos >= slowDownPoint) {
    		Robot.liftSubsystem.autoMoveElevatorUp(
    				Robot.liftSubsystem.calculatePowerHyperbolic(desiredEndingPower, encoderPos, slowDownPoint, maxPower));
    	} else if(encoderPos > requestedEncoderPos || Robot.liftSubsystem.isTopLimitDepressed() == true) {
    		weAreDoneSenor = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(weAreDoneSenor == true) {
    		return true;
    	} else {
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

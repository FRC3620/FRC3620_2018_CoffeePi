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
    	double joyPos = 1.0;
    	double encoderPos = Robot.liftSubsystem.readEncoderInInches();
    	
		 if(Robot.liftSubsystem.readEncoderInInches() < 45){ //Was at 0.82 and 0.9 for JoyPos, 0.77 and 1.0
			joyPos = joyPos * (1 - (((45 - Robot.liftSubsystem.readEncoderInInches())/44)*0.63));
		} else {
			joyPos = 1.0;
		}
		//		if( Robot.liftSubsystem.readEncoderInInches() < 8){
		//		joyPos = joyPos * 0.2;
		//}
	
		Robot.liftSubsystem.moveElevatorDown(joyPos);
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double encoderPos = Robot.liftSubsystem.readEncoderInInches();
    	if(Robot.liftSubsystem.isBottomLimitDepressed()){
    		Robot.liftSubsystem.resetEncoder();
    		 return true;

    	} else if(Robot.liftSubsystem.readEncoderInInches() < 2) {

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

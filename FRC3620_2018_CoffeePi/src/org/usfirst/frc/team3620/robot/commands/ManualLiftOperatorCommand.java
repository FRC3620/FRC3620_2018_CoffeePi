package org.usfirst.frc.team3620.robot.commands;


import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3620.robot.subsystems.LiftSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.OI;
/**
 *
 */
public class ManualLiftOperatorCommand extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	boolean highGear = Robot.liftSubsystem.highGear;

    public ManualLiftOperatorCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Starting Manual Lift Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double joyPos = Robot.m_oi.getLiftJoystick();
    	logger.info("" + joyPos);
    if(highGear == false){
    	if(joyPos > 0.2 && Robot.liftSubsystem.isBottomLimitDepressed() == false){
    		Robot.liftSubsystem.climb(joyPos);
    	}
    	else if(joyPos < -0.2 && Robot.liftSubsystem.isTopLimitDepressed() == false){
    		Robot.liftSubsystem.moveElevatorUp(-joyPos, highGear);
    	}
    } else{
    	if(joyPos <  -0.2 && Robot.liftSubsystem.isTopLimitDepressed() == false) {
    		Robot.liftSubsystem.moveElevatorUp(-joyPos);
//    		System.out.println("Moving Lift Up");
    	}
    	else if(joyPos > 0.2 && Robot.liftSubsystem.isBottomLimitDepressed() == false) {
    		if( Robot.liftSubsystem.readEncoderInInches() < 14){
    			joyPos = joyPos * 0.75;
    		}
    		Robot.liftSubsystem.moveElevatorDown(joyPos);
    		//   		System.out.println("Moving Lift Down");
    	}
    }
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.isBottomLimitDepressed()) {
    		System.out.println("Bottom Switch just got pushed.");
    		Robot.liftSubsystem.resetEncoder();
    		
    		return true;
    	}
    /*	else if(Robot.liftSubsystem.isTopLimitDepressed()) {
    		System.out.println("Top Switch just got pushed.");
    		
    		return true;
    	}
    */	else {
    		return false;
    	}
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending Manual Lift Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting Manual Lift Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    }
}

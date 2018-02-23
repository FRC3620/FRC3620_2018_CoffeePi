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
	double joyPos;

    public ManualLiftOperatorCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("Starting Manual Lift Command");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	joyPos = Robot.m_oi.getLiftJoystick();
    	if(joyPos <  -0.2 && Robot.liftSubsystem.isTopLimitDepressed() == false) {
    		Robot.liftSubsystem.moveElevator(-joyPos);
//    		System.out.println("Moving Lift Up");
    	}
    	else if(joyPos > 0.2 && Robot.liftSubsystem.isBottomLimitDepressed() == false) {
    		Robot.liftSubsystem.moveElevator(0.04 + (joyPos *0.03));
//    		System.out.println("Moving Lift Down");
    	}
    	
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.isBottomLimitDepressed()) {
    		System.out.println("Bottom Switch just got pushed.");
    		return true;
    	}
    	else if(Robot.liftSubsystem.isTopLimitDepressed()) {
    		System.out.println("Top Switch just got pushed.");
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending Manual Lift Command");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting Manual Lift Command");
    }
}

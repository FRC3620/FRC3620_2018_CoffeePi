package org.usfirst.frc.team3620.robot.commands;

import java.util.Random;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoMoveALittleCommand extends Command {
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	EventLogging.commandMessage(logger);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	EventLogging.commandMessage(logger);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	EventLogging.commandMessage(logger);
    }
}

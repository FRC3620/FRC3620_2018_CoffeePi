package org.usfirst.frc.team3620.robot.autonomous;

import java.util.Random;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FakeCommand extends Command {
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	Timer timer = new Timer();
	double delay; 
	String name;

    public FakeCommand(double ll, double ul, Command realCommand) {
    	name = realCommand.getClass().getName();
    	delay = ll + ( (new Random()).nextDouble()*(ul-ll));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("fake command {} initialize", name);
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > delay;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("fake command {} end", name);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("fake command {} interrupted", name);
    }
}

package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousPukeCubeCommand extends Command {
	
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	Timer timer = new Timer();
	
    public AutonomousPukeCubeCommand() {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	EventLogging.commandMessage(logger);
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	Robot.intakeSubsystem.pushCubeOut(-0.6);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double howlong = timer.get();
    	if (howlong > 1) {
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	EventLogging.commandMessage(logger);
    	Robot.intakeSubsystem.pushCubeOut(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	EventLogging.commandMessage(logger);
    	Robot.intakeSubsystem.pushCubeOut(0);
    }
}

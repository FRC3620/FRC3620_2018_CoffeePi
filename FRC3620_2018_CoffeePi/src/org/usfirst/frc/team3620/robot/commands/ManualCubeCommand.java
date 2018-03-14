package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualCubeCommand extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);


	public ManualCubeCommand() {
		requires(Robot.intakeSubsystem);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		EventLogging.commandMessage(logger);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double joyPos;
		joyPos = Robot.m_oi.getCubeJoystick();
		if(joyPos < -0.2 ) {
			Robot.intakeSubsystem.pushCubeOut(0.8* -joyPos);
		}
		else if(joyPos > 0.2) {
			Robot.intakeSubsystem.bringCubeIn(0.8 * joyPos);
		} else if(joyPos < 0.2 && joyPos > -0.2) {
			Robot.intakeSubsystem.pushCubeOut(0);
		}



	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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

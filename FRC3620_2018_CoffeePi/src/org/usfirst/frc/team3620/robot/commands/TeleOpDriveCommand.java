package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.OI;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.subsystems.DriveSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;


public class TeleOpDriveCommand extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	public TeleOpDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		EventLogging.commandMessage(logger);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.driveSubsystem.teleOpDrive(Robot.m_oi.getDriveVerticalJoystick(),Robot.m_oi.getDriveHorizontalJoystick());
		//Robot.driveSubsystem.teleOpDriveTransmitter(Robot.m_oi.getDriveVerticalJoystick(),Robot.m_oi.getTransmitterHorizontalJoystick());
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
	// subsystems is scheduled to run5
	protected void interrupted() {
		EventLogging.commandMessage(logger);
	}
}

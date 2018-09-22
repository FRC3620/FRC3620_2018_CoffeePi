package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopDriveCommandWithStepperLimiter extends Command {
Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double speedMultiplier = 1.0;
	
    public TeleopDriveCommandWithStepperLimiter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	EventLogging.commandMessage(logger);
    }

    // Called repeatedly when this Command is scheduled to run
    
    // TODO Abstract out from the subsystem; investigate lift NPE
    protected void execute() {
    	double liftEncoderPos = Robot.liftSubsystem.readEncoderInInches();
			if(liftEncoderPos <= 40.0) {
				speedMultiplier = 1.0;
			}
			else if(liftEncoderPos > 40.0 && liftEncoderPos <= 50.0) {
				speedMultiplier = 0.95;
			}
			else if(liftEncoderPos > 50.0 && liftEncoderPos <= 60.0) {
				speedMultiplier = 0.90;
			}
			else if(liftEncoderPos > 60.0 && liftEncoderPos <= 70.0) {
				speedMultiplier = 0.85;
			}
			else if(liftEncoderPos > 70.0) {
				speedMultiplier = 0.8;
			}
			else {
				speedMultiplier = 1.0;
			}
		
		
    	Robot.driveSubsystem.teleOpDrive(speedMultiplier*Robot.m_oi.getDriveVerticalJoystick(),speedMultiplier*Robot.m_oi.getDriveHorizontalJoystick());
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

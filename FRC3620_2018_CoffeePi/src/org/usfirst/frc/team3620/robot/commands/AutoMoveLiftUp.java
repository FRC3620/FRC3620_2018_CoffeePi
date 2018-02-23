package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoMoveLiftUp extends Command {
	double encoderPos;
	double requestedEncoderPos;
	double slowDownPoint = requestedEncoderPos - 768;
    public AutoMoveLiftUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    //1440 ticks of encoder = 16.875 inches
    protected void initialize() {
    	requestedEncoderPos = 4949;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	encoderPos = Robot.liftSubsystem.readEncoder();
    	if(encoderPos <= 512) {
    		Robot.liftSubsystem.moveElevator(1/Math.cosh(((encoderPos - 512)/-233.38)));
    	} else if(encoderPos > 512 && encoderPos < slowDownPoint) {
    		Robot.liftSubsystem.moveElevator(1);
    	} else if(encoderPos >= slowDownPoint && encoderPos < requestedEncoderPos) {
    		Robot.liftSubsystem.moveElevator(1/Math.cosh(((encoderPos - requestedEncoderPos)/281.32)));
    	} else if(encoderPos >= requestedEncoderPos) {
    		Robot.liftSubsystem.brace(0.13);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.liftSubsystem.readEncoder() > 4949 || Robot.liftSubsystem.isTopLimitDepressed()) {
    		Robot.liftSubsystem.brace(0.13);
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.liftSubsystem.brace(0.06);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.liftSubsystem.brace(0.06);
    }
}

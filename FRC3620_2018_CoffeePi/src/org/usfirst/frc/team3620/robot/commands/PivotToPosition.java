package org.usfirst.frc.team3620.robot.commands;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotToPosition extends Command implements PIDSource, PIDOutput {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	
	final double Kp = 0.01; // 10 degrees error = 0.1 motor power 
	//Kp 0.01 left us 10 degrees short
	//Kp = 0.02 is too much
	//Kp set back to 0.01, working on Ki
	//ki = 0.0001
	final double Ki = 0.0005; //works
	final double Kd = 0.15;
	
	PIDController pidController = new PIDController(Kp, Ki, Kd, this, this);
	double PIVOT_IN_MIDDLE = 25.0;     //TODO verify this value 

    public PivotToPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intakeSubsystem);
    	
    	pidController.setSetpoint(PIVOT_IN_MIDDLE);
    	pidController.setInputRange(0, 180);
    	pidController.setOutputRange(-0.6, 0.6);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	EventLogging.commandMessage(logger);
    	pidController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean isEncoderValid = Robot.intakeSubsystem.isEncoderValid;
    	if(!isEncoderValid) {
    		logger.info("Encoder is not valid, ended pivoting.");
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	EventLogging.commandMessage(logger);
    	pidController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	EventLogging.commandMessage(logger);
    	end();
    }

	@Override
	public void pidWrite(double output) {
		double angle = pidGet();
		double o2 = output;
		/*if(output < 0) { TODO verify this code
			if(angle < PIVOT_IN_MIDDLE) {
				o2 = 0;
			}
			if(angle < PIVOT_IN_MIDDLE + 45) {
				o2 = output/2;
			}
		}*/
		logger.info ("angle {}, got output power {}, set power {}",  angle, output, o2);
		Robot.intakeSubsystem.pivotDown(o2);
		
		// TODO uncomment this *AFTER* testing
		// Robot.intakeSubsystem.pivotDown(output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return Robot.intakeSubsystem.readPivotAngleInDegress();
	}
}

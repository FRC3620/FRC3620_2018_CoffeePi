// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team3620.robot.subsystems;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.commands.TeleOpDriveCommand;
import org.usfirst.frc.team3620.robot.commands.TeleopDriveCommandWithStepperLimiter;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import com.ctre.phoenix.ErrorCode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveSubsystem extends Subsystem {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	public AHRS ahrs = null /* will set in the constructor */;

    private final WPI_TalonSRX driveSubsystemTalonLeft1 = RobotMap.driveSubsystemTalonLeft1;
   
    private final WPI_TalonSRX driveSubsystemTalonRight1 = RobotMap.driveSubsystemTalonRight1;
    
    private final DifferentialDrive cANDifferentialDrive = RobotMap.driveSubsystemCANDifferentialDrive;
    
    private boolean reverse;
	private boolean highGear;//for the motors
	double automaticHeading;
	
	// Allows double squaring to be turned on and off, while avoiding another qual9 mistake
	private boolean doubleSquaredTurn = false; // Set to true to re-square the turn input.
	private double turnReducer = 0.8; // Multiplied by turn value to scale it down	
	private boolean heightBasedSpeed = true; // Set to true to reduce speed for lift height, false to override.
	private boolean gotCompBot;
    
	public DriveSubsystem() {
		super();               
		ahrs = new AHRS(edu.wpi.first.wpilibj.SPI.Port.kMXP);
		ahrs.enableLogging(false);
		
		gotCompBot = RobotMap.practiceBotJumper.get();
	}
	
	@Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TeleOpDriveCommand());
//    	setDefaultCommand(new TeleopDriveCommandWithStepperLimiter());
    }

	public boolean gotCompBot() {
		return gotCompBot;
	}
	
	private double lowerLimit(double value, double lowerLimit) {//This function removes low input values to insure low voltage don't fidget the motors
		if(Math.abs(value)<=lowerLimit) {
			return(0.0);
		}
		return(value);
	}
	
	
	private double getSpeedModifier() {	// TODO Tune me!!
		return 1.0;
		
	}
	
	public void teleOpDrive(double speed,double turn) {
		if (cANDifferentialDrive != null) {
		
			double r2;
			// Lets double turn squaring be switched on and off quickly with the boolean up top.
			if(doubleSquaredTurn) {
				r2 = turn * turn * turnReducer;
				if (turn < 0) {
					r2 = -r2;
				}
			}
			else {
				r2 = turn * turnReducer;
			}
			speed = (speed * 0.8);
			cANDifferentialDrive.arcadeDrive(-speed, r2, true);
		}
	}
	
	public void teleOpDriveTransmitter(double speed,double turn) {
		if (cANDifferentialDrive != null) {
			speed=lowerLimit(speed, 0.2)*getSpeedModifier();
			turn=lowerLimit(turn, 0.1)*getSpeedModifier();
			cANDifferentialDrive.arcadeDrive(-speed, turn, false);
		}
	}

	public void autoDrive(double speed,double turn) {
		if (cANDifferentialDrive != null) {
			cANDifferentialDrive.arcadeDrive(speed, turn);
		}
	}
	
	public void autoDriveTank(double left, double right) {
		if (cANDifferentialDrive != null) {
			left *= getSpeedModifier();
			right *= getSpeedModifier();
			cANDifferentialDrive.tankDrive(left, right, false);
		}
	}
	
	public void autoDriveNoSquared(double speed,double turn) {
		if (cANDifferentialDrive != null) {
			cANDifferentialDrive.arcadeDrive(speed, turn, false);
		}
	}
	
	public void setReverseFlag(Boolean setVal) {
		reverse = setVal;
	}
	
	public void resetReverseFlag() {
		setReverseFlag(false);
	}
	
	public void toggleReverseFlag() {
		setReverseFlag(!(reverse));
	}
	
	//Encoders and NavX:
	public void resetEncoders() {
		if (driveSubsystemTalonLeft1 != null) {
		   ErrorCode ec = driveSubsystemTalonLeft1.getSensorCollection().setQuadraturePosition(0, 100);  //10ms timeout for confirmation. reduce this?
		   //ErrorCode ec = RobotMap.driveSubsystemLeftEncoder.getRaw();
		    logger.info("Left encoder reset status = {}", ec);
		}
		if (driveSubsystemTalonRight1 != null) {
			ErrorCode ec = driveSubsystemTalonRight1.getSensorCollection().setQuadraturePosition(0, 100);  //10ms timeout for confirmation. reduce this?
		    logger.info("Right encoder reset status = {}", ec);
		}
		RobotMap.driveSubsystemLeftEncoder.reset();
		RobotMap.driveSubsystemRightEncoder.reset();
	}
	
	public int readLeftEncRaw() {
	    //return -1 * driveSubsystemTalonLeft1.getSensorCollection().getQuadraturePosition();
	    return RobotMap.driveSubsystemLeftEncoder.getRaw();
	}
    
	public int readRightEncRaw() {
	    return RobotMap.driveSubsystemRightEncoder.getRaw();
	}
	
    public void resetNavX() {
		if (ahrs != null) {
			ahrs.resetDisplacement();
			logger.info("Resetting X Displacement, X = {}",
					ahrs.getDisplacementX());
			ahrs.reset();
			logger.info("Resetting NavX Angle, Angle = {}", ahrs.getAngle());
		}
	}
	
    boolean complainedAboutMissingAhrs = false;
    
    public boolean ahrsIsConnected() {
		return ahrs != null && ahrs.isConnected();
	}

	void complainAboutMissingAhrs() {
		if (!complainedAboutMissingAhrs) {
			logger.warn("Cannot read NavX, not connected");
		}
		complainedAboutMissingAhrs = true;
	}

	public double getPitch() {
		if (ahrsIsConnected()) {
			return ahrs.getPitch();
		} else {
			complainAboutMissingAhrs();
			return 0;
		}
	}

	public double getRoll() {
		if (ahrsIsConnected()) {
			return ahrs.getRoll();
		} else {
			complainAboutMissingAhrs();
			return 0;
		}
	}

	public double getAngle() {
		if (ahrsIsConnected()) {
			return ahrs.getAngle(); //Angle direction was opposite of 2017 testing (raw is clockwise-positive, corrected is clockwise-negative)
		} else {
			complainAboutMissingAhrs();
			return 0;
		}
	}

	public double getDisplacementX() {
		if (ahrsIsConnected()) {
			return ahrs.getDisplacementX();
		} else {
			complainAboutMissingAhrs();
			return 0;
		}
	}

	public double getDisplacementY() {
		if (ahrsIsConnected()) {
			return ahrs.getDisplacementY();
		} else {
			complainAboutMissingAhrs();
			return 0;
		}
	}

	public double getDisplacementZ() {
		if (ahrsIsConnected()) {
			return ahrs.getDisplacementZ();
		} else {
			complainAboutMissingAhrs();
			return 0;
		}
	}
	
	public void setMotors(double speedLeft, double speedRight) {
    	cANDifferentialDrive.tankDrive(speedLeft, speedRight);
    }
	
	public void driveAutomatically(double move, double rotate) {
		cANDifferentialDrive.tankDrive(move, rotate);
	}
	
	public void updateDashboardWithPidStuff(Command who, PIDController pid,
			double sidestick) {
//		SmartDashboard.putString("PID Command", who.getName());
//		SmartDashboard.putNumber("PID P", pid.getP());
//		SmartDashboard.putNumber("PID I", pid.getI());
//		SmartDashboard.putNumber("PID D", pid.getD());
//
//		SmartDashboard.putNumber("PID Turn Sidestick", sidestick);
//		SmartDashboard.putNumber("PID Angle Setpoint", pid.getSetpoint());
//		SmartDashboard.putNumber("PID Angle Error", pid.getError());

		SmartDashboard.putNumber("ActualHeading", getAngle());
		SmartDashboard.putNumber("DesiredHeading", getAutomaticHeading());
	}
	
	public double getAutomaticHeading() {
		return automaticHeading;
	}
	
	public double changeAutomaticHeading(double changeAngle) {
		automaticHeading = automaticHeading + changeAngle;
		automaticHeading = normalizeAngle(automaticHeading);
		logger.info("Changing auto heading to" +  automaticHeading);
		return automaticHeading;
	}
	
	static public double normalizeAngle(double angle) {
		// bring into range of -360..360
		double newAngle = angle % 360;

		// if it's between -360..0, put it between 0..360
		if (newAngle < 0)
			newAngle += 360;

		return newAngle;
	}
	
	public double angleDifference(double angle1, double angle2) {
		double diff = Math.abs(angle1 - angle2);
		if (diff > 180) {
			diff = 360 - diff;
		}
		return diff;
	}
	
	public PIDSource getAhrsPidSource() {
		if (ahrsIsConnected()) {
			return ahrs;
		} else {
			return new PIDSource() {

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					// TODO Auto-generated method stub

				}

				@Override
				public double pidGet() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
	}

//	public double readLeftEncoder() {
//	return 0.0;
//	}
//	
//	public double readRightEncoder() {
//		return 0.0;
//		}
	
	public void setHighGear() {//@Doug, HoW DOOOooOOOoOooOooOooOOOOOooOOoOooOo??????????????/????//??/??/?????/?
		highGear = true;
	}
	
    public void setLowGear() {
    	highGear = false;
    }
    

    @Override
    public void periodic() {
    	if (driveSubsystemTalonLeft1 != null) {
    		SmartDashboard.putNumber("Current Draw on Left Talon 1: ", driveSubsystemTalonLeft1.getOutputCurrent());
    	}
    	if (driveSubsystemTalonRight1 != null) {
    		SmartDashboard.putNumber("Current Draw on Right Talon 1: ", driveSubsystemTalonRight1.getOutputCurrent());
    	}
    	
    	SmartDashboard.putNumber("NavX Heading :", ahrs.getAngle());
    }
    

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
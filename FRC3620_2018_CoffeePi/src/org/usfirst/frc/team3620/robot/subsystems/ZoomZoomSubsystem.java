package org.usfirst.frc.team3620.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ZoomZoomSubsystem extends Subsystem {
	private static Boolean reverse;
	private static Boolean highGear;//for the motors
	
	
	
	public static void useWPMdrive() {
		
	}
	
	public static void useCANdrive() {
		
	}
	
	
	
	private static double lowerLimit(double value, double lowerLimit) {//This function removes low input values to insure low voltage don't fidget the motors
		if(Math.abs(value)<=lowerLimit) {
			return(0.0);
		}
		return(value);
	}
	
	
	public static void teleOpDrive(double speed,double turn) {
		speed=lowerLimit(speed,0.2);
		turn=lowerLimit(turn,0.2);
		
	}

	public static void autoDrive() {
		
	}
	
	
	
	public static void setFlag(Boolean setVal) {
		reverse = setVal;
	}
	
	public static void resetFlag() {
		reverse = false;
	}
	
	
	
	public static void resetEncoders() {
		
	}
	
	public static double readEncoder() {
	return 0.0;
	}
	
	
	public static void highGear() {
		highGear = true;
	}
	
	
    public static void lowGear() {
    	highGear = false;
    }
    
    public void initDefaultCommand() {
    	
    }
}


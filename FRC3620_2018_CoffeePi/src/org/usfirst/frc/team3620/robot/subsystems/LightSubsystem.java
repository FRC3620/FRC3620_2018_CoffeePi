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


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DropMode;

import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc3620.misc.RobotMode;
import org.usfirst.frc3620.misc.BlinkinDict;
import org.usfirst.frc3620.misc.BlinkinDict.Color;

import edu.wpi.first.wpilibj.DriverStation;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class LightSubsystem extends Subsystem {
	Timer initTime = new Timer();
	
	HashMap<Integer, Double> lightsPriority = new HashMap<Integer, Double>();
	
	public void modeChange (RobotMode newMode, RobotMode previousMode) {
		DriverStation.Alliance teamColor = DriverStation.getInstance().getAlliance();
		if (newMode != RobotMode.DISABLED) {
			lightsPriority.put(2, Color.DARK_GREEN.value);
		}
		if ((newMode == RobotMode.AUTONOMOUS || newMode== RobotMode.TELEOP) && previousMode == RobotMode.DISABLED) {
			initTime.reset();
			initTime.start();
			if (teamColor == DriverStation.Alliance.Red) {
				lightsPriority.put(0, Color.STROBE_TEAMCOLOR2.value);
			}
			else if (teamColor == DriverStation.Alliance.Blue) {
				lightsPriority.put(0, Color.STROBE_TEAMCOLOR1.value);
			}
		}
	}
	public void setEvent (String event, Boolean condition) {
		if (event == "cube") {
			if(condition) {lightsPriority.put(1, Color.HEARTBEAT_WHITE.value);}
			else {lightsPriority.remove(1, Color.HEARTBEAT_WHITE.value);}
		}
		if (event == "lift") {
			if(condition) {lightsPriority.put(1, Color.CONFETTI.value);}
		}

	}
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController lightPWM9 = RobotMap.lightSubsystemLightPWM9;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
    	if (initTime.get() >= 1.5) {
    		lightsPriority.remove(0);
    		initTime.stop();
    	}
        // Put code here to be run every loop
    	if (lightsPriority.get(0) != null) {
    		lightPWM9.set(lightsPriority.get(0));
    	}
    	else if (lightsPriority.get(1) != null) {
    		lightPWM9.set(lightsPriority.get(1));
    	}
    	else if (lightsPriority.get(2) != null) {
    		lightPWM9.set(lightsPriority.get(2));
    	}

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}


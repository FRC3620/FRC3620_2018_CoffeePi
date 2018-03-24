/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3620.robot;


import org.usfirst.frc.team3620.robot.paths.*;
import org.usfirst.frc3620.misc.AnalogValueButton;
import org.usfirst.frc3620.misc.DPad;
import org.usfirst.frc3620.misc.DoubleTriggerButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3620.robot.commands.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick driverJoystick;
	public Joystick operatorJoystick;
	public Joystick kaiBox;
	
	public OI() {
	       driverJoystick = new Joystick(0);
	       operatorJoystick = new Joystick(1);
	       kaiBox = new Joystick(2);

	   	//// CREATING BUTTONS
	   	// One type of button is a joystick button which is any button on a
	   	//// joystick.
	   	// You create one by telling it which joystick it's on and which button
	   	// number it is.
	   	// Joystick stick = new Joystick(port);
	   	// Button button = new JoystickButton(stick, buttonNumber);

	   	// There are a few additional built in buttons you can use. Additionally,
	   	// by subclassing Button you can create custom triggers and bind those to
	   	// commands the same as any other Button.

	   	//// TRIGGERING COMMANDS WITH BUTTONS
	   	// Once you have a button, it's trivial to bind it to a button in one of
	   	// three ways:

	   	// Start the command when the button is pressed and let it run the command
	   	// until it is finished as determined by it's isFinished method.
	   	// button.whenPressed(new ExampleCommand());

	   	// Run the command while the button is being held down and interrupt it once
	   	// the button is released.
	   	// button.whileHeld(new ExampleCommand());

	   	// Start the command when the button is released and let it run the command
	   	// until it is finished as determined by it's isFinished method.
	   	// button.whenReleased(new ExampleCommand());

	       //Button A
	       Button clamp = new JoystickButton(operatorJoystick, 5);
	       clamp.whenPressed(new ClampCommand());
	       Button unclamp = new JoystickButton(operatorJoystick, 6);
	       unclamp.whenPressed(new UnClampCommand());
	       Button pivotUp = new JoystickButton(operatorJoystick, 2);
	       pivotUp.whenActive(new PivotUpCommand());
	       Button pivotDown = new JoystickButton(operatorJoystick, 3);
	       pivotDown.whileHeld(new CreepIntakeUp()); 
	     Button moveLiftUp = new JoystickButton(operatorJoystick, 4);
	      moveLiftUp.whenPressed(new AutoMoveLiftUpToScaleHeight());
	       
	 //      Button moveLiftDown = new JoystickButton(operatorJoystick, 1);
	   //    moveLiftDown.whenPressed(new AutoMoveLiftDown());
	       Button shiftIntoHighGear = new JoystickButton(operatorJoystick, 8);
	       shiftIntoHighGear.whenPressed(new LiftShiftHighGear());
	       
	       Button shiftIntoLowGear = new JoystickButton(operatorJoystick, 7);
	       shiftIntoLowGear.whenPressed(new LiftShiftLowGear()); 
	       
	       DPad dpad = new DPad(operatorJoystick, 0);
	       dpad.up().whenActive(new PivotUpCommand());
	       dpad.down().whenActive(new PivotDownCommand());
	       
	       Button liftOnManualControl = new AnalogValueButton(()-> Math.abs(getLiftJoystick()), 0.2);
	       liftOnManualControl.whileHeld(new ManualLiftOperatorCommand());
	       
	       Button pivotToTripPointButton = new DoubleTriggerButton(operatorJoystick, 0.6);
	       pivotToTripPointButton.whenPressed(new PivotToPosition());
	      
	       Button posSet8 = new JoystickButton(kaiBox, 8);
	       Button posSet9 = new JoystickButton(kaiBox, 9);
	       
	       SmartDashboard.putData("ResetLiftEncoder", new ResetLiftEncoderCommand());
	       SmartDashboard.putData("ResetPivotEncoder", new ResetPivotEncoder());
	       SmartDashboard.putData("Pivot Down Command", new PivotDownCommand());
	       SmartDashboard.putData("Pivot Up Command", new PivotUpCommand());
	       SmartDashboard.putData("Pivot to position command", new PivotToPosition());
	       // SmartDashboard Buttons:
//	       SmartDashboard.putData("AutonomousLeft", new AutonomousLeft());
//	       SmartDashboard.putData("AutonomousCenter", new AutonomousCenter());
//	       SmartDashboard.putData("AutonomousRight", new AutonomousRight());
	       SmartDashboard.putData("TestPoints Auto", new TestPoints());
           SmartDashboard.putData("Center start, left switch", new Path1_CenterStart_LeftSwitch());
           SmartDashboard.putData("Center start, right switch", new Path1_CenterStart_RightSwitch());
           SmartDashboard.putData("Left start, left scale end", new Path1_LeftStart_LeftScaleEnd());
           SmartDashboard.putData("Left start, left scale side", new Path1_LeftStart_LeftScaleSide());
           SmartDashboard.putData("Left start, left switch", new Path1_LeftStart_LeftSwitchEnd());
//           SmartDashboard.putData("Left start, left switch", new Path_LeftStart_LeftSwitch());
           SmartDashboard.putData("Left start, right scale", new Path1_LeftStart_RightScaleEnd());
           SmartDashboard.putData("Left Start, Right scale side", new Path1_LeftStart_RightScaleSide());
//           SmartDashboard.putData("Right start, left scale", new Path_RightStart_LeftScale());
           SmartDashboard.putData("Right start, right scale end", new Path1_RightStart_RightScaleEnd());
           SmartDashboard.putData("Right start, right scale side", new Path1_RightStart_RightScaleSide());
//           SmartDashboard.putData("Right start, right switch", new Path_RightStart_RightSwitch());
           SmartDashboard.putData("ResetDriveEncoders", new ResetDriveEncodersCommand());
           SmartDashboard.putData("Move Lift to Transport Height", new AutoMoveLiftUpToSwitchHeight());
           SmartDashboard.putData("Move Lift to Scale Height", new AutoMoveLiftUpToScaleHeight());
           SmartDashboard.putData("Pivot180", new Auto180PointTurn(90));
           SmartDashboard.putData("Auto Move Lift Down", new AutoMoveLiftDown());

           SmartDashboard.putData("Left Scale Side to Alley Cube", new Path2_LeftScaleSide_AlleyCube());

           SmartDashboard.putData("BackUpFromScale", new Path_BackUpFromScale());
           SmartDashboard.putData("PID Tuning Paths", new ZeHomelessPathHaven());

	}
	       
	public double getDriveVerticalJoystick() {
		return driverJoystick.getRawAxis(1);
	}
	public double getDriveHorizontalJoystick() {
		return driverJoystick.getRawAxis(4);
	}
	public double getTransmitterHorizontalJoystick() {
		return -1 * driverJoystick.getRawAxis(5);
	}
	public double getLiftJoystick() {
		return operatorJoystick.getRawAxis(5);
	}
	public double getCubeJoystick() {
		return operatorJoystick.getRawAxis(1);
	}
}

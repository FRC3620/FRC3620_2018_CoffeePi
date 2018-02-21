/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3620.robot;

import org.usfirst.frc.team3620.robot.commands.AutonomousCenter;
import org.usfirst.frc.team3620.robot.commands.AutonomousLeft;
import org.usfirst.frc.team3620.robot.commands.AutonomousRight;
import org.usfirst.frc.team3620.robot.commands.ClampCommand;
import org.usfirst.frc.team3620.robot.commands.FullSpeedDriveCommand;
import org.usfirst.frc.team3620.robot.commands.HoldLift;
import org.usfirst.frc.team3620.robot.commands.IntakeCubeCommand;
import org.usfirst.frc.team3620.robot.commands.LiftDown;
import org.usfirst.frc.team3620.robot.commands.LiftShiftHighGear;
import org.usfirst.frc.team3620.robot.commands.LiftShiftLowGear;
import org.usfirst.frc.team3620.robot.commands.LiftToHome;
import org.usfirst.frc.team3620.robot.commands.LiftToScale;
import org.usfirst.frc.team3620.robot.commands.LiftUp;
import org.usfirst.frc.team3620.robot.commands.OutakeCubeCommand;
import org.usfirst.frc.team3620.robot.commands.PivotDownCommand;
import org.usfirst.frc.team3620.robot.commands.PivotUpCommand;
import org.usfirst.frc.team3620.robot.commands.ResetLiftEncoderCommand;
import org.usfirst.frc.team3620.robot.commands.ResetDriveEncodersCommand;
import org.usfirst.frc.team3620.robot.commands.SetDriveGearHighCommand;
import org.usfirst.frc.team3620.robot.commands.SetDriveGearLowCommand;
import org.usfirst.frc.team3620.robot.commands.UnClampCommand;
import org.usfirst.frc.team3620.robot.paths.Path_1_E;
import org.usfirst.frc.team3620.robot.paths.Path_CenterStart_LeftSwitch;
import org.usfirst.frc.team3620.robot.paths.Path_CenterStart_RightSwitch;
import org.usfirst.frc.team3620.robot.paths.Path_LeftStart_LeftScale;
import org.usfirst.frc.team3620.robot.paths.Path_LeftStart_LeftSwitch;
import org.usfirst.frc.team3620.robot.paths.Path_LeftStart_RightScale;
import org.usfirst.frc.team3620.robot.paths.Path_RightStart_LeftScale;
import org.usfirst.frc.team3620.robot.paths.Path_RightStart_RightScale;
import org.usfirst.frc.team3620.robot.paths.Path_RightStart_RightSwitch;
import org.usfirst.frc.team3620.robot.paths.TestPoints;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	       Button clamp = new JoystickButton(operatorJoystick, 9);
	       clamp.whenPressed(new ClampCommand());
	       Button unclamp = new JoystickButton(operatorJoystick, 10);
	       unclamp.whenPressed(new UnClampCommand());
	       Button spinIn = new JoystickButton(operatorJoystick, 6);
	       spinIn.whileHeld(new IntakeCubeCommand());
	       Button spinOut = new JoystickButton(operatorJoystick, 5);
	       spinOut.whileHeld(new OutakeCubeCommand());
	       Button pivotUp = new JoystickButton(operatorJoystick, 2);
	       pivotUp.whileHeld(new PivotUpCommand());
	       Button pivotDown = new JoystickButton(operatorJoystick, 3);
	       pivotDown.whileHeld(new PivotDownCommand());

	       Button moveLiftUp = new JoystickButton(operatorJoystick, 4);
	       moveLiftUp.whileHeld(new LiftUp());
	       Button moveLiftDown = new JoystickButton(operatorJoystick, 1);
	       moveLiftDown.whileHeld(new LiftDown());
	       

	       


	       Button posSet8 = new JoystickButton(kaiBox, 8);
	       Button posSet9 = new JoystickButton(kaiBox, 9);
	      
	       SmartDashboard.putData("ResetEncoder", new ResetLiftEncoderCommand());

	       // SmartDashboard Buttons:
//	       SmartDashboard.putData("AutonomousLeft", new AutonomousLeft());
//	       SmartDashboard.putData("AutonomousCenter", new AutonomousCenter());
//	       SmartDashboard.putData("AutonomousRight", new AutonomousRight());
	       SmartDashboard.putData("TestPoints Auto", new TestPoints());
           SmartDashboard.putData("Center start, left switch", new Path_CenterStart_LeftSwitch());
//           SmartDashboard.putData("Center start, right switch", new Path_CenterStart_RightSwitch());
//           SmartDashboard.putData("Left start, left scale", new Path_LeftStart_LeftScale());
//           SmartDashboard.putData("Left start, left switch", new Path_LeftStart_LeftSwitch());
//           SmartDashboard.putData("Left start, right scale", new Path_LeftStart_RightScale());
//           SmartDashboard.putData("Right start, left scale", new Path_RightStart_LeftScale());
//           SmartDashboard.putData("Right start, right scale", new Path_RightStart_RightScale());
//           SmartDashboard.putData("Right start, right switch", new Path_RightStart_RightSwitch());
           SmartDashboard.putData("Reset encoders", new ResetDriveEncodersCommand());
	}
	       
	
	public double getDriveVerticalJoystick() {
		return driverJoystick.getRawAxis(1);
	}
	public double getDriveHorizontalJoystick() {
		return driverJoystick.getRawAxis(4);
	}
	public double getLiftJoystick() {
		return operatorJoystick.getRawAxis(3);
	}

}

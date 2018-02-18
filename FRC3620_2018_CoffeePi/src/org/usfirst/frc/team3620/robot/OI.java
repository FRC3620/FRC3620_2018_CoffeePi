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
import org.usfirst.frc.team3620.robot.commands.LiftShiftHighGear;
import org.usfirst.frc.team3620.robot.commands.LiftShiftLowGear;
import org.usfirst.frc.team3620.robot.commands.LiftToHome;
import org.usfirst.frc.team3620.robot.commands.LiftToScale;
import org.usfirst.frc.team3620.robot.commands.LiftUp;
import org.usfirst.frc.team3620.robot.commands.OutakeCubeCommand;
import org.usfirst.frc.team3620.robot.commands.PivotDownCommand;
import org.usfirst.frc.team3620.robot.commands.PivotUpCommand;
import org.usfirst.frc.team3620.robot.commands.ResetEncoder;
import org.usfirst.frc.team3620.robot.commands.SetDriveGearHighCommand;
import org.usfirst.frc.team3620.robot.commands.SetDriveGearLowCommand;
import org.usfirst.frc.team3620.robot.commands.UnClampCommand;
        

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
	       Button clamp = new JoystickButton(operatorJoystick,1);
	       clamp.whenPressed(new ClampCommand());
	       Button unclamp = new JoystickButton(operatorJoystick,2);
	       unclamp.whenPressed(new UnClampCommand());
	       Button spinIn = new JoystickButton(operatorJoystick, 3);
	       spinIn.whileHeld(new IntakeCubeCommand());
	       Button spinOut = new JoystickButton(operatorJoystick, 4);
	       spinOut.whileHeld(new OutakeCubeCommand());
	       Button pivotUp = new JoystickButton(operatorJoystick, 5);
	       pivotUp.whileHeld(new PivotUpCommand());
	       Button pivotDown = new JoystickButton(operatorJoystick, 6);
	       pivotDown.whileHeld(new PivotDownCommand());
	       
	       Button lBumper = new JoystickButton(driverJoystick, 5);
	       lBumper.whileHeld(new SetDriveGearLowCommand());
	       Button rBumper = new JoystickButton(driverJoystick, 6);
	       rBumper.whileHeld(new SetDriveGearHighCommand());

	       Button fullspeed = new JoystickButton(driverJoystick, 2);
	       fullspeed.whileHeld(new FullSpeedDriveCommand());
	       Button brace = new JoystickButton(driverJoystick, 1);
	       brace.whileHeld(new HoldLift());
	       Button moveLiftUp = new JoystickButton(driverJoystick, 4);
	       moveLiftUp.whileHeld(new LiftUp());
	       
	       Button liftShiftHighGear = new JoystickButton(operatorJoystick, 7);
	       liftShiftHighGear.whenPressed(new LiftShiftHighGear());
	       Button liftShiftLowGear = new JoystickButton(operatorJoystick, 8);
	       liftShiftLowGear.whenPressed(new LiftShiftLowGear()  );
	       
	       Button resetEncoder = new JoystickButton(driverJoystick, 5);
	       resetEncoder.whenPressed(new ResetEncoder());
	       
	       Button posSet8 = new JoystickButton(kaiBox, 8);
	       Button posSet9 = new JoystickButton(kaiBox, 9);
	       
	       
	       SmartDashboard.putData("AutonomousLeft", new AutonomousLeft());
	       SmartDashboard.putData("AutonomousCenter", new AutonomousCenter());
	       SmartDashboard.putData("AutonomousRight", new AutonomousRight());
	       
	       
	       
	       
	       
	       

       //Button button = new JoystickButton(stick,9);

       //button.wh
	    //   ileHeld(new OperatorViewTestCommand());
       //button.whileHeld(new OperatorViewTestCommand());
	}
	       
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

	
	
	public double getDriveVerticalJoystick() {
		return driverJoystick.getRawAxis(1);
	}
	public double getDriveHorizontalJoystick() {
		return driverJoystick.getRawAxis(4);
	}
	public double getLiftJoystick() {
		return operatorJoystick.getRawAxis(1);
	}

}

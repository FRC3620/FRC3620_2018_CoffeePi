package org.usfirst.frc3620.misc;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class JoystickAxisTrigger extends Trigger {
	Joystick myJoystick;
	int myAxis;
	
	public JoystickAxisTrigger(Joystick _joystick, int _axis, double threshold) {
		super();
		myJoystick = _joystick;
		myAxis = _axis;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return false;
	}

}

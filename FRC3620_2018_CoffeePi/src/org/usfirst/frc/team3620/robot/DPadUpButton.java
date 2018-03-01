package org.usfirst.frc.team3620.robot;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPadUpButton extends Button {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	Joystick joystick;

	public DPadUpButton(Joystick _joystick) {
		super();
		joystick = _joystick;
	}

	@Override
	public boolean get() {
		int angle = joystick.getPOV(0);

		boolean pressed = false;

		// Is the DPad pushed?
		if (angle > -1) {
			if (angle > 314 || angle < 46) {
				pressed = true;
			}
		}
		// logger.info("POV = {}, pressed (up) = {}", angle, pressed);
		return pressed;
	}

}

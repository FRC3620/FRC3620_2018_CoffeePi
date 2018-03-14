package org.usfirst.frc.team3620.robot;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.usfirst.frc3620.misc.AverageSendableChooser2018;
import org.usfirst.frc3620.misc.PersistentChooser;

import edu.wpi.first.wpilibj.Joystick;

public class ControlPanelWatcher {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	Timer timer = new Timer();
	Joystick controlPanel = Robot.m_oi.kaiBox;

	PersistentChooser trustPersister, posPersister, delayPersister;

	public ControlPanelWatcher() {
		// look at the control panel every 2000 ms (2 seconds)
		logger.info("starting control panel watcher");

		trustPersister = new PersistentChooser(Robot.trustChooser, "trustChooser");
		posPersister = new PersistentChooser(Robot.posChooser, "posChooser");
		delayPersister = new PersistentChooser(Robot.delayChooser, "delayChooser");

		if (controlPanel != null) {
			logger.info("Control Panel appears to be a {}", controlPanel.getName());
		}

		timer.schedule(new MyTimerTask(), 0, 2000);
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			boolean controlPanelPresent = controlPanel != null
					&& controlPanel.getName().equals("Arduino Leonardo") 
					&& controlPanel.getY() < -0.95
					&& controlPanel.getZ() > 0.95;
					// the control panel is connected to the driver's station
					if (controlPanelPresent) {
						trustPersister.setFromControlPanel(readTrustFromControlPanel());
						posPersister.setFromControlPanel(readPosFromControlPanel());
						delayPersister.setFromControlPanel(readDelayFromControlPanel());
					}
					trustPersister.persist();
					posPersister.persist();
					delayPersister.persist();
		}
	}

	void updateChooserFromControlPanel (AverageSendableChooser2018 chooser, int controlPanelIndex, String name) {
		// get all the names from the chooser
		List<String> chooserNames = chooser.getChoiceNames();
		// find which mame is currently chosen in the chooser
		String chooserSelectedName = chooser.getSelectedName();
		// calculate which name is chosen on the control panel
		String controlPanelName = chooserNames.get(controlPanelIndex);

		logger.info("{} control panel = {} {}", name, controlPanelIndex, controlPanelName);
		logger.info("{} chooser = {} of {}", name, chooserSelectedName, chooserNames);

		// the names differ; change the chooser
		if (!controlPanelName.equals(chooserSelectedName)) {
			logger.info("{} mismatch, changing {} to {}", name, chooserSelectedName, controlPanelName);
			chooser.select(controlPanelName);
		}

	}

	int readXxxFromControlPanel(int arduinoStart, int arduinoEnd, String label) {
		int rv = 0;
		for (int i = arduinoEnd+1; i >= arduinoStart+1; i--) {
			rv = rv << 1;
			boolean b = controlPanel.getRawButton(i);
			if (b)
				rv += 1;
			logger.debug("robot button {} = {}, {} is now {}", i, b, label, rv);
		}
		return rv;
	}

	int readPosFromControlPanel() {
		// these bits are 1, 2 on the Arduino end
		return readXxxFromControlPanel(4,  5, "pos");
	}

	int readTrustFromControlPanel() {
		// these bits are 3 on the Arduino end
		return readXxxFromControlPanel(6,  6, "trust");
	}

	int readDelayFromControlPanel() {
		// these bits are 4, 5 and 6 on the Arduino end
		return readXxxFromControlPanel(7,  9, "delay");
	}

}

package org.usfirst.frc.team3620.robot;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;

public class ControlPanelWatcher {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	Timer timer = new Timer();
	Joystick controlPanel = Robot.m_oi.kaiBox;
	
	AverageSendableChooser2018<String> wtdChooser = Robot.wtdChooser;
	AverageSendableChooser2018<String> posChooser = Robot.posChooser;

	public ControlPanelWatcher() {
		// look at the control panel every 2000 ms (2 seconds)
		logger.info("starting control panel watcher");
		timer.schedule(new MyTimerTask(), 0, 2000);
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			boolean controlPanelPresent = controlPanel.getY() < -0.95 && controlPanel.getZ() > 0.95;
			// the control panel is connected to the driver's station
			if (controlPanelPresent) {
				updateChooserFromControlPanel(wtdChooser, readWTDFromControlPanel(), "WTD");
				updateChooserFromControlPanel(posChooser, readPosFromControlPanel(), "pos");
			}
		}
	}
	
	void updateChooserFromControlPanel (AverageSendableChooser2018<String> chooser, int controlPanelIndex, String name) {
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


	int readPosFromControlPanel() {
		int rv = 0;
		// these bits are 7, 8, 9, 10 on the Arduino end
		for (int i = 11; i >= 8; i--) {
			rv = rv << 1;
			boolean b = controlPanel.getRawButton(i);
			if (b)
				rv += 1;
			logger.debug("button {} = {}, pos is now {}", i, b, rv);
		}
		return rv;
	}

    int readWTDFromControlPanel() {
        int rv = 0;
        // these bits are 4, 5, 6 on the Arduino end
        for (int i = 7; i >= 5; i--) {
            rv = rv << 1;
            boolean b = controlPanel.getRawButton(i);
            if (b)
                rv += 1;
            logger.debug("button {} = {}, WTD is now {}", i, b, rv);
        }
        return rv;
    }

}

package org.usfirst.frc.team3620.robot;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class ControlPanelWatcher {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	Timer timer = new Timer();
	Joystick controlPanel = Robot.m_oi.kaiBox;
	AverageSendableChooser autonomousChooser = Robot.m_chooser;
	AverageSendableChooser patternChooser = Robot.m_chooser;

	public ControlPanelWatcher() {
		// look at the control panel every 2000 ms (2 seconds)
		timer.schedule(new MyTimerTask(), 0, 2000);
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			boolean controlPanelPresent = controlPanel.getAxis(AxisType.kZ) > 0.95;
			logger.debug("The z axis is {}", controlPanel.getAxis(AxisType.kZ));
			List<String> autonomousChooserNames = autonomousChooser.getChoiceNames();
			String autonomousChooserSelectedName = autonomousChooser.getSelectedName();
			int autonomousChooserIndex = autonomousChooserNames.indexOf(autonomousChooserSelectedName);
            List<String> patternChooserNames = patternChooser.getChoiceNames();
            String patternChooserSelectedName = patternChooser.getSelectedName();
            int patternChooserIndex = patternChooserNames.indexOf(patternChooserSelectedName);

			if (controlPanelPresent) {
				// the control panel is connected to the driver's station
				int controlPanelAutonomousIndex = readAutonomousFromControlPanel();
				logger.debug("Control panel autonomous is {}", controlPanelAutonomousIndex);

				if (controlPanelAutonomousIndex != autonomousChooserIndex) {
					if (controlPanelAutonomousIndex < autonomousChooserNames.size()) {
						String controlPanelName = autonomousChooserNames.get(controlPanelAutonomousIndex);
                        String chooserName = autonomousChooserNames.get(autonomousChooserIndex);
						logger.info("chooser says autonomous {} ({}), control panel says {} ({}), updating chooser to {}",
								autonomousChooserIndex, chooserName, controlPanelAutonomousIndex, controlPanelName, controlPanelName);

						// update chooser here
						autonomousChooser.select(controlPanelName);
						autonomousChooserSelectedName = controlPanelName;
					} else {
						logger.info("control panel says autonomous {}, don't have that many", controlPanelAutonomousIndex);
					}
				}

				int controlPanelpatternIndex = readpatternFromControlPanel();
                logger.debug("Control panel pattern is {}", controlPanelpatternIndex);

                if (controlPanelpatternIndex != patternChooserIndex) {
                    if (controlPanelpatternIndex < patternChooserNames.size()) {
                        String controlPanelName = patternChooserNames.get(controlPanelpatternIndex);
                        String chooserName = patternChooserNames.get(patternChooserIndex);
                        logger.info("chooser says pattern {} ({}), control panel says {} ({}), updating chooser to {}",
                                patternChooserIndex, chooserName, controlPanelpatternIndex, controlPanelName, controlPanelName);

                        // update chooser here
                        patternChooser.select(controlPanelName);
                        patternChooserSelectedName = controlPanelName;
                    } else {
                        logger.info("control panel says pattern {}, don't have that many", controlPanelpatternIndex);
                    }
                }
			}

			String autonomousPreferencesName = Robot.preferences.getString("autonomous", autonomousChooserNames.get(0));
			if (!autonomousChooserSelectedName.equals(autonomousPreferencesName)) {
				logger.info("changing autonomous in preferences from {} to {}", autonomousPreferencesName, autonomousChooserSelectedName);
				Robot.preferences.putString("autonomous", autonomousChooserSelectedName);
			}
            String patternPreferencesName = Robot.preferences.getString("autonomouspattern", patternChooserNames.get(0));
            if (!patternChooserSelectedName.equals(patternPreferencesName)) {
                logger.info("changing autonomous pattern in preferences from {} to {}", patternPreferencesName, patternChooserSelectedName);
                Robot.preferences.putString("autonomouspattern", patternChooserSelectedName);
            }
		}
	}

	int readAutonomousFromControlPanel() {
		int rv = 0;
		// these bits are 7, 8, 9, 10 on the Arduino end
		for (int i = 11; i >= 8; i--) {
			rv = rv << 1;
			boolean b = controlPanel.getRawButton(i);
			if (b)
				rv += 1;
			logger.debug("button {} = {}, autonomous is now {}", i, b, rv);
		}
		return rv;
	}

    int readpatternFromControlPanel() {
        int rv = 0;
        // these bits are 4, 5, 6 on the Arduino end
        for (int i = 7; i >= 5; i--) {
            rv = rv << 1;
            boolean b = controlPanel.getRawButton(i);
            if (b)
                rv += 1;
            logger.debug("button {} = {}, pattern is now {}", i, b, rv);
        }
        return rv;
    }

}

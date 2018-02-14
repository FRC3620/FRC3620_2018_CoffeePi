package org.usfirst.frc3620.misc;

import java.util.List;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.Preferences;

public class PersistentChooser {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	Preferences preferences = Preferences.getInstance();

	AverageSendableChooser2018 chooser;
	List<String> chooserNames;
	String nameOfPreference;

	public PersistentChooser(AverageSendableChooser2018 c, String n) {
		chooser = c;
		chooserNames = chooser.getChoiceNames();
		nameOfPreference = n;

		String nameFromPreferences = preferences.getString(nameOfPreference, null);
		if (nameFromPreferences != null) {
			logger.info("setting chooser {} to {} from preferences", nameOfPreference, nameFromPreferences);
			chooser.select(nameFromPreferences);
		}
	}

	public void setFromControlPanel(int controlPanelIndex) {
		String selectedName = chooser.getSelectedName();
		int chooserIndex = chooserNames.indexOf(selectedName);
		if (controlPanelIndex != chooserIndex) {
			if (controlPanelIndex < chooserNames.size()) {
				String controlPanelName = chooserNames.get(controlPanelIndex);
				String chooserName = chooserNames.get(chooserIndex);
				logger.info("chooser {} says autonomous {} ({}), control panel says {} ({}), updating chooser to {}",
						nameOfPreference, chooserIndex, chooserName, controlPanelIndex, controlPanelName,
						controlPanelName);

				// update chooser here
				chooser.select(controlPanelName);
				selectedName = controlPanelName;
				persist();
			} else {
				logger.info("control panel for chooser {} says autonomous {}, don't have that many", nameOfPreference,
						controlPanelIndex);
			}
		}
	}

	public void persist() {
		String preferencesSelectedName = preferences.getString(nameOfPreference, chooserNames.get(0));
		String selectedName = chooser.getSelectedName();
		if (!selectedName.equals(preferencesSelectedName)) {
			logger.info("changing {} in preferences from {} to {}", nameOfPreference, preferencesSelectedName,
					selectedName);
			preferences.putString(nameOfPreference, selectedName);
		}
	}
}
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3620.misc;

import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * The {@link AverageSendableChooser2018} class is a useful tool for presenting a selection of options to the
 * {@link SmartDashboard}.
 *
 * <p>For instance, you may wish to be able to select between multiple autonomous modes. You can do
 * this by putting every possible {@link Command} you want to run as an autonomous into a {@link
 * AverageSendableChooser2018} and then put it into the {@link SmartDashboard} to have a list of options appear
 * on the laptop. Once autonomous starts, simply ask the {@link AverageSendableChooser2018} what the selected
 * value is.
 *
 * @param <V> The type of the values to be stored
 */
public class AverageSendableChooser2018<V> extends SendableBase implements Sendable {
  Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

  /**
   * The key for the default value.
   */
  private static final String DEFAULT = "default";
  /**
   * The key for the selected option.
   */
  private static final String SELECTED = "selected";
  /**
   * The key for the option array.
   */
  private static final String OPTIONS = "options";
  /**
   * A map linking strings to the objects the represent.
   */
  private final LinkedHashMap<String, V> m_map = new LinkedHashMap<>();
  private String m_defaultChoice = "";

  /**
   * Instantiates a {@link AverageSendableChooser2018}.
   */
  public AverageSendableChooser2018() {
  }

  /**
   * Adds the given object to the list of options. On the {@link SmartDashboard} on the desktop, the
   * object will appear as the given name.
   *
   * @param name   the name of the option
   * @param object the option
   */
  public void addObject(String name, V object) {
    m_map.put(name, object);
  }

  /**
   * Add the given object to the list of options and marks it as the default. Functionally, this is
   * very close to {@link #addObject(String, Object)} except that it will use this as the default
   * option if none other is explicitly selected.
   *
   * @param name   the name of the option
   * @param object the option
   */
  public void addDefault(String name, V object) {
    requireNonNull(name, "Provided name was null");

    m_defaultChoice = name;
    addObject(name, object);
  }

  /**
   * Returns the selected option. If there is none selected, it will return the default. If there is
   * none selected and no default, then it will return {@code null}.
   *
   * @return the option selected
   */
  public V getSelected() {
    String selected = m_defaultChoice;
    if (m_tableSelected != null) {
      selected = m_tableSelected.getString(m_defaultChoice);
    }
    return m_map.get(selected);
  }

  private NetworkTableEntry m_tableSelected;

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("String Chooser");
    builder.addStringProperty(DEFAULT, () -> {
      return m_defaultChoice;
    }, null);
    builder.addStringArrayProperty(OPTIONS, () -> {
      return m_map.keySet().toArray(new String[0]);
    }, null);
    m_tableSelected = builder.getEntry(SELECTED);
  }
  
  // added by FRC 3620
  public void select (String label) {
	  logger.debug ("setting chooser to {}", label);
	  m_tableSelected.setString(label);
  }
  
  public List<String> getChoiceNames() {
	  return new ArrayList<String>(m_map.keySet());
  }
  
  public String getSelectedName() {
	  return m_tableSelected.getString(null);
  }
  

}

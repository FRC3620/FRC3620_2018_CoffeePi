package org.usfirst.frc.team3620.robot;

import java.text.DecimalFormat;

import org.usfirst.frc3620.logger.DataLogger;
import org.usfirst.frc3620.misc.CANDeviceFinder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;

public class RobotDataLoggingSetup {
	PowerDistributionPanel powerDistributionPanel = null;
	DriverStation driverStation = DriverStation.getInstance();

	public RobotDataLoggingSetup (DataLogger robotDataLogger, CANDeviceFinder canDeviceFinder) {
		robotDataLogger.addDataProvider("robotMode", () -> Robot.currentRobotMode.toString());
		robotDataLogger.addDataProvider("robotModeInt", () -> Robot.currentRobotMode.ordinal());
		robotDataLogger.addDataProvider("batteryVoltage", () -> f2(RobotController.getBatteryVoltage()));

		// do not log extra stuff
		if (false) {
		if (Robot.canDeviceFinder.isPDPPresent()) {
			powerDistributionPanel = new PowerDistributionPanel();
			robotDataLogger.addDataProvider("pdp.totalCurrent", () -> f2(powerDistributionPanel.getTotalCurrent()));
			robotDataLogger.addDataProvider("pdp.totalPower", () -> f2(powerDistributionPanel.getTotalPower()));
			robotDataLogger.addDataProvider("pdp.totalEnergy", () -> f2(powerDistributionPanel.getTotalEnergy()));
			
			// this needs work!

			robotDataLogger.addDataProvider("drive.l0.pdpcurrent", () -> f2(powerDistributionPanel.getCurrent(13)));
			robotDataLogger.addDataProvider("drive.l1.pdpcurrent", () -> f2(powerDistributionPanel.getCurrent(14)));
			robotDataLogger.addDataProvider("drive.l2.pdpcurrent", () -> f2(powerDistributionPanel.getCurrent(15)));

			robotDataLogger.addDataProvider("drive.r3.pdpcurrent", () -> f2(powerDistributionPanel.getCurrent(2)));
			robotDataLogger.addDataProvider("drive.r4.pdpcurrent", () -> f2(powerDistributionPanel.getCurrent(1)));
			robotDataLogger.addDataProvider("drive.r5.pdpcurrent", () -> f2(powerDistributionPanel.getCurrent(0)));
		}
		

		//
		if (RobotMap.driveSubsystemTalonLeft1 != null) {
			robotDataLogger.addDataProvider("drive.l1.power", () -> f2(RobotMap.driveSubsystemTalonLeft1.get()));
			robotDataLogger.addDataProvider("drive.l1.voltage",
					() -> f2(RobotMap.driveSubsystemTalonLeft1.getMotorOutputVoltage()));
			robotDataLogger.addDataProvider("drive.l1.current",
					() -> f2(RobotMap.driveSubsystemTalonLeft1.getOutputCurrent()));
		}
		if (RobotMap.driveSubsystemVictorLeft2 != null) {
			robotDataLogger.addDataProvider("drive.l2.voltage",
					() -> f2(RobotMap.driveSubsystemVictorLeft2.getMotorOutputVoltage()));
		}
		if (RobotMap.driveSubsystemVictorLeft3 != null) {
			robotDataLogger.addDataProvider("drive.l3.voltage",
					() -> f2(RobotMap.driveSubsystemVictorLeft3.getMotorOutputVoltage()));
		}
		if (RobotMap.driveSubsystemVictorLeft4 != null) {
			robotDataLogger.addDataProvider("drive.l4.voltage",
					() -> f2(RobotMap.driveSubsystemVictorLeft4.getMotorOutputVoltage()));
		}
		//
		if (RobotMap.driveSubsystemTalonRight1 != null) {
			robotDataLogger.addDataProvider("drive.r1.power", () -> f2(RobotMap.driveSubsystemTalonRight1.get()));
			robotDataLogger.addDataProvider("drive.r1.voltage",
					() -> f2(RobotMap.driveSubsystemTalonRight1.getMotorOutputVoltage()));
			robotDataLogger.addDataProvider("drive.r1.current",
					() -> f2(RobotMap.driveSubsystemTalonRight1.getOutputCurrent()));
		}
		if (RobotMap.driveSubsystemVictorRight2 != null) {
			robotDataLogger.addDataProvider("drive.r2.voltage",
					() -> f2(RobotMap.driveSubsystemVictorRight2.getMotorOutputVoltage()));
		}
		if (RobotMap.driveSubsystemVictorRight3 != null) {
			robotDataLogger.addDataProvider("drive.r3.voltage",
					() -> f2(RobotMap.driveSubsystemVictorRight3.getMotorOutputVoltage()));
		}
		if (RobotMap.driveSubsystemVictorRight4 != null) {
			robotDataLogger.addDataProvider("drive.r4.voltage",
					() -> f2(RobotMap.driveSubsystemVictorRight4.getMotorOutputVoltage()));
		}
		}

	}

	private DecimalFormat f2Formatter = new DecimalFormat("#.##");

	private String f2(double f) {
		String rv = f2Formatter.format(f);
		return rv;
	}
}
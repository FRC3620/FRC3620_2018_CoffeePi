package org.usfirst.frc.team3620.robot.commands;

public class AutoMoveLiftUpToScaleHeight extends AutoMoveLiftUp {

	@Override
	public double getRequestedEndPos() {
		// TODO Auto-generated method stub
		return 40;

		
	}
	@Override
	public double getMaxPower() {
		return 1.0;

	}

	@Override
	public double getStartingPower() {
		return 0.7;
	}
	
	@Override
	public double getAccelDecelDistance() {
		return 1.0;
	}
}


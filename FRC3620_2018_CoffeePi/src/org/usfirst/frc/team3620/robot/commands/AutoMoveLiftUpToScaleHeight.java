package org.usfirst.frc.team3620.robot.commands;

public class AutoMoveLiftUpToScaleHeight extends AutoMoveLiftUp {

	@Override
	public double getRequestedEndPos() {
		// TODO Auto-generated method stub
		return 50;

		
	}
	@Override
	public double getMaxPower() {
		return 0.6;

	}

	@Override
	public double getStartingPower() {
		return 0.3;
	}
	
	@Override
	public double getAccelDecelDistance() {
		return 4.0;
	}
}


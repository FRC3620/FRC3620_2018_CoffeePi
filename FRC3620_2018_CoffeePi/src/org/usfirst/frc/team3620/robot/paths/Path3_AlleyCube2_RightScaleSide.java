package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path3_AlleyCube2_RightScaleSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0.00)),
				new Waypoint(4, 1.5, Pathfinder.d2r(20)),
		};
	}
	
	@Override
	public double getPathfinderGenVelocityMultiplier() {
		return 0.85;
	}
	@Override
	public boolean getPathfinderReverseMode(){
		return true;
	}
	
	@Override
	public double getPathfinderP() {
		return 0.;
	}

}

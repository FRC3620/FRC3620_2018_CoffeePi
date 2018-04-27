package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path2_AlleyCube_LeftScaleSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0.00)),
				new Waypoint(3.16, 0.87, Pathfinder.d2r(0.00)),
		};
	}
	
	@Override
	public double getPathfinderGenVelocityMultiplier() {
		return 1.0;
	}
	@Override
	public boolean getPathfinderReverseMode(){
		return true;
	}
	
	@Override
	public double getPathfinderP() {
		return 0.09;
	}

}

package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_LeftStart_LeftSwitchEnd extends AbstractPath {

	// Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				/*
			new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
			new Waypoint(7.67, 23.50, Pathfinder.d2r(0)),
			new Waypoint(14.00, 19.90, Pathfinder.d2r(-90)),
			*/
			new Waypoint(1.7, 22.91, Pathfinder.d2r(0)),
			new Waypoint(9.0, 24.75, Pathfinder.d2r(0)),
			new Waypoint(14.0, 21.5, Pathfinder.d2r(-90)),
		};
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.58;		//Slightly slower to keep sharp-turn motor output below 1.0
	}
}



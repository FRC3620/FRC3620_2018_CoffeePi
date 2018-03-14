package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_RightStart_RightScaleSide extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				//Original
				/*
			new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
			new Waypoint(15.0, 2.0, Pathfinder.d2r(0)),
			new Waypoint(23.5, 4.5, Pathfinder.d2r(40)),
				 */
				new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
				new Waypoint(20.2, 5.05, Pathfinder.d2r(20))

		};
	}

	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.68;		//Slightly slower to keep sharp-turn motor output below 1.0
	}

	@Override
	double getPathfinderOutputMultiplier() {

		return 0.62;

	}

	@Override
	double getPathfinderV_MAX() {
		// TODO Auto-generated method stub
		return 4.5;
	}

	@Override
	boolean getPathfinderReverseMode() {
		return false;
	}
}

package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path1_CenterStart_RightSwitch extends AbstractPath {

	//Status: tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
				new Waypoint(5.095, 10.83, Pathfinder.d2r(-40)),
				new Waypoint(10.61, 8.88, Pathfinder.d2r(0)),

				//new Waypoint(5.75, 11.08, Pathfinder.d2r(-50)),
				//new Waypoint(9.92, 8.58, Pathfinder.d2r(0)),
		};
	}
	@Override
	double getPathfinderP() {
		return 0.08;
	}
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.5;
	}

	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.7;		//Slightly slower to keep sharp-turn motor output below 1.0
	}

	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

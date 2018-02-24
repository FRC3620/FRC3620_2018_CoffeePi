package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_CenterStart_RightSwitch extends AbstractPath {

	//Status: not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
				new Waypoint(5.75, 10.83, Pathfinder.d2r(-70)),
				new Waypoint(9.92, 8.58, Pathfinder.d2r(0)),
		};
	}
	@Override
	double getPathfinderP() {
		return 0.001;
	}
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.5;
	}
}

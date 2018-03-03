package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_RightStart_RightSwitchSide extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
				new Waypoint(5.79, 6.33, Pathfinder.d2r(43)),
				new Waypoint(10.0, 8.58, Pathfinder.d2r(0)),
				/*
				 * Old points
				new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
				new Waypoint(5.75, 6.54, Pathfinder.d2r(43)),
				new Waypoint(11.46, 8.58, Pathfinder.d2r(0)),
				*/
		};
	}
}

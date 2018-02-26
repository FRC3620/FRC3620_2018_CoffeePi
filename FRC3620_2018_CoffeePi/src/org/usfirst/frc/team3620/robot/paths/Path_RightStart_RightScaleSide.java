package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_RightStart_RightScaleSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
			new Waypoint(1.58, 4.0833, Pathfinder.d2r(0)),
			new Waypoint(15.0, 3.15, Pathfinder.d2r(0)),
			new Waypoint(22.5, 3.15, Pathfinder.d2r(0)),
			new Waypoint(23.00, 3.15, Pathfinder.d2r(0)),
			new Waypoint(27.0, 21.25, Pathfinder.d2r(-90)),
		};
	}
}

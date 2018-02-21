package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_CenterStart_LeftSwitch extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.5, 11.5, Pathfinder.d2r(0)),
				new Waypoint(10.0, 18.0, Pathfinder.d2r(0)),
		};
	}
}

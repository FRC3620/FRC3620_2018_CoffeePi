package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_RightStart_RightSwitchSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 4.0833, Pathfinder.d2r(0)),
				new Waypoint(5.75, 6.54, Pathfinder.d2r(43)),
				new Waypoint(11.46, 8.58, Pathfinder.d2r(0)),
		};
	}
}

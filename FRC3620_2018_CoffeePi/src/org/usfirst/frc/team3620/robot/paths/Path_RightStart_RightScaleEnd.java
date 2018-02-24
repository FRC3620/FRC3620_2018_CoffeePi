package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_RightStart_RightScaleEnd extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 4.0833, Pathfinder.d2r(0)),
				new Waypoint(15.0, 2.25, Pathfinder.d2r(-5)),
				new Waypoint(22.5, 2.25, Pathfinder.d2r(5)),
				new Waypoint(27.0, 5.75, Pathfinder.d2r(90)),
		};
	}
}

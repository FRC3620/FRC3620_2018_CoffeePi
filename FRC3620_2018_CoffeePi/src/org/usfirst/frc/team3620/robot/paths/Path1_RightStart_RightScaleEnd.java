package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Finish me!
//TODO Test me!

public class Path1_RightStart_RightScaleEnd extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
				new Waypoint(15.0, 2.25, Pathfinder.d2r(0)),
				new Waypoint(23.5, 2.25, Pathfinder.d2r(0)),
				new Waypoint(27.0, 5.75, Pathfinder.d2r(90)),
				/*
				 * Old path:
				new Waypoint(1.58, 4.0833, Pathfinder.d2r(0)),
				new Waypoint(15.0, 2.25, Pathfinder.d2r(-5)),
				new Waypoint(22.5, 2.25, Pathfinder.d2r(5)),
				new Waypoint(27.0, 5.75, Pathfinder.d2r(90)),
				 */
		};
	}
}

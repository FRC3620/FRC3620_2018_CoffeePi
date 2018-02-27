package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_RightStart_RightSwitchEnd extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
				new Waypoint(9.0, 2.25, Pathfinder.d2r(0)),
				new Waypoint(14.0, 5.5, Pathfinder.d2r(90)),
		};
	}
}

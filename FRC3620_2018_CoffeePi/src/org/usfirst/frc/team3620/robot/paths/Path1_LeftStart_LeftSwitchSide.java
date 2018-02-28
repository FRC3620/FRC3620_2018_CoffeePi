package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_LeftStart_LeftSwitchSide extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
			new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
			new Waypoint(5.75, 20.46, Pathfinder.d2r(-43)),
			new Waypoint(9.92, 18.0, Pathfinder.d2r(0)),
			
		};
	}
}



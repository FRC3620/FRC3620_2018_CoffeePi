package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Finish me!
//TODO Test me!

public class Path1_RightStart_LeftScaleSide extends AbstractPath {

	//Status: not tested, unfinished
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 5.5, Pathfinder.d2r(0)),
				new Waypoint(14.0, 4.0, Pathfinder.d2r(0)),
				new Waypoint(19.25, 8.5, Pathfinder.d2r(90)),
				new Waypoint(19.25, 17.5, Pathfinder.d2r(90)),
				new Waypoint(22.00, 22.15, Pathfinder.d2r(0)),
		};
	}
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.68;
	}
//	@Override
//	double getPathfinderP() {
//		return 0.001;
//	}
//	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.57;
	}
}

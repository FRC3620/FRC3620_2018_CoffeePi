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
			/*	new Waypoint(1.58, 5.5, Pathfinder.d2r(0)),
				new Waypoint(12.0, 4.0, Pathfinder.d2r(0)),
				new Waypoint(17.00, 8.5, Pathfinder.d2r(90)),
				new Waypoint(17.00, 17.5, Pathfinder.d2r(90)),
				new Waypoint(22.00, 22.15, Pathfinder.d2r(0)),  */
				
		/*		new Waypoint(1.58, 21.5, Pathfinder.d2r(0)),
				new Waypoint(12.0, 23.0, Pathfinder.d2r(0)),
				new Waypoint(17.25, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(17.25, 9.5, Pathfinder.d2r(-90)),
				new Waypoint(20.25, 5.85, Pathfinder.d2r(0)),  */
				// 27 - y for reflection from the left side
				new Waypoint(1.58, 5.5, Pathfinder.d2r(0)),
				// Straightaway
				new Waypoint(12.0, 4.0, Pathfinder.d2r(0)),
				//First Alley Point
				new Waypoint(18.25, 8.5, Pathfinder.d2r(90)),
				//Second Alley Point Straightaway
				new Waypoint(18.25, 17.5, Pathfinder.d2r(90)),
				//Turn for the endpoint
				new Waypoint(20.25, 19.5, Pathfinder.d2r(-6)),
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
		return 0.55;
	}
}

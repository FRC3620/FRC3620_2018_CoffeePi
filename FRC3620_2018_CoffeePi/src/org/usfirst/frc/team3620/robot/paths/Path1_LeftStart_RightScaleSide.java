package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Finish me!
//TODO Test me!

public class Path1_LeftStart_RightScaleSide extends AbstractPath {

	//Status: not tested, unfinished (start point, end point)
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
			/*	new Waypoint(1.59, 21.5, 0.0001),
				new Waypoint(14.0, 24.0, 0.0001),
				new Waypoint(19.5, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(19.5, 7.5, Pathfinder.d2r(-90)),
				new Waypoint(24.5, 2.0, 0.0001),
				new Waypoint(27.0, 4.5, Pathfinder.d2r(90)),
				*/
				/*
				//Close but hits cubes:
				new Waypoint(1.58, 21.5, Pathfinder.d2r(0)),
				new Waypoint(12.0, 23.0, Pathfinder.d2r(0)),
				new Waypoint(17.25, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(17.25, 9.5, Pathfinder.d2r(-90)),
				new Waypoint(20.25, 5.85, Pathfinder.d2r(0)),
				*/
				// It werks!
				new Waypoint(1.58, 21.5, Pathfinder.d2r(0)),
				new Waypoint(13.0, 23.0, Pathfinder.d2r(0)),
				new Waypoint(18.25, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(18.25, 9.5, Pathfinder.d2r(-90)),
				new Waypoint(21.25, 5.85, Pathfinder.d2r(0)),
				
				
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

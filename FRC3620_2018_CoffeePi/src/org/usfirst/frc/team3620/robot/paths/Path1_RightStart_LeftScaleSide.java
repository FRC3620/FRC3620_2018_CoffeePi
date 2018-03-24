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

				//Kinda works
			/*	new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				//new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
				new Waypoint(7.05, 3.69, Pathfinder.d2r(4)),
				new Waypoint(16.5, 5.55, Pathfinder.d2r(0)), */
				
			/*	new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
				//new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
				new Waypoint(7.05, 3.69, Pathfinder.d2r(4)),
				new Waypoint(16.5, 5.55, Pathfinder.d2r(0)), */
				
				
				new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
				new Waypoint(11.20, 3.49, Pathfinder.d2r(0)),
				new Waypoint(15.45, 8.8, Pathfinder.d2r(90)),
				new Waypoint(15.85, 13.5, Pathfinder.d2r(90)),
				new Waypoint(19.10, 16.05, Pathfinder.d2r(0)),
	
		};
	}
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 1.0;
	}
	@Override
	double getPathfinderP() {
		return 0.003;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {

		return 0.55;

	}
	
	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

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
				
				//PreState Two Cube Points
			/*	new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
				new Waypoint(13.20, 3.49, Pathfinder.d2r(0)),
				new Waypoint(19.05, 8.8, Pathfinder.d2r(90)),
				new Waypoint(19.75, 13.65, Pathfinder.d2r(90)),
				new Waypoint(23.25, 17.75, Pathfinder.d2r(0)), */
				
				//State Two Cube Points
			/*	new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
				new Waypoint(11.50, 3.49, Pathfinder.d2r(0)),
				new Waypoint(18.25, 8.8, Pathfinder.d2r(90)),
				new Waypoint(18.50, 15.15, Pathfinder.d2r(90)),
				new Waypoint(21.25, 17.70, Pathfinder.d2r(0)), */
				
				//Worlds Points
				new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
				new Waypoint(13.20, 3.49, Pathfinder.d2r(0)),
				new Waypoint(19.05, 8.8, Pathfinder.d2r(90)),
				new Waypoint(19.75, 13.65, Pathfinder.d2r(90)),
				new Waypoint(23.25, 17.75, Pathfinder.d2r(0)),

	
		};
	}
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 1.0;
	}
	@Override
	double getPathfinderP() {
		return 0.027;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {

		return 0.50;

	}
	
	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_RightStart_RightScaleSide extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
			//Original
			/*
			new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
			new Waypoint(15.0, 2.0, Pathfinder.d2r(0)),
			new Waypoint(23.5, 4.5, Pathfinder.d2r(40)),
			*/
		/*	new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
			new Waypoint(24.4, 4.99, Pathfinder.d2r(18)) */
			//Hecka fast
			new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
			//new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
			new Waypoint(12.40, 4.04, Pathfinder.d2r(4)),
			new Waypoint(21.25, 6.25, Pathfinder.d2r(0)),
		};
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 1.0;		//Slightly slower to keep sharp-turn motor output below 1.0
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.55;
	}
	
	@Override
	double getPathfinderP() {
		return 0.09;
	}
	
	@Override
	double getPathfinderV_MAX() {
		// TODO Auto-generated method stub
		return 4.5;
	}
	
	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_LeftStart_LeftScaleSide extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				//Original:
				/*
				new Waypoint(1.58, 22.91, 0.0001),
				new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
				new Waypoint(23.5, 22.5, Pathfinder.d2r(-40)),
				*/
			/*	Right Shape
			 * new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				//new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
				new Waypoint(16.00, 22.91, Pathfinder.d2r(-18)),
				new Waypoint(24.4, 20.25, Pathfinder.d2r(0)),
				
				*/
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				//new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),

				new Waypoint(11.40, 22.96, Pathfinder.d2r(-4)),
				new Waypoint(21.25, 19.13, Pathfinder.d2r(0)),

		};
	}
	
	@Override
	double getPathfinderP() {
		return 0.09;
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
	double getPathfinderV_MAX() {
		// TODO Auto-generated method stub
		return 4.5;
	}
	
//	@Override
//	double getPathfinderGenAcceleration() {
//		// TODO Auto-generated method stub
//		return 4;
//	}
	
	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

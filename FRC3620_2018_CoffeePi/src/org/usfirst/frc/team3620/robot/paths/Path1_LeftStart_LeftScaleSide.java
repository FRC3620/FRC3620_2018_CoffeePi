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
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				//new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
				new Waypoint(32.7, 18.85, Pathfinder.d2r(-20)),
		};
	}
	
	@Override
	double getPathfinderP() {
		return 0.001;
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.68;		//Slightly slower to keep sharp-turn motor output below 1.0
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.5;
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
		return false;
	}
}

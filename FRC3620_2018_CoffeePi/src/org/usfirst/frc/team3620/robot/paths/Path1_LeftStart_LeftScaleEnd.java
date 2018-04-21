package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_LeftStart_LeftScaleEnd extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(15.0, 25.0, Pathfinder.d2r(5)),
				new Waypoint(22.5, 25.25, Pathfinder.d2r(0)),
				new Waypoint(25.0, 23.25, Pathfinder.d2r(-80)),
		};
	}
	
	@Override
	double getPathfinderP() {
		return 0.5;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.5;
	}
	@Override
	double getPathfinderV_MAX() {
		// TODO Auto-generated method stub
		return 3.0;
	}
	@Override 
	boolean getPathfinderReverseMode(){
		return true;
	}
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 1.0;		//Slightly slower to keep sharp-turn motor output below 1.0
	}
}

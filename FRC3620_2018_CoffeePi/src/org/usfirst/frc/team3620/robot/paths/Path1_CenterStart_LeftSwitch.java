package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path1_CenterStart_LeftSwitch extends AbstractPath {

	//Status: FINAL @ St. Joe = Don't Touch, please
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				//new Waypoint(1.58, 11.5, Pathfinder.d2r(0)),  //center-of-ds start point
				new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
				//new Waypoint(5.75, 14.75, Pathfinder.d2r(80)),  //for center-of-ds start point
				new Waypoint(5.70, 15.192, Pathfinder.d2r(80)),
				new Waypoint(9.82, 17.30, Pathfinder.d2r(0)),
		};
	}

	@Override
	double getPathfinderP() {
		return 0.001;
	}

	@Override
	double getPathfinderOutputMultiplier() {
		return 0.53;
	}

	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.6;		//Slightly slower to keep sharp-turn motor output below 1.0
	}

	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

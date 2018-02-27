package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path1_CenterStart_LeftSwitch extends AbstractPath {

	//Status: tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				//new Waypoint(1.58, 11.5, Pathfinder.d2r(0)),  //center-of-ds start point
				new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
				//new Waypoint(5.75, 14.75, Pathfinder.d2r(80)),  //for center-of-ds start point
				new Waypoint(5.75, 15.542, Pathfinder.d2r(70)),
				new Waypoint(9.92, 18.0, Pathfinder.d2r(0)),
		};
	}
	
	@Override
	double getPathfinderP() {
		return 0.001;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.5;
	}
}

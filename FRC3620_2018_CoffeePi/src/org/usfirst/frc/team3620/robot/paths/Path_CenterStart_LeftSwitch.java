package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_CenterStart_LeftSwitch extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				//new Waypoint(1.58, 11.5, Pathfinder.d2r(0)),  //TODO edit waypoints for actual center-to-bumper dimension
				new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
				//new Waypoint(5.75, 14.75, Pathfinder.d2r(80)),
				new Waypoint(5.75, 15.542, Pathfinder.d2r(70)),
				new Waypoint(9.92, 18.0, Pathfinder.d2r(0)),
		};
	}
	
	@Override
	double getPathfinderP() {
		// TODO Auto-generated method stub
		return 0.001;
	}
	
	@Override
	double getPathfinderSpeedMultiplier() {
		// TODO Auto-generated method stub
		return 0.5;
	}
}

package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_LeftStart_LeftScale extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.7, 21.5, 0.0001),
				new Waypoint(15.0, 25.0, Pathfinder.d2r(5)),
				new Waypoint(22.5, 25.25, Pathfinder.d2r(-1.0)),
				new Waypoint(27.0, 22.5, Pathfinder.d2r(-90)),
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

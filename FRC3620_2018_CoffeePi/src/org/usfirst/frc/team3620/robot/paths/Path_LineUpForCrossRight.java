package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_LineUpForCrossRight extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		// TODO Auto-generated method stub
		return new Waypoint[] {
				new Waypoint(1.58, 4.09, Pathfinder.d2r(0)),
				new Waypoint(13.20, 3.49, Pathfinder.d2r(0)),
				new Waypoint(19.05, 8.8, Pathfinder.d2r(90)),
				new Waypoint(19.75, 13.65, Pathfinder.d2r(90)),
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

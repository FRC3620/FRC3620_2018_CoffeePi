package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_BackUpFromScale extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				
				new Waypoint(2, 0, Pathfinder.d2r(0)),
				new Waypoint(0, 0, Pathfinder.d2r(0)),
		};
		// TODO Auto-generated method stub
	}
	@Override
	double getPathfinderP() {
		return 0.001;
	}
	
	@Override
	boolean getPathfinderReverseMode() {
		return false;
	}
	@Override
	double getPathfinderOutputMultiplier() {
		// TODO Auto-generated method stub
		return 0.5;
	}

}

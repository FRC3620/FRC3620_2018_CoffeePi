package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path1_LeftStart_LeftScaleSide extends AbstractPath {

	//Status: Not tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.58, 22.91, 0.0001),
				new Waypoint(15.0, 25.0, Pathfinder.d2r(0)),
				new Waypoint(23.5, 22.5, Pathfinder.d2r(-40)),
		};
	}
	
	@Override
	double getPathfinderP() {
		// TODO Auto-generated method stub
		return 0.001;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		// TODO Auto-generated method stub
		return 0.5;
	}
}

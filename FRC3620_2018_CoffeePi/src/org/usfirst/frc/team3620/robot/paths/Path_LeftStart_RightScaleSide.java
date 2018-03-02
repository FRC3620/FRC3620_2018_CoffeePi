package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_LeftStart_RightScaleSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(1.59, 21.5, 0.0001),
				new Waypoint(14.0, 24.0, 0.0001),
				new Waypoint(19.5, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(19.5, 7.5, Pathfinder.d2r(-90)),
				new Waypoint(24.5, 2.0, 0.0001),
				new Waypoint(27.0, 4.5, Pathfinder.d2r(90)),
				
		};
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		// TODO Auto-generated method stub
		return 0.6;
	}
//	@Override
//	double getPathfinderP() {
//		// TODO Auto-generated method stub
//		return 0.001;
//	}
//	
//	@Override
//	double getPathfinderOutputMultiplier() {
//		// TODO Auto-generated method stub
//		return 0.5;
//	}
}

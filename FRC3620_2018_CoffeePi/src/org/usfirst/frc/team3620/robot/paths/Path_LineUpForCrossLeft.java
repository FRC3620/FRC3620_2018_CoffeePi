package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_LineUpForCrossLeft extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		// TODO Auto-generated method stub
		return new Waypoint[] {
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(14.025, 23.0, Pathfinder.d2r(0)),
				//Mark: "18.45 is smack dab in the middle"
				new Waypoint(18.65, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(18.65, 16.35, Pathfinder.d2r(-90)),
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
		return 0.5;
	}
	
	@Override
	public boolean getPathfinderReverseMode() {
		return true;
	}
}

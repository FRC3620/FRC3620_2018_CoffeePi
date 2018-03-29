package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Finish me!
//TODO Test me!

public class Path2_RightScaleSide_AlleyCube extends AbstractPath {

	//Status: Not tested, unfinished
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 1, Pathfinder.d2r(0)),
				new Waypoint(3.16, 0.45, Pathfinder.d2r(0.00)),
		};
	}
	@Override
	public boolean getPathfinderReverseMode() {
		return false;
	}
	
	@Override
	public double getPathfinderP() {
		return 0.09;
	}
}

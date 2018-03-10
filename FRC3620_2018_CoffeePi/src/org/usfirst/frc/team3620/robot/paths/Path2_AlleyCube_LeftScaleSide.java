package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path2_AlleyCube_LeftScaleSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(5.67, 1.25, Pathfinder.d2r(30.00)),
		};
	}
	
	@Override
	public boolean getPathfinderReverseMode(){
		return true;
	}

}

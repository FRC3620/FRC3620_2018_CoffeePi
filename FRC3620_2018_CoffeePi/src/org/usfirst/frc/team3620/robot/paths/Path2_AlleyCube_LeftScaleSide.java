package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path2_AlleyCube_LeftScaleSide extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(3.17, .75, Pathfinder.d2r(0)),
		};
	}
	
	@Override
	public boolean getPathfinderReverseMode(){
		return true;
	}
	
	@Override
	public double getPathfinderP() {
		return 0.09;
	}

}

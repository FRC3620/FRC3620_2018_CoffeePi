package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_LeftStart_DriveAcrossLine extends AbstractPath {

	//Status: Not tested
	@Override
	public Waypoint[] getMyWaypoints(){
		return new Waypoint[]{
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(13.0, 22.91, Pathfinder.d2r(0)),
		};
	}
}

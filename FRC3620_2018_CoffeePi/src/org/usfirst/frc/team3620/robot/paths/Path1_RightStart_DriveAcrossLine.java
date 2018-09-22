package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Test me!

public class Path1_RightStart_DriveAcrossLine extends AbstractPath {
	
	//Status: Not tested
	@Override
	public Waypoint[] getMyWaypoints(){
		return new Waypoint[]{
			new Waypoint(1.58, 4.08, Pathfinder.d2r(0)),
			new Waypoint(13.0, 4.08, Pathfinder.d2r(0)),
		};
	}
	@Override
	public boolean getPathfinderReverseMode() {
		return true;
	}
}

//TODO Physically verify RightStart y value is 4.08ft.
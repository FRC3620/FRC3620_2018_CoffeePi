package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path2_RightSwitch_CubeZone extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
		// TODO Auto-generated method stub
		new Waypoint(0, 6.00, Pathfinder.d2r(0)),
		new Waypoint(2.50, 2.00, Pathfinder.d2r(-90)),
		};
	}
	
}
		
	

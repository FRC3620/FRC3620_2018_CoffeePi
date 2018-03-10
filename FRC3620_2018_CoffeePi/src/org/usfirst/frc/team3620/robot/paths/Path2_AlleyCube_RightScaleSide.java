package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path2_AlleyCube_RightScaleSide extends AbstractPath {

	
		// TODO Auto-generated method stub
		@Override
		Waypoint[] getMyWaypoints() {
			return new Waypoint[] {
					new Waypoint(0, 1.25, Pathfinder.d2r(0)),
					new Waypoint(3.67, 0, Pathfinder.d2r(-30.00)),
			};
		}
	

}

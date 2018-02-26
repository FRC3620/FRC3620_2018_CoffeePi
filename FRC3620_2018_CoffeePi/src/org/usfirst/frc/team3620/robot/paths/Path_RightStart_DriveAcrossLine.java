package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path_RightStart_DriveAcrossLine extends AbstractPath {
		@Override
		public Waypoint[] getMyWaypoints(){
			return new Waypoint[]{
				new Waypoint(1.58, 4.0833, Pathfinder.d2r(0)),
				new Waypoint(13.0, 4.0833, Pathfinder.d2r(0)),
			};
		}
}

package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class ZeHomelessPathHaven extends AbstractPath {

	/*
	 *  This command is a safe haven for any a wandering wayward Waypoint.
	 *  This is where all paths that do not have a command to call home 
	 *  may now find shelter, in hopes of someday being wanted.
	 */
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 23, 0.001),
				
		};
	}
}

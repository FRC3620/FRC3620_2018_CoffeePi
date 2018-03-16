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
				new Waypoint(0, 22.91, Pathfinder.d2r(0)),
				new Waypoint(11.5, 22.91, Pathfinder.d2r(-45)),
				new Waypoint(12.5, 16.91, Pathfinder.d2r(-90)),
				
		};
	}
	
	@Override
	public boolean getPathfinderReverseMode() {
		return true;
	}
	
	@Override
	public double getPathfinderP() {
		return 0.6;
	}
}

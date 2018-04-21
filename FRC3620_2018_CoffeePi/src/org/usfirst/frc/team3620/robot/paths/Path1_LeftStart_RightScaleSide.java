package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Finish me!
//TODO Test me!

public class Path1_LeftStart_RightScaleSide extends AbstractPath {

	//Status: not tested, unfinished (start point, end point)
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
			/*	new Waypoint(1.59, 21.5, 0.0001),
				new Waypoint(14.0, 24.0, 0.0001),
				new Waypoint(19.5, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(19.5, 7.5, Pathfinder.d2r(-90)),
				new Waypoint(24.5, 2.0, 0.0001),
				new Waypoint(27.0, 4.5, Pathfinder.d2r(90)),
				*/
				//PreState Points
			/*	new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(13.425, 23.0, Pathfinder.d2r(0)),
				//Mark: "18.45 is smack dab in the middle"
				new Waypoint(18.45, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(18.95, 12.95, Pathfinder.d2r(-90)),
				new Waypoint(21.30, 9.75, Pathfinder.d2r(0)),  */
				
				//State Points
		/*		new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(13.425, 23.0, Pathfinder.d2r(0)),
				//Mark: "18.45 is smack dab in the middle"
				new Waypoint(18.45, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(18.95, 12.95, Pathfinder.d2r(-90)),
				new Waypoint(21.30, 9.75, Pathfinder.d2r(0)),  */
				
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(11.5, 23.0, Pathfinder.d2r(0)),  // was new Waypoint(11.425, 23.0, Pathfinder.d2r(0)),
				//Mark: "18.45 is smack dab in the middle"
				new Waypoint(17, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(17.8, 12.95, Pathfinder.d2r(-90)),
				new Waypoint(19.30, 9.75, Pathfinder.d2r(0)),
				
		};
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 1.0;
	}
	@Override
	double getPathfinderP() {
		return 0.027;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.7;
	}
	
	@Override
	public boolean getPathfinderReverseMode() {
		return true;
	}
}

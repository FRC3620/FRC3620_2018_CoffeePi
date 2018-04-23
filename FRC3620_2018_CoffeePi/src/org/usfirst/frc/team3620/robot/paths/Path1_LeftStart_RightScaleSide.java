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
				
				//Kind of good
		/*		new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(11.5, 23.0, Pathfinder.d2r(0)),  // was new Waypoint(11.425, 23.0, Pathfinder.d2r(0)),
				//Mark: "18.45 is smack dab in the middle"
				new Waypoint(18.2, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(18.3, 12.95, Pathfinder.d2r(-90)),
				new Waypoint(21.0, 9.75, Pathfinder.d2r(0)), */
				
				new Waypoint(1.58, 22.91, Pathfinder.d2r(0)),
				new Waypoint(10.5, 23.0, Pathfinder.d2r(0)),  // was new Waypoint(11.425, 23.0, Pathfinder.d2r(0)),
				//Mark: "18.45 is smack dab in the middle"
				new Waypoint(18.4, 18.5, Pathfinder.d2r(-90)),
				new Waypoint(18.4, 11.5, Pathfinder.d2r(-90)),
				new Waypoint(22.5, 7.50, Pathfinder.d2r(0)),
				
		};
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 0.85;
	}
	@Override
	double getPathfinderP() {
		return 0.17;
	}
	
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.7;
	}
	
	@Override
	public boolean getPathfinderReverseMode() {
		return true;
	}
	@Override
	double getPathfinderGenAcceleration() {
		// TODO Auto-generated method stub
		return 1.5;
	}
	
	@Override
	double getHeadingCorrectionFactor() {
		// return 0.8 / 80.0;
		return 2 * 0.8 / 80.0;
	}


}

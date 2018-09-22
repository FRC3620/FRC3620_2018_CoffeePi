package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path1_CenterStart_RightSwitch extends AbstractPath {

	//Status: tested
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
			/* Return to for Two Cube
			 * 	new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
			 * //new Waypoint(5.75, 14.75, Pathfinder.d2r(80)),  //for center-of-ds start point
				new Waypoint(4.25, 12.330, Pathfinder.d2r(-45)),
				new Waypoint(9.22, 11.630, Pathfinder.d2r(0))
			 */

				new Waypoint(1.58, 13.083, Pathfinder.d2r(0)),
				new Waypoint(5.095, 11.03, Pathfinder.d2r(-40)),
				new Waypoint(10.61, 9.78, Pathfinder.d2r(0)),

				
				//new Waypoint(5.75, 11.08, Pathfinder.d2r(-50)),
				//new Waypoint(9.92, 8.58, Pathfinder.d2r(0)),
		};
	}
	@Override
	double getPathfinderP() {
		return 0.1;
	}
	@Override
	double getPathfinderOutputMultiplier() {
		return 0.60;
	}
	
	@Override
	double getPathfinderGenVelocityMultiplier() {
		return 1.0;		//Slightly slower to keep sharp-turn motor output below 1.0
	}
	
	@Override
	boolean getPathfinderReverseMode() {
		return true;
	}
}

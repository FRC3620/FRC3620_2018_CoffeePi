package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path2_TurnALittle extends AbstractPath {
	double degreesTurned;
	boolean left;
	public Path2_TurnALittle(double degreesTurned, boolean left){
		super();
		this.degreesTurned = degreesTurned;
		this.left = left;
	}
	
	@Override
	Waypoint[] getMyWaypoints() {
		// TODO Auto-generated method stub
		if(left == true) {
			return new Waypoint[] {
					new Waypoint(0.50,	0.50, Pathfinder.d2r(degreesTurned)),
					new Waypoint(0.00, 0.00, Pathfinder.d2r(0)),
				};
		} else {
			return new Waypoint[] {
					new Waypoint(0.50,	-0.50, Pathfinder.d2r(-degreesTurned)),
					new Waypoint(0.00, 0.00, Pathfinder.d2r(0)),
				};
		}
		
		
	}
	
	@Override
	public boolean getPathfinderReverseMode() {
		return true;
	}

}

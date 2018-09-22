package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Path3_LeftScaleSide_AlleyCube2 extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		// TODO Auto-generated method stub
		return new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(4.76, 1.55, Pathfinder.d2r(37.50)),
		};
	}
	
	@Override
	public boolean getPathfinderReverseMode(){
		return false;
	}

	@Override
	public double getPathfinderGenVelocityMultiplier() {
		return 0.85;
	}
	
	@Override
	public double getPathfinderP() {
		return 0.09;
	}
}
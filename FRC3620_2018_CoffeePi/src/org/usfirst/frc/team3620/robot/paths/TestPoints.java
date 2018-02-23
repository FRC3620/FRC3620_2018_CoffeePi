package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class TestPoints extends AbstractPath {

	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				/*
				new Waypoint(0, 23, 0.001),
				new Waypoint(14, 25, 0.001),
				new Waypoint(19.5, 16, Pathfinder.d2r(-90)),
				*/
				new Waypoint(0, 0, 0.001),
				new Waypoint(8, 0, 0.0001),
				//new Waypoint(4, -4, Pathfinder.d2r(-90))
				/*
				//TODO convert to feet
				new Waypoint(-3.33, -0.7, Pathfinder.d2r(-30)),
				new Waypoint(-1.18, -1.5, Pathfinder.d2r(-5)),
				new Waypoint(-0.52, -1.5, Pathfinder.d2r(10)),				//Pathfinder.d2r(10)
				new Waypoint(0, -0.95, Pathfinder.d2r(88)),
				new Waypoint(0, -0.75, Pathfinder.d2r(90)),
				new Waypoint(0, -0.7, Pathfinder.d2r(90)),
				*/
		};
	}
}

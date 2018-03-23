package org.usfirst.frc.team3620.robot.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

//TODO Finish me!
//TODO Test me!

public class Path2_LeftScaleSide_AlleyCube extends AbstractPath {

	//Status: Not tested, unfinished
	@Override
	Waypoint[] getMyWaypoints() {
		return new Waypoint[] {
				new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(3.76, .85, Pathfinder.d2r(0.00)),
		};
	}
	
	public boolean getPathfinderReverseMode() {
		return false;
	}
	
	public double getPathfinderP() {
		return 0.09;
	}
	
}

/* package org.usfirst.frc.team3620.robot.paths;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
/* public class Path2_LeftScaleSide_AlleyCube extends CommandGroup {

    public Path2_LeftScaleSide_AlleyCube() {
//        addSequential(Subpath_LeftScaleSide_AlleyCubeA);
//        addSequential(Subpath_LeftScaleSide_AlleyCubeB);
    }
}
*/

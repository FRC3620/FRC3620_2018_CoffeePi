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
				new Waypoint(5.11, 0.60, Pathfinder.d2r(0.00)),
		};
	}
	@Override
	public boolean getPathfinderReverseMode() {
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

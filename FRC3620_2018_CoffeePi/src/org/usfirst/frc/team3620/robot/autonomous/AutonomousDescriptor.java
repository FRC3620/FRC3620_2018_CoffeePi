package org.usfirst.frc.team3620.robot.autonomous;

import org.usfirst.frc.team3620.robot.paths.AbstractPath;

public class AutonomousDescriptor {
	Class<? extends AbstractPath> pathClass;
	WhereToPutCube whereToPutCube;
	
	public AutonomousDescriptor (Class<? extends AbstractPath> clazz, WhereToPutCube _whereToPutCube) {
		this.pathClass = clazz;
		/*
			try {
				this.path = (AbstractPath) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		this.whereToPutCube = _whereToPutCube;
	}

	@Override
	public String toString() {
		return "AutonomousDescriptor [path=" + pathClass + ", whereToPutCube=" + whereToPutCube + "]";
	}
}

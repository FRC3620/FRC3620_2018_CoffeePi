package org.usfirst.frc.team3620.robot.autonomous;

import org.usfirst.frc.team3620.robot.paths.AbstractPath;

public class AutonomousDescriptor {
	Class<? extends AbstractPath> pathClass;
	WhereToPutCube whereToPutCube;
	
	public AutonomousDescriptor (Class<? extends AbstractPath> clazz, WhereToPutCube _whereToPutCube) {
		this.pathClass = clazz;
		this.whereToPutCube = _whereToPutCube;
	}

	@Override
	public String toString() {
		return "AutonomousDescriptor [pathClass=" + pathClass + ", whereToPutCube=" + whereToPutCube + "]";
	}

	public AbstractPath getPath() {
		try {
			return (AbstractPath) pathClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public WhereToPutCube getWhereToPutCube() {
		return whereToPutCube;
	}
}

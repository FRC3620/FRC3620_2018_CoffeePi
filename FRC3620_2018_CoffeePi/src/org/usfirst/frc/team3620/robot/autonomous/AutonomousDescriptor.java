package org.usfirst.frc.team3620.robot.autonomous;

import org.usfirst.frc.team3620.robot.paths.AbstractPath;

public class AutonomousDescriptor {
	Class<? extends AbstractPath> pathClass; 
	WhereToPutCube whereToPutCube;
	TwoCube twoCube;
	ThreeCube threeCube;
	public AutonomousDescriptor (Class<? extends AbstractPath> clazz, WhereToPutCube _whereToPutCube, TwoCube _twoCube, ThreeCube _threeCube) {
		this.pathClass = clazz;
		this.whereToPutCube = _whereToPutCube;
		this.twoCube = _twoCube;
	}

	@Override
	public String toString() {
		return "AutonomousDescriptor [pathClass=" + pathClass + ", whereToPutCube=" + whereToPutCube + ", twoCube=" + twoCube + "]";
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

	public TwoCube getTwoCube() {
		return twoCube;
	}
	public ThreeCube getThreeCube() {
		return threeCube;
	}
}
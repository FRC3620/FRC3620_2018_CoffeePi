package org.usfirst.frc.team3620.robot.autonomous;

public enum StartingLocation {
	NONE('N'), LEFT('L'), CENTER('C'), RIGHT('R');

	private char c;
	private static StartingLocation[] vals = values();

	private StartingLocation(char c) {
		this.c = c;
	}

	public char getChar() {
		return this.c;
	}

	public StartingLocation next() {
		return vals[(this.ordinal() + 1) % vals.length];
	}

	public StartingLocation previous() {
		return vals[(this.ordinal() - 1 + vals.length) % vals.length];
	}
	
	public static StartingLocation byIndex(int i) {
		return vals[i];
	}

	public static StartingLocation byChar(char c) {
		for (int i = 0; i < vals.length; i++) {
			if (vals[i].c == c) {
				return vals[i];
			}
		}
		return null;
	}

}

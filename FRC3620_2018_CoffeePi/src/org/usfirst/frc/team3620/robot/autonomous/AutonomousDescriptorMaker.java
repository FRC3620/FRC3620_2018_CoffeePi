package org.usfirst.frc.team3620.robot.autonomous;

import java.util.SortedMap;
import java.util.TreeMap;

import org.usfirst.frc.team3620.robot.paths.*;

public class AutonomousDescriptorMaker {
	static SortedMap<String,AutonomousDescriptor> firstPathMap = new TreeMap<>();
	
	static boolean eliminationsSoWeDoTheScaleFromTheEndWithNoTwoCube = true;
	static {
		// we are starting from left, and we own the left side of the scale, and we trust our partner
		if (eliminationsSoWeDoTheScaleFromTheEndWithNoTwoCube) {
			firstPathMap.put("LLLY",new AutonomousDescriptor(Path1_LeftStart_LeftScaleEnd.class, WhereToPutCube.SCALE, TwoCube.NO));
			firstPathMap.put("LRLY",new AutonomousDescriptor(Path1_LeftStart_LeftScaleEnd.class, WhereToPutCube.SCALE, TwoCube.NO));
			firstPathMap.put("LLLN",new AutonomousDescriptor(Path1_LeftStart_LeftScaleEnd.class, WhereToPutCube.SCALE, TwoCube.NO));
			firstPathMap.put("LRLN",new AutonomousDescriptor(Path1_LeftStart_LeftScaleEnd.class, WhereToPutCube.SCALE, TwoCube.NO));
		} else {
			firstPathMap.put("LLLY",new AutonomousDescriptor(Path1_LeftStart_LeftScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
			firstPathMap.put("LRLY",new AutonomousDescriptor(Path1_LeftStart_LeftScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
			firstPathMap.put("LLLN",new AutonomousDescriptor(Path1_LeftStart_LeftScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
			firstPathMap.put("LRLN",new AutonomousDescriptor(Path1_LeftStart_LeftScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		}

		// we are starting from left, and we own the right side of the scale, and we DO trust our partner
		firstPathMap.put("LLRY",new AutonomousDescriptor(Path_LineUpForCrossLeft.class, WhereToPutCube.SCALE, TwoCube.YES));
		firstPathMap.put("LRRY",new AutonomousDescriptor(Path_LineUpForCrossLeft.class, WhereToPutCube.SCALE, TwoCube.YES));
		
		// we are starting from left, and we own the right side of the scale, and we DO NOT trust our partner
		firstPathMap.put("LLRN",new AutonomousDescriptor(Path1_LeftStart_RightScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		firstPathMap.put("LRRN",new AutonomousDescriptor(Path1_LeftStart_RightScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		
		//we are staring from the right, and we own the left side of the scale, and we DO trust our partner
		firstPathMap.put("RLLY",new AutonomousDescriptor(Path_LineUpForCrossRight.class, WhereToPutCube.SCALE, TwoCube.YES));
		firstPathMap.put("RRLY",new AutonomousDescriptor(Path_LineUpForCrossRight.class, WhereToPutCube.SCALE, TwoCube.YES));
		
		//we are staring from the right, and we own the left side of the scale, and we DO NOT trust our partner
		firstPathMap.put("RLLN",new AutonomousDescriptor(Path1_RightStart_LeftScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		firstPathMap.put("RRLN",new AutonomousDescriptor(Path1_RightStart_LeftScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));	
		
		//we are staring from the right, and we own the right side of the scale
		firstPathMap.put("RLRY",new AutonomousDescriptor(Path1_RightStart_RightScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));	
		firstPathMap.put("RRRY",new AutonomousDescriptor(Path1_RightStart_RightScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		firstPathMap.put("RLRN",new AutonomousDescriptor(Path1_RightStart_RightScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		firstPathMap.put("RRRN",new AutonomousDescriptor(Path1_RightStart_RightScaleSide.class, WhereToPutCube.SCALE, TwoCube.YES));
		
		//firstPathMap.put("",Path_#_L.class);
	}
	public static AutonomousDescriptor makeAutonomousDescriptor(char myPosition, char switchPosition,char scalePosition,boolean trustPartner) {
		AutonomousDescriptor autonomousDescriptor;
		if(myPosition == 'C') {
			if(switchPosition == 'L') {
				autonomousDescriptor = new AutonomousDescriptor(Path1_CenterStart_LeftSwitch.class, WhereToPutCube.SWITCH, TwoCube.NO);
			} else {
				autonomousDescriptor = new AutonomousDescriptor(Path1_CenterStart_RightSwitch.class, WhereToPutCube.SWITCH, TwoCube.NO);
			}
		}else if(myPosition == 'N') {
				return null;
		} else {
			String address = ""+myPosition+switchPosition+scalePosition+(trustPartner?'Y':'N');
			autonomousDescriptor = firstPathMap.get(address);
		}
		return autonomousDescriptor;
	}
	
	
	
	public static void main(String[] args) {
		char[] lr = new char[] {'L', 'R'};
		char[] lcr = new char[] {'L', 'C', 'R'};
		for (char me : lcr) {
			for (char sw : lr) {
				for (char sc : lr) {
					test(me, sw, sc, true);
					test(me, sw, sc, false);
				}
			}
		}
	}
	
	static void test(char myPosition, char switchPosition,char scalePosition,boolean trustPartner) {
		AutonomousDescriptor result = makeAutonomousDescriptor(myPosition, switchPosition, scalePosition, trustPartner);
		System.out.println("" + myPosition + " " + switchPosition + " " + scalePosition + " " + trustPartner + " -> " + result);
	}
}

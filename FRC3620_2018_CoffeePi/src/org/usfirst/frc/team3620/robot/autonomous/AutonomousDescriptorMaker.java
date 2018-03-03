package org.usfirst.frc.team3620.robot.autonomous;

import java.util.SortedMap;
import java.util.TreeMap;

import org.usfirst.frc.team3620.robot.paths.*;

public class AutonomousDescriptorMaker {
	static SortedMap<String,AutonomousDescriptor> firstPathMap = new TreeMap<>();
	static {
		firstPathMap.put("LLLY",new AutonomousDescriptor(Path1_LeftStart_LeftScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("LLRY",new AutonomousDescriptor(Path1_LeftStart_RightScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("LRLY",new AutonomousDescriptor(Path1_LeftStart_LeftScaleEnd.class, WhereToPutCube.SCALE));
		firstPathMap.put("LRRY",new AutonomousDescriptor(Path1_LeftStart_RightScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("LLLN",new AutonomousDescriptor(Path1_LeftStart_LeftSwitchEnd.class, WhereToPutCube.SWITCH));
		firstPathMap.put("LLRN",new AutonomousDescriptor(Path1_LeftStart_LeftSwitchEnd.class, WhereToPutCube.SWITCH));
		firstPathMap.put("LRLN",new AutonomousDescriptor(Path1_LeftStart_LeftScaleEnd.class, WhereToPutCube.SCALE));
		firstPathMap.put("LRRN",new AutonomousDescriptor(Path1_LeftStart_RightScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLLY",new AutonomousDescriptor(Path1_RightStart_LeftScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLRY",new AutonomousDescriptor(Path1_RightStart_RightScaleEnd.class, WhereToPutCube.SCALE));
		firstPathMap.put("RRLY",new AutonomousDescriptor(Path1_RightStart_LeftScaleSide.class, WhereToPutCube.SCALE));		
		firstPathMap.put("RRRY",new AutonomousDescriptor(Path1_RightStart_RightScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLLN",new AutonomousDescriptor(Path1_RightStart_LeftScaleSide.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLRN",new AutonomousDescriptor(Path1_RightStart_RightScaleEnd.class, WhereToPutCube.SCALE));
		firstPathMap.put("RRLN",new AutonomousDescriptor(Path1_RightStart_RightSwitchEnd.class, WhereToPutCube.SWITCH));		
		firstPathMap.put("RRRN",new AutonomousDescriptor(Path1_RightStart_RightSwitchEnd.class, WhereToPutCube.SWITCH));
		
		//firstPathMap.put("",Path_#_L.class);
	}
	public static AutonomousDescriptor makeAutonomousDescriptor(char myPosition, char switchPosition,char scalePosition,boolean trustPartner) {
		AutonomousDescriptor autonomousDescriptor;
		if(myPosition == 'C') {
			if(switchPosition == 'L') {
				autonomousDescriptor = new AutonomousDescriptor(Path1_CenterStart_LeftSwitch.class, WhereToPutCube.SWITCH);
			} else {
				autonomousDescriptor = new AutonomousDescriptor(Path1_CenterStart_RightSwitch.class, WhereToPutCube.SWITCH);
			}
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

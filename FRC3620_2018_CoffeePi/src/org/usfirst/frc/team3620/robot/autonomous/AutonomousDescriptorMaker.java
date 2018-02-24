package org.usfirst.frc.team3620.robot.autonomous;

import java.util.SortedMap;
import java.util.TreeMap;

import org.usfirst.frc.team3620.robot.paths.Path_1_A;
import org.usfirst.frc.team3620.robot.paths.Path_1_E;
import org.usfirst.frc.team3620.robot.paths.Path_1_G;
import org.usfirst.frc.team3620.robot.paths.Path_2_B;
import org.usfirst.frc.team3620.robot.paths.Path_2_C;
import org.usfirst.frc.team3620.robot.paths.Path_3_D;
import org.usfirst.frc.team3620.robot.paths.Path_3_F;
import org.usfirst.frc.team3620.robot.paths.Path_3_H;

public class AutonomousDescriptorMaker {
	static SortedMap<String,AutonomousDescriptor> firstPathMap = new TreeMap<>();
	static {
		firstPathMap.put("LLLY",new AutonomousDescriptor(Path_1_E.class, WhereToPutCube.SCALE));
		firstPathMap.put("LLRY",new AutonomousDescriptor(Path_1_G.class, WhereToPutCube.SCALE));
		firstPathMap.put("LRLY",new AutonomousDescriptor(Path_1_E.class, WhereToPutCube.SCALE));
		firstPathMap.put("LRRY",new AutonomousDescriptor(Path_1_G.class, WhereToPutCube.SCALE));
		firstPathMap.put("LLLN",new AutonomousDescriptor(Path_1_A.class, WhereToPutCube.SWITCH));
		firstPathMap.put("LLRN",new AutonomousDescriptor(Path_1_A.class, WhereToPutCube.SWITCH));
		firstPathMap.put("LRLN",new AutonomousDescriptor(Path_1_E.class, WhereToPutCube.SCALE));
		firstPathMap.put("LRRN",new AutonomousDescriptor(Path_1_G.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLLY",new AutonomousDescriptor(Path_3_F.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLRY",new AutonomousDescriptor(Path_3_H.class, WhereToPutCube.SCALE));
		firstPathMap.put("RRLY",new AutonomousDescriptor(Path_3_F.class, WhereToPutCube.SCALE));		
		firstPathMap.put("RRRY",new AutonomousDescriptor(Path_3_H.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLLN",new AutonomousDescriptor(Path_3_F.class, WhereToPutCube.SCALE));
		firstPathMap.put("RLRN",new AutonomousDescriptor(Path_3_H.class, WhereToPutCube.SCALE));
		firstPathMap.put("RRLN",new AutonomousDescriptor(Path_3_D.class, WhereToPutCube.SWITCH));		
		firstPathMap.put("RRRN",new AutonomousDescriptor(Path_3_D.class, WhereToPutCube.SWITCH));
		
		//firstPathMap.put("",Path_#_L.class);
	}
	public static AutonomousDescriptor makeAutonomousDescriptor(char myPosition, char switchPosition,char scalePosition,boolean trustPartner) {
		AutonomousDescriptor autonomousDescriptor;
		if(myPosition == 'C') {
			if(switchPosition == 'L') {
				autonomousDescriptor = new AutonomousDescriptor(Path_2_B.class, WhereToPutCube.SWITCH);
			} else {
				autonomousDescriptor = new AutonomousDescriptor(Path_2_C.class, WhereToPutCube.SWITCH);
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

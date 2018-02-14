package org.usfirst.frc.team3620.robot.paths;

import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;



public class PathPicker {
	static Logger logger = EventLogging.getLogger(PathPicker.class, Level.INFO);
	static SortedMap<String,Class> firstPathMap = new TreeMap<>();
	static {
		firstPathMap.put("LLLY",Path_1_E.class);
		firstPathMap.put("LLRY",Path_1_G.class);
		firstPathMap.put("LRLY",Path_1_E.class);
		firstPathMap.put("LRRY",Path_1_G.class);
		firstPathMap.put("LLLN",Path_1_A.class);
		firstPathMap.put("LLRN",Path_1_A.class);
		firstPathMap.put("LRLN",Path_1_E.class);
		firstPathMap.put("LRRN",Path_1_G.class);
		firstPathMap.put("RLLY",Path_3_F.class);
		firstPathMap.put("RLRY",Path_3_H.class);
		firstPathMap.put("RRLY",Path_3_F.class);		
		firstPathMap.put("RRRY",Path_3_H.class);
		firstPathMap.put("RLLN",Path_3_F.class);
		firstPathMap.put("RLRN",Path_3_H.class);
		firstPathMap.put("RRLN",Path_3_D.class);		
		firstPathMap.put("RRRN",Path_3_D.class);
		
		//firstPathMap.put("",Path_#_L.class);
	}
	public static Class pickFirstPath(char myPosition, char switchPosition,char scalePosition,boolean trustPartner) {
		if(myPosition == 'C') {
			if(switchPosition == 'L') {
				return(Path_2_B.class);
			} else {
				return(Path_2_C.class);
			}
		} else {
			String address = ""+myPosition+switchPosition+scalePosition+(trustPartner?'Y':'N');
			logger.info("Path to pick: "+address);
			return(firstPathMap.get(address));
		}
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
		Class result = pickFirstPath(myPosition, switchPosition, scalePosition, trustPartner);
	}
}

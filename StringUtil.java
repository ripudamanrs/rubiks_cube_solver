
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {
	public static Map<Integer, String> colorMap = new HashMap<Integer, String>() {
		{
			put(0, "G");
			put(1, "B");
			put(2, "W");
			put(3, "Y");
			put(4, "O");
			put(5, "R");
		}
	};
	
	public static Map<String, List<Integer>> moveMap = new HashMap<String, List<Integer>>() {
		{
			put("F", Arrays.asList(new Integer[]{0,1}));
			put("F1", Arrays.asList(new Integer[]{0,3}));
			put("R", Arrays.asList(new Integer[]{1,1}));
			put("R1", Arrays.asList(new Integer[]{1,3}));
			put("U", Arrays.asList(new Integer[]{5,1}));
			put("U1", Arrays.asList(new Integer[]{5,3}));
			put("B", Arrays.asList(new Integer[]{2,1}));
			put("B1", Arrays.asList(new Integer[]{2,3}));
			put("L", Arrays.asList(new Integer[]{3,1}));
			put("L1", Arrays.asList(new Integer[]{3,3}));
			put("D", Arrays.asList(new Integer[]{4,1}));
			put("D1", Arrays.asList(new Integer[]{4,3}));
		}
	};
	
	public static Map<String, String> descMap = new LinkedHashMap<String, String>() {
		{
			put("F", "Front-Clockwise");
			put("F1", "Front-AntiClockwise");
			put("R", "Right-Clockwise");
			put("R1", "Right-AntiClockwise");
			put("U", "Up-Clockwise");
			put("U1", "Up-AntiClockwise");
			put("B", "Back-Clockwise");
			put("B1", "Back-AntiClockwise");
			put("L", "Left-Clockwise");
			put("L1", "Left-AntiClockwise");
			put("D", "Down-Clockwise");
			put("D1", "Down-AntiClockwise");
		}
	};
	
	public static Map<List<Integer>, String> reverseMoveMap = new HashMap<List<Integer>, String>() {
		{
			put(Arrays.asList(new Integer[]{0,1}), "F");
			put(Arrays.asList(new Integer[]{0,3}), "F1");
			put(Arrays.asList(new Integer[]{1,1}), "R");
			put(Arrays.asList(new Integer[]{1,3}), "R1");
			put(Arrays.asList(new Integer[]{5,1}), "U");
			put(Arrays.asList(new Integer[]{5,3}), "U1");
			put(Arrays.asList(new Integer[]{2,1}), "B");
			put(Arrays.asList(new Integer[]{2,3}), "B1");
			put(Arrays.asList(new Integer[]{3,1}), "L");
			put(Arrays.asList(new Integer[]{3,3}), "L1");
			put(Arrays.asList(new Integer[]{4,1}), "D");
			put(Arrays.asList(new Integer[]{4,3}), "D1");
		}
	};
	
	public static Map<Integer,Map<Integer,List<Integer>>> createPTConfig() {
		Map<Integer,Map<Integer,List<Integer>>> panelTwistConfig = new LinkedHashMap<>();
		Map<Integer,List<Integer>> temp0 = new LinkedHashMap<>();
		temp0.put(5, Arrays.asList(new Integer[]{2,3,4}));
		temp0.put(1, Arrays.asList(new Integer[]{0,1,2}));
		temp0.put(4, Arrays.asList(new Integer[]{6,7,0}));
		temp0.put(3, Arrays.asList(new Integer[]{4,5,6}));
		panelTwistConfig.put(0, temp0);
		Map<Integer,List<Integer>> temp1 = new LinkedHashMap<>();
		temp1.put(5, Arrays.asList(new Integer[]{4,5,6}));
		temp1.put(2, Arrays.asList(new Integer[]{0,1,2}));
		temp1.put(4, Arrays.asList(new Integer[]{4,5,6}));
		temp1.put(0, Arrays.asList(new Integer[]{4,5,6}));
		panelTwistConfig.put(1, temp1);
		Map<Integer,List<Integer>> temp2 = new LinkedHashMap<>();
		temp2.put(5, Arrays.asList(new Integer[]{6,7,0}));
		temp2.put(3, Arrays.asList(new Integer[]{0,1,2}));
		temp2.put(4, Arrays.asList(new Integer[]{2,3,4}));
		temp2.put(1, Arrays.asList(new Integer[]{4,5,6}));
		panelTwistConfig.put(2, temp2);
		Map<Integer,List<Integer>> temp3 = new LinkedHashMap<>();
		temp3.put(5, Arrays.asList(new Integer[]{0,1,2}));
		temp3.put(0, Arrays.asList(new Integer[]{0,1,2}));
		temp3.put(4, Arrays.asList(new Integer[]{0,1,2}));
		temp3.put(2, Arrays.asList(new Integer[]{4,5,6}));
		panelTwistConfig.put(3, temp3);
		Map<Integer,List<Integer>> temp4 = new LinkedHashMap<>();
		temp4.put(0, Arrays.asList(new Integer[]{2,3,4}));
		temp4.put(1, Arrays.asList(new Integer[]{2,3,4}));
		temp4.put(2, Arrays.asList(new Integer[]{2,3,4}));
		temp4.put(3, Arrays.asList(new Integer[]{2,3,4}));
		panelTwistConfig.put(4, temp4);
		Map<Integer,List<Integer>> temp5 = new LinkedHashMap<>();
		temp5.put(2, Arrays.asList(new Integer[]{6,7,0}));
		temp5.put(1, Arrays.asList(new Integer[]{6,7,0}));
		temp5.put(0, Arrays.asList(new Integer[]{6,7,0}));
		temp5.put(3, Arrays.asList(new Integer[]{6,7,0}));
		panelTwistConfig.put(5, temp5);
		return panelTwistConfig;
	}
}

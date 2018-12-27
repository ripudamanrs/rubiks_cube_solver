
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameState {
	
	private List<List<String>> rcConfig;
	private List<Integer> move;
	private String config;
	
	public GameState() {
		super();
		this.rcConfig = new ArrayList<List<String>>();
		this.rcConfig.add(new ArrayList<String>());
		this.rcConfig.add(new ArrayList<String>());
		this.rcConfig.add(new ArrayList<String>());
		this.rcConfig.add(new ArrayList<String>());
		this.rcConfig.add(new ArrayList<String>());
		this.rcConfig.add(new ArrayList<String>());
	}
	
	public GameState(List<List<String>> rcConfig, List<Integer> move) {
		super();
		this.rcConfig = rcConfig;
		this.config = convertToString();
		this.move = move;
	}

	public List<List<String>> getRcConfig() {
		return rcConfig;
	}

	public List<Integer> getMove() {
		return this.move;
	}
	
	public void setRcConfig(List<String> initialMoves) {
		for(Map.Entry<Integer,String> entry : StringUtil.colorMap.entrySet()) {
			String c = entry.getValue();
			this.rcConfig.set(entry.getKey(), Arrays.asList(new String[]{ c,c,c,c,c,c,c,c }));
		}
		for(String move : initialMoves) {
			Integer faceIndex = StringUtil.moveMap.get(move).get(0);
			Integer degree = StringUtil.moveMap.get(move).get(1);
			clockwiseFaceTwist(faceIndex, degree);
			panelTwist(StringUtil.createPTConfig().get(faceIndex), degree);
		}
		this.config = convertToString();
	}

	public List<GameState> getSuccessors() {
		List<GameState> successors = new ArrayList<>();
		for(int faceIndex=0;faceIndex<6;faceIndex++) {
			if(this.move != null && !this.move.isEmpty()) {
				if(faceIndex == this.move.get(0)) {
					continue;
				}
			}
			for(int degree=1;degree<4;degree++) {
				List<List<String>> rcConfigNew = new ArrayList<>();
				for(int j=0;j<6;j++) {
					List<String> faceConfig = new ArrayList<>();
					for(int k=0;k<8;k++) {
						faceConfig.add(rcConfig.get(j).get(k));
					}
					rcConfigNew.add(faceConfig);
				}
				GameState gs = new GameState(rcConfigNew,Arrays.asList(new Integer[]{faceIndex,degree}));
				gs.clockwiseFaceTwist(faceIndex, degree);
				gs.panelTwist(StringUtil.createPTConfig().get(faceIndex), degree);
				gs.config = gs.convertToString();
				successors.add(gs);
			}
		}
		return successors;
	}
	
	private void clockwiseFaceTwist(Integer faceIndex, Integer degree) {
		List<String> face = rcConfig.get(faceIndex);
		for(int i=0;i<degree;i++) {
			Collections.rotate(face, 2);
		}
	}
	
	private void panelTwist(Map<Integer,List<Integer>> faceMap, Integer degree) {
		for(int i=0;i<degree;i++) {
			Iterator<Integer> iter = faceMap.keySet().iterator();
			Integer firstKey = iter.next();
			List<String> temp1 = getColors(firstKey,faceMap.get(firstKey));
			List<String> temp2;
			while(iter.hasNext()) {
				Integer key = iter.next();
				temp2 = getColors(key,faceMap.get(key));
				setColors(key, faceMap.get(key), temp1);
				temp1 = temp2;
			}
			setColors(firstKey, faceMap.get(firstKey), temp1);
		}
	}
	
	private List<String> getColors(Integer faceIndex, List<Integer> squares) {
		List<String> colorList = new ArrayList<>();
		for(Integer square : squares) {
			colorList.add(this.rcConfig.get(faceIndex).get(square));
		}
		return colorList;
	}
	
	private void setColors(Integer faceIndex, List<Integer> squares, List<String> newColors) {
		for(int i=0;i<3;i++) {			
			this.rcConfig.get(faceIndex).set(squares.get(i), newColors.get(i));
		}
	}
	
	public Boolean isGoalState(GameState goal) {
		if(this.equals(goal)) {
			return true;
		}
		return false;
	}

	private String convertToString() {
		StringBuilder sb = new StringBuilder();
		for(List<String> values : this.rcConfig) {
			for(String val : values) {
				sb.append(val);
			}
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((config == null) ? 0 : config.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (config == null) {
			if (other.config != null)
				return false;
		} else if (!config.equals(other.config))
			return false;
		return true;
	}
}



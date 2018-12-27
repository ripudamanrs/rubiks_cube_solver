
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RubiksCube {

	private static Long nodesExpanded = 0L;
	public static List<List<Integer>> meetInMiddle(GameState gs) {
		GameState goal = new GameState();
		goal.setRcConfig(new ArrayList<String>());
		if(gs.isGoalState(goal)) {
			return null;
		}
		
		Map<GameState,GameStateInfo> openForward = new HashMap<>();
		openForward.put(gs, new GameStateInfo(0,0,new ArrayList<List<Integer>>()));
		Map<GameState,GameStateInfo> closeForward = new HashMap<>();
		
		Map<GameState,GameStateInfo> openBackward = new HashMap<>();
		openBackward.put(goal, new GameStateInfo(0,0,new ArrayList<List<Integer>>()));		
		Map<GameState,GameStateInfo> closeBackward = new HashMap<>();
		
		Map<String, String> reversedDir = new HashMap<String, String>();
		reversedDir.put("F","F1");
		reversedDir.put("R","R1");
		reversedDir.put("L","L1");
		reversedDir.put("D","D1");
		reversedDir.put("U","U1");
		reversedDir.put("B","B1");
		
		Integer epsilon = 1;
		Integer U = Integer.MAX_VALUE;
		List<List<Integer>> finalPath = new ArrayList<>();

		while(!openForward.isEmpty() && !openBackward.isEmpty()) {
			Integer C;
			Integer prminf = Integer.MAX_VALUE;
			Integer costf = Integer.MAX_VALUE;
			GameState nf = new GameState();
			Integer prminb = Integer.MAX_VALUE;
			Integer costb = Integer.MAX_VALUE;
			GameState nb = new GameState();
			for(Map.Entry<GameState, GameStateInfo> entry : openForward.entrySet()) {
				if(entry.getValue().priority < prminf) {
					prminf = entry.getValue().priority;
					nf = entry.getKey();
				}
				if(entry.getValue().cost < costf) {
					costf = entry.getValue().cost;
				}
			}
			for(Map.Entry<GameState, GameStateInfo> entry : openBackward.entrySet()) {
				if(entry.getValue().priority < prminb) {
					prminb = entry.getValue().priority;
					nb = entry.getKey();
				}	
				if(entry.getValue().cost < costb) {
					costb = entry.getValue().cost;
				}
			}
			C = Math.min(prminf, prminb);
			if(U < costf + costb + epsilon) {
				return finalPath;
			}
			if(C == prminf) {
				GameStateInfo cfinfo = openForward.remove(nf);
				closeForward.put(nf, cfinfo);
				List<GameState> successors = nf.getSuccessors();
				nodesExpanded++;
				Integer ncfcost = cfinfo.cost + 1;
				for(GameState item : successors) {
					if((openForward.containsKey(item)) && (openForward.get(item).cost <= ncfcost)) {
						continue;
					}
					if((closeForward.containsKey(item)) && (closeForward.get(item).cost <= ncfcost)) {
						continue;
					}
					openForward.remove(item);
					closeForward.remove(item);
					List<List<Integer>> newPath = new ArrayList<>();
					for(List<Integer> values : cfinfo.path) {
						newPath.add(values);
					}
					newPath.add(item.getMove());
					openForward.put(item, new GameStateInfo(ncfcost, 2*ncfcost, newPath));
					if(openBackward.containsKey(item)) {
						Integer cost = ncfcost + openBackward.get(item).cost;
						if(U >= cost) {
							U = cost;
							finalPath.clear();
							System.out.println("Forward Path Length: "+openForward.get(item).path.size());
							System.out.println("Backward Path Length: "+openBackward.get(item).path.size());
							List<List<Integer>> reverseOpenBackward = new ArrayList<>(openBackward.get(item).path);
							Collections.reverse(reverseOpenBackward);
							finalPath.addAll(openForward.get(item).path);
							for(List<Integer> move : reverseOpenBackward) {
								move.set(1, 4 - move.get(1));
								finalPath.add(move);
							}
							System.out.println("Total Path Length: "+finalPath.size());
						}
						
					}
				}
				
			} else {
				GameStateInfo cbinfo = openBackward.remove(nb);
				closeBackward.put(nb, cbinfo);
				List<GameState> successors = nb.getSuccessors();
				nodesExpanded++;
				Integer ncbcost = cbinfo.cost + 1;
				for(GameState item : successors) {
					if((openBackward.containsKey(item)) && (openBackward.get(item).cost <= ncbcost)) {
						continue;
					}
					if((closeBackward.containsKey(item)) && (closeBackward.get(item).cost <= ncbcost)) {
						continue;
					}
					openBackward.remove(item);
					closeBackward.remove(item);
					List<List<Integer>> newPath = new ArrayList<>();
					for(List<Integer> values : cbinfo.path) {
						newPath.add(values);
					}
					newPath.add(item.getMove());
					openBackward.put(item, new GameStateInfo(ncbcost, 2*ncbcost, newPath));
					if(openForward.containsKey(item)) {
						Integer cost = ncbcost + openForward.get(item).cost;
						if(U >= cost) {
							U = cost;
							finalPath.clear();
							System.out.println("Forward Path Length: "+openForward.get(item).path.size());
							System.out.println("Backward Path Length: "+openBackward.get(item).path.size());
							List<List<Integer>> reverseOpenBackward = new ArrayList<>(openBackward.get(item).path);
							Collections.reverse(reverseOpenBackward);
							finalPath.addAll(openForward.get(item).path);
							for(List<Integer> move : reverseOpenBackward) {
								move.set(1, 4 - move.get(1));
								finalPath.add(move);
							}
							System.out.println("Total Path Length: "+finalPath.size());
						}
					}
				}
			}
		}
		return finalPath;
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to Rubik's Cube!");
		System.out.println("You are allowed the following moves");
		for(Map.Entry<String, String> e : StringUtil.descMap.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
		System.out.println();
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter moves separated by spaces: ");
		String input = s.nextLine();
		s.close();
		String[] inputArray = input.split(" ");
		for(String move : inputArray) {
			if(!StringUtil.descMap.containsKey(move)) {
				System.out.println("Incorrect moves passed, please try again.");
				System.exit(0);
			}
		}
		System.out.println();
		GameState start = new GameState();
		start.setRcConfig(Arrays.asList(inputArray));
		List<List<Integer>> solution = meetInMiddle(start);
		System.out.println("Total Number of Nodes Expanded: "+nodesExpanded);
		System.out.print("Solution: ");
		if(solution == null) {
			System.out.println("The cube is already at goal state");
			System.exit(0);
		}
		for(List<Integer> values : solution) {
			if (values.get(1) == 2) {
				List<Integer> idx = Arrays.asList(new Integer[]{values.get(0), values.get(1) - 1});
				System.out.print(StringUtil.reverseMoveMap.get(idx) + " ");
				System.out.print(StringUtil.reverseMoveMap.get(idx) + " ");
			}
			else {
			    System.out.print(StringUtil.reverseMoveMap.get(values) + " ");
			}
		}
		System.out.println();
	}
}

class GameStateInfo {
	Integer cost;
	Integer priority;
	List<List<Integer>> path;
	
	public GameStateInfo(Integer cost, Integer priority, List<List<Integer>> path) {
		super();
		this.cost = cost;
		this.priority = priority;
		this.path = path;
	}
}

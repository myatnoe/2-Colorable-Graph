import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TwoColorable {

	private Graph graph;
	private Color[] color;
	private int[] level;
	private int[] parent;
	private ArrayList<Integer> cycle;
	
	/*/
	 * graph with int value vertices
	 */
	public TwoColorable(Graph g){
		this.graph = g;
		this.color = new Color[this.graph.V()];
		this.level = new int[this.graph.V()];
		this.parent = new int[this.graph.V()];
		this.cycle = new ArrayList<Integer>();
		findOddCycle();
	}

	public boolean isTwoColorable(){
		return cycle.size() == 0;
	}
	/*
	 * String outputfile : name of output file
	 * print out 'yes' if there is an odd cycle
	 * print out 'no' and the odd cycle if there is one
	 */
	public void outputOddCycle(String outputfile) throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		if(cycle.size() == 0){
			out.println("Yes");
			// print out all the non 0 level vertices
			for(int i = 1; i < level.length; i++){
				if(level[i] != 0){
					String c = level[i]%2 == 0?"Blue":"Red";
					out.println(i + " " + c);
				}
			}
		}else{
			out.println("No");
			for(int i = 0; i < cycle.size(); i++){
				out.println(cycle.get(i).toString());
			}
		}
		out.close();
	}

	/*
	 * return the first odd cycle in the graph that can find
	 */
	public void findOddCycle(){
		// initialize
		for(int i = 1; i < this.graph.V(); i++){
			color[i] = Color.WHITE;
			level[i] = 0;
			parent[i] = 0;
		}
		// find odd cycle
		int startVertex = getFirstUnvisited();
		while(startVertex != 0){
			cycle = getCycle(startVertex);
			if(cycle.size() != 0){
				break;
			}
			startVertex = getFirstUnvisited(startVertex);
		}
	}
	
	/*
	 * int start : root node of BFS tree
	 * - use BFS to check the edge if the graph is 2-colorable
	 */
	private ArrayList<Integer> getCycle(int start){
		Queue<Integer> q = new LinkedList<Integer>();
		
		q.offer(start);
		color[start] = Color.GREY;
		while(!q.isEmpty()){
			int current = q.poll();
			for(int n : this.graph.adj(current)){
				if(color[n] == Color.WHITE){
					color[n] = Color.GREY;
					level[n] = level[current] + 1;
					parent[n] = current;
					q.offer(n);
				}else{
					// the adj vertex is grey or black - need to check for cycle
					int diff = level[n] - level[current];
					if(level[n] == level[current]){
						// there are odd vertices in cycle - printout
						return giveUncolorable(n, current);
					}
				}
			}
			color[current] = Color.BLACK; 
		}
		return new ArrayList<Integer>();
	}
	
	/*
	 * return odd cycle between from first vertex to second vertex
	 * - go back to the root from both vertices.
	 */
	private ArrayList<Integer> giveUncolorable(int first, int second){
		ArrayList<Integer> result = new ArrayList<Integer>();
		// for one to root
		result.add(first);
		while(parent[first] != 0){ // parent == 0 => root
			result.add(parent[first]);
			first = parent[first];
		}
		// for two to root
		Stack<Integer> temp = new Stack<Integer>();
		temp.push(second);
		while(parent[second] != 0){
			temp.push(parent[second]);
			second = parent[second];
		}
		temp.pop(); // remove duplicate root
		for(int i: temp){
			result.add(i);
		}
		return result;
	}
	
	/*
	 * return unvisited vertex after previous root
	 */
	private int getFirstUnvisited(int start){
		for(int i = start; i < this.graph.V(); i++){
			if(this.graph.isValidVertex(i) && color[i] == Color.WHITE) {
				level[i] = 1;
				return i;
			}
		}
		return 0;
	}
	
	/*
	 * the first unvisited vertex
	 */
	private int getFirstUnvisited(){
		return getFirstUnvisited(1);
	}
}

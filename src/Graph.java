import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	private int V;
	private LinkedList<Integer>[] adj;
	private Color[] color;
	private int[] level;
	private int[] parent;
	
	@SuppressWarnings("unchecked")
	public Graph(int size){
		this.V = size+1; // the vertex starts from 1 to size+1
		this.adj = new LinkedList[size+1];
		for(int i = 1; i < this.adj.length; i++){
			this.adj[i] = new LinkedList<Integer>();
		}
	}
	
	public int V(){
		return this.V;
	}
	
	public void addEdge(int v, int u){
		adj[v].add(u);
		adj[u].add(v);
	}
	
	public Iterable<Integer> adj(int v){
		return adj[v];
	}
	
	public void printGraph(){
		for(int i = 1; i < this.V; i++){
			System.out.print(i+" ");
			System.out.println(this.adj[i].toString());
		}
	}

	/*
	 * String outputfile : name of output file
	 */
	public void outputOddCycle(String outputfile) throws FileNotFoundException {
		// initialize for checking
		color = new Color[this.V];
		level = new int[this.V];
		parent = new int[this.V];
		
		for(int i = 1; i < this.V; i++){
			color[i] = Color.WHITE;
			level[i] = 0;
			parent[i] = 0;
		}
		
		// get odd cycle and write in output file
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		int startNode = getFirstUnvisited();
		while(startNode != 0){
			ArrayList<Integer> cycle = getCycle(startNode);
			if(cycle.size() != 0){
				// there is cycle - need to print into the file
				out.println("no");
				for(int i = 0; i < cycle.size(); i++){
					out.println(cycle.get(i).toString());
				}
				out.close();return;
			}
			startNode = getFirstUnvisited(startNode);
		}
		out.println("yes");
		out.close();
		return;
	}
	
	// work as BFS to check the edge if the graph is 2-colorable
	/*
	 * int start : root node of BFS tree
	 */
	private ArrayList<Integer> getCycle(int start){
		Queue<Integer> q = new LinkedList<Integer>();
		
		q.offer(start);
		color[start] = Color.GREY;
		while(!q.isEmpty()){
			int current = q.poll();
			for(int n : this.adj[current]){
				if(color[n] == Color.WHITE){
					color[n] = Color.GREY;
					level[n] = level[current] + 1;
					parent[n] = current;
					q.offer(n);
				}else{
					// the adj node is grey or black - need to check for cycle
					int diff = level[n] - level[current];
					if(diff % 2 == 0){
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
	private ArrayList<Integer> giveUncolorable(int one, int two){
		ArrayList<Integer> result = new ArrayList<Integer>();
		// for one to root
		result.add(one);
		while(parent[one] != 0){ // parent == 0 => root
			result.add(parent[one]);
			one = parent[one];
		}
		// for two to root
		Stack<Integer> temp = new Stack<Integer>();
		temp.push(two);
		while(parent[two] != 0){
			temp.push(parent[two]);
			two = parent[two];
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
		for(int i = start; i < this.V; i++){
			if(color[i] == Color.WHITE) {
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

import java.util.LinkedList;

public class Graph {
	private int V;
	private LinkedList<Integer>[] adj;
	
	/* undirected graph */
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
	
	public boolean isValidVertex(int n){
		return adj[n].size() != 0;
	}
}

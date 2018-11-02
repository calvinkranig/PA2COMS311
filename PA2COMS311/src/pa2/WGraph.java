package pa2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author ckranig, ans66
 *
 */
public class WGraph {
	private class EdgeComparator implements Comparator<Node>{
		@Override
		public int compare(Node n1, Node n2) {
			int xdiff = n1.x -n2.x;
			if(xdiff == 0){
				return n1.y - n2.y;
			}
			return xdiff;
		}
	}
	private class Edge{
		private Node dst;
		private int weight;
		public Edge(Node dst, int weight) {
			this.dst = dst;
			this.weight = weight;
		}
		
		public Node dst(){
			return dst;
		}
		
		public int weight(){
			return weight;
		}
	}
	
	private class Node{
		private int x;
		private int y;
		private Node parent;
		private LinkedList<Edge> edges;
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			edges = new LinkedList<Edge>();
			parent = null;
		}
		public LinkedList<Edge> edges() {
			return edges;
		}
		public int x() {
			return x;
		}
		public int y() {
			return y;
		}
		public void setParent(Node n){
			parent = n;
		}
		public void addAdjacent(Node n, int weight){
			edges.add(new Edge(n, weight));
		}
	}
	
	private Node[][] graph;
	
	public WGraph(String fName) {
		
		// TODO Auto-generated constructor stub
	}
	
	private void parseFile(String fName){
		
	}
	
	/**
	 * pre: ux, uy, vx, vy are valid coordinates of vertices u and v
	 * in the graph
	 * 
	 * post: return arraylist contains even number of integers,
	 * for any even i,
	 * i-th and i+1-th integers in the array represent
	 * the x-coordinate and y-coordinate of the i-th vertex
	 * in the returned path (path is an ordered sequence of vertices)
	 * @param uz
	 * @param uy
	 * @param vx
	 * @param vy
	 * @return
	 */
	public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
		PriorityQueue<Node> minheap = new PriorityQueue<Node>(new EdgeComparator());
		
		
		
		return null;
	}
	
	public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
		//Do Dijsktra BFS and stop when first node in Set S is pulled from the PQ
		return null;
	}
	
	public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
		return null;
	}
	
	private ArrayList<Integer> returnPath(Node end){
		ArrayList<Integer> path = new ArrayList<Integer>();
		Node cur = end;
		while(cur != null){
			path.add(cur.x);
			path.add(cur.y);
			cur = end.parent;
		}
		return null;
	}

}

package pa2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author ckranig, ans66
 *
 */
public class WGraph {
	private class NodeComparator implements Comparator<Node>{
		@Override
		public int compare(Node n1, Node n2) {
			return n1.distanceToSource-n2.distanceToSource;
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
	
	protected class Node{
		private int x;
		private int y;
		private Node parent;
		private int distanceToSource;
		private LinkedList<Edge> edges;
		private boolean inQ;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			edges = new LinkedList<Edge>();
			parent = null;
			distanceToSource = Integer.MAX_VALUE;
			inQ = false;
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
		public void setDST(int dst){
			this.distanceToSource = dst;
		}
		public void addAdjacent(Node n, int weight){
			edges.add(new Edge(n, weight));
		}
		
		/**
		 * Removes latest added node from list
		 */
		public void removeAdjacent(){
			edges.removeLast();
		}
		
	}
	
	private Node[] nodes;
	private Node[][] Graph;
	
	public WGraph(String fName) {
		Graph = new Node[Integer.MAX_VALUE][Integer.MAX_VALUE];
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
	 */
	public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
		PriorityQueue<Node> minheap = new PriorityQueue<Node>(new NodeComparator());
		resetNodes(ux, uy);
		
		//add all nodes to PQ
		for(Node n : this.nodes){
			minheap.add(n);
			n.inQ = true;
		}
		//perform Dijkstras
		Node dst = Dijkstras(minheap, vx, vy);
		if(dst!= null){
			return returnPath(dst);
		}
		else{
			return null;
		}
	}
	
	/**
	 * pre: ux, uy are valid coordinates of vertex u from the graph
	 * The S arraylist contains even number of intergers
	 * for any even i,
	 * i-th and i+1-th integers in the array represent
	 * the x-coordinate and y-coordinate of the i-th vertex
	 * in the set S.
	 * 
	 * post: return arraylist contains even number of integers,
	 * for any even i,
	 * i-th and i+1-th integers in the array represent
	 * the x-coordinate and y-coordinate of the i-th vertex
	 * in the returned path (path is an ordered sequence of vertices)
	 */
	public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
		//Do Dijsktra BFS and stop when first node in Set S is pulled from the PQ
		PriorityQueue<Node> minheap = new PriorityQueue<Node>(new NodeComparator());
		HashSet<Node> set = toNodeSet(S);
		resetNodes(ux, uy);
		
		//add all nodes to PQ
		for(Node n : this.nodes){
			minheap.add(n);
			n.inQ = true;
		}	
		//perform Dijkstras
		Node dst = SetDijkstras(minheap, set);
		if(dst!= null){
			return returnPath(dst);
		}
		else{
			return null;
		}
	}
	
	/**
	 * @param S1
	 * @param S2
	 * @return
	 */
	public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
		//Create two new nodes S1n, and S2n such that S1n is connected to all nodes in S1 and all nodes in S2 are connected to S2n
		//Call Dijsktras on S1n and S2n
		Node S1n = new Node (-1,-1);
		Node S2n = new Node (-2,-2);
		LinkedList<Node> L1 = this.toNodeList(S1);
		LinkedList<Node> L2 = this.toNodeList(S2);
		
		for(Node n: L1){
			S1n.addAdjacent(n, 0);
		}
		
		for(Node n: L2){
			n.addAdjacent(S2n, 0);
		}
		
		PriorityQueue<Node> minheap = new PriorityQueue<Node>(new NodeComparator());
		resetNodes(-1,-1);
		
		//Add nodes to PQ
		for(Node n : this.nodes){
			minheap.add(n);
			n.inQ = true;
		}	
		minheap.add(S2n);
		S2n.inQ = true;
		minheap.add(S1n);
		S1n.setDST(0);
		S1n.inQ = true;
		
		//perform Dijkstras
		Node dst = Dijkstras(minheap, S2n.x, S2n.y);
		//Remove S2n from adjacency list of nodes
		for(Node n: L2){
			n.removeAdjacent();
		}


		if(dst!= null){
			return returnPath(dst);
		}
		else{
			return null;
		}
	}
	
	private Node SetDijkstras(PriorityQueue<Node> minheap, HashSet<Node> set){
		Node curMin = null;
		//Update once decrease key is implemented
		while(!minheap.isEmpty()&&(curMin = minheap.poll()).inQ){
			curMin.inQ = false;
			//Is curMin the destination?
			if(set.contains(curMin)){
				return curMin;
			}
			
			Iterator<Edge> i = curMin.edges.iterator();
			while(i.hasNext()){
				Edge curE = i.next();
				if(curE.dst.inQ){
					if(curE.dst.distanceToSource< curMin.distanceToSource+curE.weight){
						curE.dst.distanceToSource = curMin.distanceToSource+curE.weight;
						curE.dst.parent = curMin;
						//decrease key in PQ need to do
						minheap.add(curE.dst);
					}
				}
			}
			
		}
		
		return null;
	}
	
	private HashSet<Node> toNodeSet(ArrayList<Integer> S){
		Iterator<Integer> i = S.iterator();
		HashSet<Node> set = new HashSet<Node>();
		
		while(i.hasNext()){
			Integer x = i.next();
			Integer y = i.next();
			set.add(this.Graph[x.intValue()][y.intValue()]);
		}
		
		return set;
	}
	
	private LinkedList<Node> toNodeList(ArrayList<Integer> S){
		Iterator<Integer> i = S.iterator();
		LinkedList<Node> list = new LinkedList<Node>();
		
		while(i.hasNext()){
			Integer x = i.next();
			Integer y = i.next();
			list.add(this.Graph[x.intValue()][y.intValue()]);
		}
		
		return list;
	}
	
	private Node Dijkstras(PriorityQueue<Node> minheap, int x, int y){
		Node curMin = null;
		//Update once decrease key is implemented
		while(!minheap.isEmpty()&&(curMin = minheap.poll()).inQ){
			curMin.inQ = false;
			//Is curMin the destination?
			if(curMin.x == x && curMin.y ==y){
				return curMin;
			}
			
			Iterator<Edge> i = curMin.edges.iterator();
			while(i.hasNext()){
				Edge curE = i.next();
				if(curE.dst.inQ){
					if(curE.dst.distanceToSource< curMin.distanceToSource+curE.weight){
						curE.dst.distanceToSource = curMin.distanceToSource+curE.weight;
						curE.dst.parent = curMin;
						//decrease key in PQ need to do
						minheap.add(curE.dst);
					}
				}
			}
			
		}
		
		return null;
	}
	
	private Node resetNodes(int x, int y){
		Node srcNode = null;
		for(int i = 0; i<this.nodes.length; i++){
			Node cur = this.nodes[i];
			if(cur.x == x && cur.y ==y){
				srcNode = cur;
				cur.setDST(0);
				cur.setParent(null);
			}
			else{
				cur.setDST(Integer.MAX_VALUE);
			}
		}
		return srcNode;
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

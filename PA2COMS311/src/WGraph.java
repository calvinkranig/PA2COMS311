

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;





/**
 * @author ckranig, ans66
 *
 */
public class WGraph {
	

	private Node[] nodes;
	private HashMap<Coord, Node> GraphMap;

	public WGraph(String FName) {
		parseFile(FName);
	}
	
	public HashMap<Coord, Node> M(){
		return GraphMap;
	}
	
	public Node[] nodes(){
		return nodes;
	}

	private void parseFile(String fName) {
		// may need to swich to " "
		String delims = "[ ]+";
		try {
			BufferedReader in = new BufferedReader(new FileReader(fName));
			// Get number of nodes
			String nextLine = in.readLine();
			int nodenumber = Integer.parseInt(nextLine);
			nodes = new Node[nodenumber];
			GraphMap = new HashMap<Coord,Node>(nodenumber);

			// Get number of edges
			nextLine = in.readLine();
			//int edges = Character.getNumericValue(nextLine.charAt(0));
			int index = 0;
			while ((nextLine = in.readLine()) != null) {
				String[] tokens = nextLine.split(delims);
				Integer sx = Integer.parseInt(tokens[0]);
				Integer sy = Integer.parseInt(tokens[1]);
				Integer dx = Integer.parseInt(tokens[2]);
				Integer dy = Integer.parseInt(tokens[3]);
				Integer weight = Integer.parseInt(tokens[4]);

				Coord scoord = new Coord(sx, sy);
				Coord dcoord = new Coord(dx, dy);

				// What is faster put if absent or checking for key?
				if (!GraphMap.containsKey(dcoord)) {
					Node dst = new Node(dx, dy);
					GraphMap.put(dcoord, dst);
					nodes[index] = dst;
					index++;
				}
				if (!GraphMap.containsKey(scoord)) {
					Node src = new Node(sx, sy);
					GraphMap.put(scoord, src);
					nodes[index] = src;
					index++;
				}
				GraphMap.get(scoord).addAdjacent(GraphMap.get(dcoord), weight);
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Incorrectly Formatted File");
			e.printStackTrace();
		}

	}

	/**
	 * pre: ux, uy, vx, vy are valid coordinates of vertices u and v in the
	 * graph
	 * 
	 * post: return arraylist contains even number of integers, for any even i,
	 * i-th and i+1-th integers in the array represent the x-coordinate and
	 * y-coordinate of the i-th vertex in the returned path (path is an ordered
	 * sequence of vertices)
	 */
	public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy) {
		PriorityQ minheap = makeHeap(ux, uy);
		// perform Dijkstras
		Node dst = Dijkstras(minheap, vx, vy);
		if (dst != null) {
			return returnPath(dst);
		} else {
			return new ArrayList<Integer>();
		}
	}

	/**
	 * pre: ux, uy are valid coordinates of vertex u from the graph The S
	 * arraylist contains even number of intergers for any even i, i-th and
	 * i+1-th integers in the array represent the x-coordinate and y-coordinate
	 * of the i-th vertex in the set S.
	 * 
	 * post: return arraylist contains even number of integers, for any even i,
	 * i-th and i+1-th integers in the array represent the x-coordinate and
	 * y-coordinate of the i-th vertex in the returned path (path is an ordered
	 * sequence of vertices)
	 */
	public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S) {
		// Do Dijsktra BFS and stop when first node in Set S is pulled from the
		// PQ
		HashSet<Node> set = toNodeSet(S);
		PriorityQ minheap = makeHeap(ux, uy);
		// perform Dijkstras
		Node dst = SetDijkstras(minheap, set);
		if (dst != null) {
			return returnPath(dst);
		} else {
			return new ArrayList<Integer>();
		}
	}

	/**
	 * pre: S1 and S2 represent sets of vertices (see above for
	 * the representation of a set of vertices as arrayList)
	 * post: same structure as the last method's post.
	 */
	public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2) {
		// Create two new nodes S1n, and S2n such that S1n is connected to all
		// nodes in S1 and all nodes in S2 are connected to S2n
		// Call Dijsktras on S1n and S2n
		LinkedList<Node> L1 = this.toNodeList(S1);
		LinkedList<Node> L2 = this.toNodeList(S2);

		Node S1n = new Node(-1, -1);
		Node S2n = new Node(-2, -2);
		for (Node n : L1) {
			S1n.addAdjacent(n, 0);
		}

		for (Node n : L2) {
			n.addAdjacent(S2n, 0);
		}

		PriorityQ minheap = makeHeap(-1, -1);
		minheap.add(S2n);
		S2n.setInQ(true);

		S1n.setDstToSrc(0);
		minheap.add(S1n);
		S1n.setInQ(true);;
		S1n.setDiscovered(true);
		
		// perform Dijkstras
		Node dst = Dijkstras(minheap, S2n.x(), S2n.y());
		// Remove S2n from adjacency list of nodes
		for (Node n : L2) {
			n.removeLast();
		}

		if (dst != null) {
			ArrayList<Integer> returnList = returnPath(dst.parent());
			//remove added endnode
			returnList.remove(0);
			returnList.remove(0);
			return returnList;
		} else {
			return new ArrayList<Integer>();
		}
	}

	private Node SetDijkstras(PriorityQ minheap, HashSet<Node> set) {
		// Dealing with entrys now
		Node curMin = null;
		// Update once decrease key is implemented
		while (!minheap.isEmpty()&&(curMin = minheap.extractMin()).discovered()) {
			
			// Is curMin the destination?
			if (set.contains(curMin)) {
				return curMin;
			}

			Iterator<Edge> i = curMin.edges().iterator();
			while (i.hasNext()) {
				Edge curE = i.next();
				if (curE.dst().inQ()) {
					if (curE.dst().dstToSrc() > curMin.dstToSrc() + curE.weight()) {
						curE.dst().setDstToSrc(curMin.dstToSrc() + curE.weight());
						curE.dst().setParent(curMin);
						curE.dst().setDiscovered(true);
						// decrease key in PQ need to do
						minheap.decrementPriority(curE.dst().position(), 0);
					}
				}
			}

		}

		return null;
	}

	private HashSet<Node> toNodeSet(ArrayList<Integer> S) {
		Iterator<Integer> i = S.iterator();
		HashSet<Node> set = new HashSet<Node>();

		while (i.hasNext()) {
			Integer x = i.next();
			Integer y = i.next();
			set.add(this.GraphMap.get(new Coord(x, y)));
		}

		return set;
	}

	private LinkedList<Node> toNodeList(ArrayList<Integer> S) {
		Iterator<Integer> i = S.iterator();
		LinkedList<Node> list = new LinkedList<Node>();

		while (i.hasNext()) {
			Integer x = i.next();
			Integer y = i.next();
			list.add(this.GraphMap.get(new Coord(x, y)));
		}

		return list;
	}

	private Node Dijkstras(PriorityQ minheap, int x, int y) {
		// Dealing with entrys now
		Node curMin = null;
		// Update once decrease key is implemented
		while (!minheap.isEmpty()&& (curMin = minheap.extractMin()).discovered()) {
			
			curMin.setInQ(false);
			// Is curMin the destination?
			if (curMin.x() == x && curMin.y() == y) {
				return curMin;
			}

			Iterator<Edge> i = curMin.edges().iterator();
			while (i.hasNext()) {
				Edge curE = i.next();
				if (curE.dst().inQ()) {
					if (curE.dst().dstToSrc() > curMin.dstToSrc() + curE.weight()) {
						curE.dst().setDstToSrc(curMin.dstToSrc() + curE.weight());
						curE.dst().setParent(curMin);
						curE.dst().setDiscovered(true);
						// decrease key in PQ need to do
						minheap.decrementPriority(curE.dst().position(), 0);
					}
				}
			}

		}

		return null;
	}

	/**
	 * This method takes a src node and creates a priorityQ
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private PriorityQ makeHeap(int x, int y) {
		PriorityQ newQ = new PriorityQ(this.nodes.length);

		for (int i = 0; i < this.nodes.length; i++) {
			Node cur = this.nodes[i];
			if (cur.x() == x && cur.y() == y) {
				cur.setDstToSrc(0);
				newQ.add(cur);
				cur.setParent(null);
				cur.setInQ(true);
				cur.setDiscovered(true);
			} else {
				cur.setDstToSrc(Integer.MAX_VALUE);
				newQ.add(cur);
				cur.setInQ(true);
				cur.setDiscovered(false);
			}
		}
		return newQ;
	}

	private ArrayList<Integer> returnPath(Node end) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		Node cur = end;
		while (cur != null) {
			path.add(cur.y());
			path.add(cur.x());
			cur = cur.parent();
		}
		return reversePath(path);
	}
	
	private ArrayList<Integer> reversePath(ArrayList<Integer> path){
		ArrayList<Integer> reversePath = new ArrayList<Integer>();
		for(int i = path.size()-1; i>=0; i--){
			int it = path.get(i);
			reversePath.add(path.get(i));
		}
		return reversePath;
	}

}
package pa2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/*
/**
 * @author ckranig, ans66
 *
 */
public class WGraph {
	/*
	 * private class NodeComparator implements Comparator<Node>{
	 * 
	 * @Override public int compare(Node n1, Node n2) { return
	 * n1.distanceToSource-n2.distanceToSource; } }
	 */
	private class Edge {
		private Node dst;
		private int weight;

		public Edge(Node dst, int weight) {
			this.dst = dst;
			this.weight = weight;
		}

		public Node dst() {
			return dst;
		}

		public int weight() {
			return weight;
		}
	}

	private class Coord {
		private final int x;
		private final int y;

		public Coord(int x, int y) {

			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return (x << 16) + y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj.getClass() != this.getClass()) {
				return false;
			} else {
				return (((Coord) obj).x == this.x && ((Coord) obj).y == this.y);

			}
		}
	}

	private class Node {
		private final Coord cordinate;
		private Node parent;
		private LinkedList<Edge> edges;
		private boolean inQ;
		private int position;
		private int dstToSrc;

		public Node(int x, int y) {
			cordinate = new Coord(x, y);
			edges = new LinkedList<Edge>();
			parent = null;
			inQ = false;
			position = -1;
			dstToSrc = Integer.MAX_VALUE;
		}

		public LinkedList<Edge> edges() {
			return edges;
		}

		public int x() {
			return cordinate.x;
		}

		public int y() {
			return cordinate.y;
		}

		public int position() {
			return this.position;
		}

		public int dstToSrc() {
			return this.dstToSrc;
		}

		public Node parent() {
			return this.parent;
		}

		public void setParent(Node n) {
			parent = n;
		}

		public void setDstToSrc(int n) {
			this.dstToSrc = n;
		}

		public void setPosition(int n) {
			this.position = n;
		}

		public void addAdjacent(Node n, int weight) {
			edges.add(new Edge(n, weight));
		}

		/**
		 * Removes latest added node from list
		 */
		public void removeAdjacent() {
			edges.removeLast();
		}

	}

	private Node[] nodes;
	private HashMap<Coord, Node> GraphMap;

	public WGraph(String fName) {
		parseFile(fName);
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
			return null;
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
		PriorityQ minheap = makeHeap(ux, uy);
		HashSet<Node> set = toNodeSet(S);

		// perform Dijkstras
		Node dst = SetDijkstras(minheap, set);
		if (dst != null) {
			return returnPath(dst);
		} else {
			return null;
		}
	}

	/**
	 * @param S1
	 * @param S2
	 * @return
	 */
	public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2) {
		// Create two new nodes S1n, and S2n such that S1n is connected to all
		// nodes in S1 and all nodes in S2 are connected to S2n
		// Call Dijsktras on S1n and S2n
		Node S1n = new Node(-1, -1);
		Node S2n = new Node(-2, -2);
		LinkedList<Node> L1 = this.toNodeList(S1);
		LinkedList<Node> L2 = this.toNodeList(S2);

		for (Node n : L1) {
			S1n.addAdjacent(n, 0);
		}

		for (Node n : L2) {
			n.addAdjacent(S2n, 0);
		}

		PriorityQ minheap = makeHeap(-1, -1);
		minheap.add(S2n);
		S2n.inQ = true;

		S1n.setDstToSrc(0);
		minheap.add(S1n);
		S1n.inQ = true;

		// perform Dijkstras
		Node dst = Dijkstras(minheap, S2n.x(), S2n.y());
		// Remove S2n from adjacency list of nodes
		for (Node n : L2) {
			n.removeAdjacent();
		}

		if (dst != null) {
			ArrayList<Integer> returnList = returnPath(dst.parent());
			returnList.remove(returnList.size()-1);
			returnList.remove(returnList.size()-1);
			return returnList;
		} else {
			return null;
		}
	}

	private Node SetDijkstras(PriorityQ minheap, HashSet<Node> set) {
		// Dealing with entrys now
		Node curMin = null;
		// Update once decrease key is implemented
		while (!minheap.isEmpty()) {
			curMin = minheap.extractMin();
			// Is curMin the destination?
			if (set.contains(curMin)) {
				return curMin;
			}

			Iterator<Edge> i = curMin.edges.iterator();
			while (i.hasNext()) {
				Edge curE = i.next();
				if (curE.dst.inQ) {
					if (curE.dst.dstToSrc() > curMin.dstToSrc() + curE.weight) {
						curE.dst.setDstToSrc(curMin.dstToSrc() + curE.weight);
						curE.dst.setParent(curMin);
						// decrease key in PQ need to do
						minheap.decrementPriority(curE.dst.position(), 0);
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
		while (!minheap.isEmpty()) {
			curMin = minheap.extractMin();
			curMin.inQ = false;
			// Is curMin the destination?
			if (curMin.x() == x && curMin.y() == y) {
				return curMin;
			}

			Iterator<Edge> i = curMin.edges.iterator();
			while (i.hasNext()) {
				Edge curE = i.next();
				if (curE.dst.inQ) {
					if (curE.dst.dstToSrc() > curMin.dstToSrc() + curE.weight) {
						curE.dst.setDstToSrc(curMin.dstToSrc() + curE.weight);
						curE.dst.setParent(curMin);
						// decrease key in PQ need to do
						minheap.decrementPriority(curE.dst.position(), 0);
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
				cur.inQ = true;
			} else {
				cur.setDstToSrc(Integer.MAX_VALUE);
				newQ.add(cur);
				cur.inQ = true;
			}
		}
		return newQ;
	}

	private ArrayList<Integer> returnPath(Node end) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		Node cur = end;
		while (cur != null) {
			path.add(cur.x());
			path.add(cur.y());
			cur = cur.parent;
		}
		return path;
	}

	private class PriorityQ {

		private ArrayList<Node> heapArray;
		private int heapSize;

		/**
		 * Creates an empty priority queue.
		 */
		public PriorityQ(int size) {
			heapArray = new ArrayList<Node>(size+1); // pretty sure the max # of
												// strings is 200
			heapArray.add(null);
			heapSize = 0;
		}

		/**
		 * Accessor for getting the size of the heap
		 * 
		 * @return size of the heap
		 */
		public int getSize() {
			return heapSize;
		}

		/**
		 * Adds a String s with priority p to the priority queue.
		 * 
		 * @param s
		 *            String to be added to the queue.
		 * @param p
		 *            Priority of the added String.
		 */
		public void add(Node n) {
			// encapsulate the object and priority into an Entry

			int position = ++heapSize; // start position at the end of the heap
			heapArray.add(position, n); // put the new entry at the end of the
										// heap

			while (position != 1 && n.dstToSrc() < heapArray.get(position / 2).dstToSrc()) { 
				heapArray.set(position, heapArray.get(position / 2)); 
				heapArray.get(position).setPosition(position);
				position /= 2; // the position moves to the parent
			}
			heapArray.set(position, n); // assign the new entry at the index of the final position
			heapArray.get(position).setPosition(position);
							
		}

		/**
		 * Returns a String whose priority is maximum.
		 * 
		 * @return String with max priority.
		 */
		public Node returnMin() {
			if (this.isEmpty()) {
				return null;
			}
			return heapArray.get(1); // highest priority is at the top of the
										// heap
		}
		/*
		 * public String returnMaxDocument(){ if(this.isEmpty()){ return null; }
		 * return heapArray.get(1).getDocument(); }
		 */

		/**
		 * Returns a String whose priority is maximum and removes it from the
		 * priority queue.
		 * 
		 * @return Maximum, extracted String.
		 */
		public Node extractMin() {
			if (this.isEmpty()) {
				return null;
			}
			Node max = this.returnMin(); // save string for the return
			swap(1, this.getSize()); // swap the first and last elements in the
										// heap
			heapSize--; // decrement heap

			this.bubbledown(1); // heapify top element of heap

			return max; // return old max
		}

		/**
		 * Removes the element from the priority queue whose array index is i.
		 * 
		 * @param i
		 *            Index of element in array to be removed.
		 */
		public void remove(int i) {

			swap(i, this.getSize()); // swap removed element with last element
			heapSize--; // decrement heap
			this.bubbledown(i); // heapify swapped element
		}

		/**
		 * Decrements the priority of the ith element by k.
		 * 
		 * @param i
		 *            Element whose priority is to be decremented.
		 * @param k
		 *            Amount of decrease for the ith element.
		 */
		public void decrementPriority(int i, int k) {
			// String s = heapArray.get(i).getAddress(); // copy address of
			// entry to be decremented
			// int p = heapArray.get(i).getPriority() - k; // new priority

			// remove(i); // remove entry with old priority
			// add(s, p); // add same entry with updated priority

			heapArray.get(i).setDstToSrc(heapArray.get(i).dstToSrc() - k); // decrement
																			// priority
			bubbleUp(i); // heapify with new priority

		}

		/**
		 * Returns an array B with the following property: B[i] = key(A[i]) for
		 * all i in the array A used to implement the priority queue.
		 * 
		 * @return Priority Array of the priority queue.
		 */
		public int[] priorityArray() {

			int[] priArr = new int[heapSize + 1]; // create array for the
													// priorities
			for (int i = 1; i <= heapSize; ++i) { // for all Entries in the
													// heapArray, enter their
													// priorities into the
													// priArr
				priArr[i] = heapArray.get(i).dstToSrc();
			}
			return priArr;
		}

		/**
		 * Returns value(A[i]), where A is the array used to represent the
		 * priority queue
		 * 
		 * @param i
		 *            index of Entry that holds the returned value
		 * @return value of specified Entry
		 */
		public Node getValue(int i) {
			return heapArray.get(i);
		}

		/**
		 * Returns key(A[i]), where A is the array used to represent the
		 * priority queue
		 * 
		 * @param i
		 *            index of Entry that holds the returned key
		 * @return key of specified Entry
		 */
		public int getKey(int i) {
			return heapArray.get(i).dstToSrc();
		}

		/**
		 * Returns true if and only if the queue is empty
		 * 
		 * @return whether the queue is empty
		 */
		public boolean isEmpty() {
			return heapSize == 0;
		}

		/**
		 * Helper method to maintain heap properties after a remove.
		 * 
		 * @param i
		 *            Index at which to begin heapify.
		 */
		private void bubbledown(int i) {

			int smallest = i;
			int left = 2 * i;
			int right = 2 * i + 1;

			if ((left <= heapSize) && (heapArray.get(smallest).dstToSrc() > heapArray.get(left).dstToSrc())) {
				smallest = left;
			}

			if ((right <= heapSize) && (heapArray.get(smallest).dstToSrc() > heapArray.get(right).dstToSrc())) {
				smallest = right;
			}

			if (smallest != i) {
				swap(i, smallest);
				bubbledown(smallest);
			}
		}
		
		private void bubbleUp(int i){
			int position = i;
		
			while (position > 1 && heapArray.get(position).dstToSrc() < heapArray.get(position / 2).dstToSrc()){
				int posit2 = position/2;
				swap(position, position/2);
				position = position/2;
			}
		}

		/**
		 * Helper method used for swapping entries
		 * 
		 * @param i
		 *            index of first Entry to swap
		 * @param j
		 *            index of second Entry to swap
		 */
		private void swap(int i, int j) {

			heapArray.get(j).position = i;
			heapArray.get(i).position = j;
			Node temp = heapArray.get(i);
			heapArray.set(i, heapArray.get(j));
			heapArray.set(j, temp);
		}

	}

}
import java.util.LinkedList;


public class Node {

	/*
	 * private class NodeComparator implements Comparator<Node>{
	 * 
	 * @Override public int compare(Node n1, Node n2) { return
	 * n1.distanceToSource-n2.distanceToSource; } }
	 */

	
		private final Coord cordinate;
		private Node parent;
		private LinkedList<Edge> edges;
		private boolean inQ;
		private boolean discovered;
		private int position;
		private int dstToSrc;

		public Node(int x, int y) {
			cordinate = new Coord(x, y);
			edges = new LinkedList<Edge>();
			parent = null;
			inQ = false;
			discovered = false;
			position = -1;
			dstToSrc = Integer.MAX_VALUE;
		}

		public LinkedList<Edge> edges() {
			return edges;
		}

		public int x() {
			return cordinate.x();
		}

		public int y() {
			return cordinate.y();
		}
		
		public Coord coord(){
			return this.cordinate;
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
		
		public boolean discovered(){
			return this.discovered;
		}
		
		public boolean inQ(){
			return this.inQ;
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
		
		public void setDiscovered(boolean b){
			this.discovered = b;
		}

		public void addAdjacent(Node n, int weight) {
			edges.add(new Edge(n, weight));
		}
		
		public void setInQ(boolean b){
			this.inQ = b;
		}

		/**
		 * Removes latest added node from list
		 */
		public void removeLast() {
			edges.removeLast();
		}

	}


public class Edge {
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
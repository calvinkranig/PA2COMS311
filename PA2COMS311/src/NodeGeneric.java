import java.util.LinkedList;


public class NodeGeneric<T> {
		
		private NodeGeneric<T> parent;	
		private T object;
		private boolean inQ;
		private boolean discovered;
		private int position;
		private int weight;
		private int dstToSrc;

		public NodeGeneric(T o) {
			this.object = o;
			parent = null;
			inQ = false;
			discovered = false;
			position = -1;
			dstToSrc = Integer.MAX_VALUE;
			this.weight = 0;
		}


		public int position() {
			return this.position;
		}

		public int dstToSrc() {
			return this.dstToSrc;
		}

		public NodeGeneric parent() {
			return this.parent;
		}
		
		public boolean discovered(){
			return this.discovered;
		}
		
		public T object(){
			return this.object;
		}
		
		public int weight(){
			return weight;
		}

		public void setParent(NodeGeneric n) {
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
		
		public void setWeight(int n){
			this.weight = n;
		}
		
		

	}
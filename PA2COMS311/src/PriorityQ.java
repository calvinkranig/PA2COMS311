import java.util.ArrayList;

public class PriorityQ {

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

			heapArray.get(j).setPosition(i);
			heapArray.get(i).setPosition(j);
			Node temp = heapArray.get(i);
			heapArray.set(i, heapArray.get(j));
			heapArray.set(j, temp);
		}

	}
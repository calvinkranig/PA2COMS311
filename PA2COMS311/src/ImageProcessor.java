


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;



/**
 * @author ckranig, ans66
 *
 */
public class ImageProcessor {
	
	public class Pixel extends Node{
		private final int r;
		private final int g;
		private final int b;
		private int importance;
		public Pixel(final int r,final int g, final int b, int x, int y){
			super(x,y);
			this.r=r;
			this.g=g;
			this.b = b;
			importance = 0;
		}
		public int r() {
			return r;
		}

		public int g() {
			return g;
		}

		public int b() {
			return b;
		}
		
		public int importance(){
			return this.importance;
		}
		
		public void setImporatance(int i){
			this.importance = i;
		}

		
	}
	//First category is height 2nd is width
	ArrayList<Pixel>[] M;// imageGraph
	final int H;
	int W;
	boolean importanceUpdated;
	
	public ImageProcessor(String FName){
		this.H = parseFile(FName);	
		importanceUpdated = false;
	}
	
	private int parseFile(String FName){
		// may need to swich to " "
				String delims = "[ ]+";
				try {
					BufferedReader in = new BufferedReader(new FileReader(FName));
					// Get Height
					String nextLine = in.readLine();
					int height = Integer.parseInt(nextLine);
					// Get Width
					nextLine=in.readLine();
					W = Integer.parseInt(nextLine);
					//Create imagegraph
					M = new ArrayList[height];
					for(int y = 0; y<height; y++) {
						M[y] = new ArrayList<Pixel>(W);
						nextLine = in.readLine();
						String[] tokens = nextLine.split(delims);
						for(int x= 0; x< tokens.length; x+=3){
							//Get Pixels
							Integer r = Integer.parseInt(tokens[x]);
							Integer g = Integer.parseInt(tokens[x+1]);
							Integer b = Integer.parseInt(tokens[x+2]);
							Pixel np = new Pixel(r,g,b,x,y);
							//add new pixel to map
							M[y].add(np);	
						}

						
					}
					
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return height;
				} catch (Exception e) {
					System.out.println("Incorrectly Formatted File");
					e.printStackTrace();
				}
				return -1;
	}
	
private static int PDist(Pixel p, Pixel q) {
		int red = (p.r - q.r) * (p.r - q.r);
		int green = (p.g - q.g) * (p.g - q.g);
		int blue = (p.b - q.b) * (p.b - q.b);
		
		return red + green + blue;
	}
	
private int getImportancePixel(int x, int y) {
		
		int YImportance = 0;
		int XImportance = 0;
		if(x == 0){
			PDist(M[y].get(W-1),M[y].get(1));
		}
		else if(x == W-1){
			PDist(M[y].get(x-1),M[y].get(0));
		}
		else{
			PDist(M[y].get(x-1),M[y].get(x+1));
		}
		
		if(y == 0){
			PDist(M[H-1].get(x), M[1].get(x));
		}
		else if(y == H-1){
			PDist(M[y-1].get(x),M[0].get(x));
		}
		else{
			PDist(M[y-1].get(x),M[y+1].get(x));
		}
		
		return XImportance + YImportance;
	}


	/**
	 * Compute Importance matrix: The matrix I capturing the importance values for each
	 * element in M
	 * pre:
	 * post: returns the 2-D matrix I as per its definition
	 */
	public ArrayList<ArrayList<Integer>> getImportance(){
		ArrayList<ArrayList<Integer>> importanceMatrix = new ArrayList<ArrayList<Integer>>(H);
		//Update needed importance
		if(!this.importanceUpdated){
			for(int y = 0; y<H; y++){
				ArrayList<Integer> row = new ArrayList<Integer>(W);
				importanceMatrix.add(row);
				for(int x = 0; x < W; x++){
					int importance = this.getImportancePixel(x, y);
					M[y].get(x).setImporatance(importance);
					importanceMatrix.get(y).add(importance);
				}
			}
			this.importanceUpdated = true;
		}
		else{
			for(int y = 0; y<H; y++){
				ArrayList<Integer> row = new ArrayList<Integer>(W);
				importanceMatrix.add(row);
				for(int x = 0; x < W; x++){
					importanceMatrix.get(y).add(M[y].get(x).importance());
				}
		}
		}
		return importanceMatrix;
	}
	
	
	/**
	 * Compute the reduced image (reduction in width by k) and write the result in a file specified with FName
	 * pre: W-k > 1
	 * post: Compute the new image matrix after reducing the width by k
	 * Follow the method for reduction described above
	 * Write the result in a file named FName
	 * in the same format as the input image matrix
	 * @param k: width to reduce by
	 * @param FName: File to write too
	 */
	public void writeReduced(int k, String FName) {
		int remaining = k;
		while(this.W >1 && remaining!=0){
		//Update needed importance
		if(!this.importanceUpdated){
			for(int y = 0; y<H; y++){
				for(int x = 0; x < W; x++){
					Pixel p = M[y].get(x);
					p.setImporatance(this.getImportancePixel(x, y));
					//Only important for this
					p.coord().setX(x);
				}
			}
			this.importanceUpdated = true;
		}
		//Get Shortest Path
		//Remove Nodes on Path
		removeNodes(S2SDijkstras());
		k--;
		}
		//check if there is one column left
		if(k!=0){
			//If we need to remove last column delete graph and write nothing to file
			this.M = new ArrayList[0];
		}
		writeGraphToFile(FName);	
	}
	
	private void writeGraphToFile(String FName){
		
	}
	
	private void removeNodes(Stack<Pixel> removeList){
		while(!removeList.isEmpty()){
			Pixel cur = removeList.pop();
			this.M[cur.y()].remove(cur.x());
		}		
		this.W--;
	}
	
	private Stack<Pixel> S2SDijkstras(){

		PriorityQ minheap = makeHeap();	
		// perform Dijkstras
		Pixel dst = Dijkstras(minheap);

		if (dst != null) {
			return returnPath(dst);
		} else {
			return null;
		}
	}
	
	private PriorityQ makeHeap(){
		PriorityQ newQ = new PriorityQ(W*H);
		//Add first Row
		for(int x = 0 ;x < W; x++){
			Pixel cur = M[0].get(x);
			cur.setDiscovered(true);
			cur.setDstToSrc(cur.importance());
			newQ.add(cur);
			cur.setParent(null);
			cur.setInQ(true);
		}
		//Add Remaining Rows
		for(int y = 1; y<H; y++){
			for(int x = 0; x < W; x++){
				Pixel cur = M[y].get(x);
				cur.setDiscovered(false);
				cur.setDstToSrc(Integer.MAX_VALUE);
				newQ.add(cur);
				cur.setInQ(true);
			}
		}
		return newQ;
	}
	
	private Pixel Dijkstras(PriorityQ minheap){
		// Dealing with entrys now
		Pixel curMin = null;
		// Update once decrease key is implemented
		while (!minheap.isEmpty()&& (curMin = (Pixel)minheap.extractMin()).discovered()) {
			curMin.setInQ(false);
			//Is curMin Destination?
			if(curMin.y()==this.H-1){
				return curMin;
			}
			//Update Edges
			updateEdges(curMin, minheap);
		}
		return null;
	}
	
	/**
	 * Does not check for 1 pixel wide image
	 * @param parent
	 * @param minheap
	 */
	private void updateEdges(Pixel parent, PriorityQ minheap){
		int y = parent.y();
		int x = parent.x();
		if(y!=H-1){
			if(x==0){
				//Get two children
				for(int i= 0; i <=1 ; i++){
					Pixel curChild =M[y+1].get(x+i);
					if(curChild.inQ() && curChild.dstToSrc()>parent.dstToSrc()+curChild.importance()){
						updateChild(parent,curChild,minheap);
					}
				}
			}
			else if(x == W-1){
				//Get two children
				for(int i= 0; i >=-1 ; i--){
					Pixel curChild =M[y+1].get(x+i);
					if(curChild.inQ() && curChild.dstToSrc()>parent.dstToSrc()+curChild.importance()){
						updateChild(parent,curChild,minheap);
					}
				}
			}
			else{
				//Get three children
				for(int i= -1; i <=1 ; i++){
					Pixel curChild =M[y+1].get(x+i);
					if(curChild.inQ() && curChild.dstToSrc()>parent.dstToSrc()+curChild.importance()){
						updateChild(parent,curChild,minheap);
					}
				}
			}		
		}
	}
	
	private void updateChild(Pixel parent, Pixel child, PriorityQ minheap){
		child.setDstToSrc(parent.dstToSrc()+child.importance());
		child.setParent(parent);
		child.setDiscovered(true);
		minheap.decrementPriority(child.position(), 0);
	}
	
	
	
	private Stack<Pixel>returnPath(Pixel p){
		Stack<Pixel> list = new Stack<Pixel>();
		Pixel cur = p;
		list.add(cur);
		while (cur.parent() != null)
		{
			list.add((Pixel)cur.parent());
			cur = (Pixel)cur.parent();
		}
		return list;
	}
	

}

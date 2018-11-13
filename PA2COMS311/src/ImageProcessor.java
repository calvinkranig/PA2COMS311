


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		
		public String tostring(){
			return "" + r + " " + g + " " + b;
		}
		
	}
	//First category is height 2nd is width
	private ArrayList<Pixel>[] M;// imageGraph
	private final int H;
	private int W;
	private boolean importanceUpdated;
	
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
							Pixel np = new Pixel(r,g,b,x/3,y);
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
	
private int getImportancePixel(int x, int y, ArrayList<Pixel>[] map) {
		
		int YImportance = 0;
		int XImportance = 0;
		
		int width = map[0].size();
	
		if(x == 0){
			XImportance =PDist(map[y].get(width-1),map[y].get(1));
		}
		else if(x == width-1){
			XImportance = PDist(map[y].get(x-1),map[y].get(0));
		}
		else{
			XImportance = PDist(map[y].get(x-1),map[y].get(x+1));
		}
		//Don't need to worry about height since it will be the same on reduced graph
		if(y == 0){
			YImportance = PDist(map[H-1].get(x), map[1].get(x));
		}
		else if(y == H-1){
			YImportance = PDist(map[y-1].get(x),map[0].get(x));
		}
		else{
			YImportance = PDist(map[y-1].get(x),map[y+1].get(x));
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
					int importance = this.getImportancePixel(x, y, M);
					M[y].get(x).setImporatance(importance);
					importanceMatrix.get(y).set(x, importance);
				}
			}
			this.importanceUpdated = true;
		}
		else{
			for(int y = 0; y<H; y++){
				ArrayList<Integer> row = new ArrayList<Integer>(W);
				importanceMatrix.add(row);
				for(int x = 0; x < W; x++){
					importanceMatrix.get(y).set(x,M[y].get(x).importance());
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
		if(H ==0){
			return;
		}
		if(k > 1){
			this.importanceUpdated = false;
		}
		int remaining = k;
		ArrayList<Pixel>[] reduced = new ArrayList[H];
		//Copy Image
		for(int y = 0; y<H; y++){
			reduced[y] = new ArrayList<Pixel>(W);
			for(int x = 0; x < W; x++){
				reduced[y].set(x, M[y].get(x));
			}
		}
		
		while(reduced[0].size() >1 && remaining>0){
		//Update needed importance
		for(int y = 0; y<reduced.length; y++){
			for(int x = 0; x < reduced[0].size(); x++){
				Pixel p = reduced[y].get(x);
				p.setImporatance(this.getImportancePixel(x, y,reduced));
				//Only important for this
				p.coord().setX(x);
			}
		}
			
		//Get Shortest Path
		//Remove Nodes on Path
		removeNodes(S2SDijkstras());
		remaining--;
		}
		
		//check if there is one column left
		if(remaining>0){
			//If we need to remove last column delete graph and write nothing to file
			reduced = new ArrayList[0];
		}
		writeGraphToFile(FName,reduced);	
	}
	
	private void writeGraphToFile(String FName, ArrayList<Pixel>[] reduced){
		FileWriter fw;
		try {
			String line = System.getProperty("line.seperator");
			fw = new FileWriter(FName, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			for(int y = 0; y< reduced.length; y++){
				for(int x = 0; x<reduced[y].size(); x++){
					String pixel = reduced[y].get(x).tostring() + " ";
					out.print(pixel);
				}
				out.println("");
			}

			out.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeNodes(Stack<Pixel> removeList){
		while(!removeList.isEmpty()){
			Pixel cur = removeList.pop();
			this.M[cur.y()].remove(cur.x());
		}		
		this.W = W-1;
		this.importanceUpdated = false;
	}
	
	private Stack<Pixel> S2SDijkstras(){

		PriorityQ minheap = makeHeap();	
		Pixel dst = new Pixel(-1,-1,-1,-1,-1);
		dst.setDiscovered(false);
		dst.setDstToSrc(Integer.MAX_VALUE);
		minheap.add(dst);
		dst.setInQ(true);
		
		// perform Dijkstras
		Pixel last = Dijkstras(minheap, dst);

		if (last != null) {
			return returnPath((Pixel)last.parent());
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
	
	private Pixel Dijkstras(PriorityQ minheap, Pixel dst){
		// Dealing with entrys now
		Pixel curMin = null;
		// Update once decrease key is implemented
		while (!minheap.isEmpty()&& (curMin = (Pixel)minheap.extractMin()).discovered()) {
			curMin.setInQ(false);
			//Is curMin Destination?
			if(curMin.y()==-1){
				return curMin;
			}
			//Update Edges
			updateEdges(curMin, minheap, dst);
		}
		return null;
	}
	
	/**
	 * Does not check for 1 pixel wide image
	 * @param parent
	 * @param minheap
	 */
	private void updateEdges(Pixel parent, PriorityQ minheap, Pixel dst){
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
		else{
			updateChild(parent,dst,minheap);
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

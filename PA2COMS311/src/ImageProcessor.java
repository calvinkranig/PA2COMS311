


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
	Stack<Pixel> nodesToUpdate;
	final int H;
	int W;
	
	public ImageProcessor(String FName){
		this.H = parseFile(FName);	
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
							//add new pixel to be updated
							this.nodesToUpdate.push(np);		
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
	
private int getImportancePixel(Pixel p) {
		
		int YImportance = 0;
		int XImportance = 0;
		int x = p.x();
		int y = p.y();
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
		//Update needed importance
		while(!this.nodesToUpdate.isEmpty()){
			updatePixelImportance(this.nodesToUpdate.pop());
		}
		//Convert map into needed 2-D matrix
		ArrayList<ArrayList<Integer>> importanceMatrix = new ArrayList<ArrayList<Integer>>(H);
		for(int y = 0; y <H; y++){
			ArrayList<Integer> row = new ArrayList<Integer>(W);
			importanceMatrix.add(row);
			for(int x = 0; x <W; x++){
				importanceMatrix.get(y).add(this.M[y].get(x).importance());
			}
		}
		return importanceMatrix;
	}
	
	private void updatePixelImportance(Pixel p){
		p.setImporatance(this.getImportancePixel(p));
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
		//Update needed importance
		while(!this.nodesToUpdate.isEmpty()){
			updatePixelImportance(this.nodesToUpdate.pop());
		}
		//Get Shortest Path
		//Remove Nodes on Path
		removeNodes(Dijkstras());
		writeGraphToFile(FName);
	}
	
	private void writeGraphToFile(String FName){
		
	}
	
	private void removeNodes(LinkedList<Pixel> removeList){
		
	}
	
	private LinkedList<Pixel> Dijkstras(){
		
		return null;
	}
	
	private LinkedList<Pixel>returnPath(Pixel p){
		LinkedList<Pixel> list = new LinkedList<Pixel>();
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

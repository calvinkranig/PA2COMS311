


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;



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
	LinkedList<Pixel> nodesToUpdate;
	int H;
	int W;
	
	public ImageProcessor(String FName){
		parseFile(FName);
	}
	
	private void parseFile(String FName){
		// may need to swich to " "
				String delims = "[ ]+";
				try {
					BufferedReader in = new BufferedReader(new FileReader(FName));
					// Get Height
					String nextLine = in.readLine();
					H = Integer.parseInt(nextLine);
					// Get Width
					nextLine=in.readLine();
					W = Integer.parseInt(nextLine);
					//Create imagegraph
					M = new ArrayList[H];
					for(int y = 0; y<H; y++) {
						nextLine = in.readLine();
						String[] tokens = nextLine.split(delims);
						for(int x= 0; x< tokens.length; x+=3){
							//Get Pixels
							Integer r = Integer.parseInt(tokens[x]);
							Integer g = Integer.parseInt(tokens[x+1]);
							Integer b = Integer.parseInt(tokens[x+2]);
							M[y].add(new Pixel(r,g,b,x,y));
						}

						
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
	
private static int PDist(Pixel p, Pixel q) {
		int red = (p.r - q.r) * (p.r - q.r);
		int green = (p.g - q.g) * (p.g - q.g);
		int blue = (p.b - q.b) * (p.b - q.b);
		
		return red + green + blue;
	}
	
private int getImportancePixel(Pixel p) {
		
		int YImportance = 0;
		int XImportance = 0;
		
		switch(p.x()){
		
		}
		
		switch(p.y()){
		
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
		for(Pixel p : this.nodesToUpdate){
			updateNode(p);
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
	
	private void updateNode(Pixel p){
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
		
	}
	
	private void removeColumn(){
		this.getImportance();
	}
	
	private void Dijkstras(){
		
	}
	

}

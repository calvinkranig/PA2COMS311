


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
		final int r;
		final int g;
		final int b;
		public Pixel(final int r,final int g, final int b, int x, int y){
			super(x,y);
			this.r=r;
			this.g=g;
			this.b = b;
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

		
	}
	//First category is height 2nd is width
	LinkedList<Pixel>[] M;// imageGraph
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
					M = new LinkedList[H];
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
	
private int getImportanceSingle(int i, int j) {
		
		int YImportance = 0;
		int XImportance = 0;
		
		if (i == 0) {
			YImportance = PDist(M[H - 1][j].object(), M[i+1][j].object());
		}
		else if(i == H - 1) {
			YImportance = PDist(M[i - 1][j].object(), M[0][j].object());
		}
		else {
			YImportance = PDist(M[i-1][j].object(), M[i+1][j].object());
		}
		
		if(j == 0) {
			XImportance = PDist(M[i][W].object(), M[i][j+1].object());
		}
		else if(j == W - 1) {
			XImportance = PDist(M[i][j-1].object(), M[i][0].object());
		}
		else {
			XImportance = PDist(M[i][j-1].object(), M[i][j+1].object());
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
		

		return null;
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

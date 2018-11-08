


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * @author ckranig, ans66
 *
 */
public class ImageProcessor {
	
	public class Pixel{
		final int r;
		final int g;
		final int b;
		public Pixel(final int r,final int g, final int b){
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
	NodeGeneric<Pixel>[][] M; // imageGraph
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
					NodeGeneric<Pixel>[][] M;
					for(int i = 0; i<H; i++) {
						nextLine = in.readLine();
						String[] tokens = nextLine.split(delims);
						for(int j= 0; j< tokens.length; j+=3){
							//Get Pixels
							Integer r = Integer.parseInt(tokens[j]);
							Integer g = Integer.parseInt(tokens[j+1]);
							Integer b = Integer.parseInt(tokens[j+2]);
							this.M[i][j/3]= new NodeGeneric<Pixel>(new Pixel(r,g,b));
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
		
		ArrayList<ArrayList<Integer>> I = new ArrayList<ArrayList<Integer>>(H);
		//calculate edge cases here
		//Do First Row
		ArrayList<Integer> row = new ArrayList<Integer>(W);
		//M[0][0]
		Integer cur = PDist(M[H - 1][0].object(), M[1][0].object())+PDist(M[0][W-1].object(), M[0][1].object());
		M[0][0].setWeight(cur);
		row.add(cur);
		//M[0][j]
		for(int j=1; j<W-1;j++){
			cur = PDist(M[H - 1][j].object(), M[1][j].object())+PDist(M[0][j-1].object(), M[0][j+1].object());
			M[0][j].setWeight(cur);
			row.add(cur);
		}
		//M[0][W-1]
		cur = PDist(M[H - 1][W-1].object(), M[1][W-1].object())+PDist(M[0][W-2].object(), M[0][0].object());
		M[0][W-1].setWeight(cur);
		row.add(cur);
		//Add row
		I.add(row);
		
		for(int i = 1; i < H-1; ++i) {
			row = new ArrayList<Integer>(W);
			//Do First Column
			//M[i][0]
			cur = PDist(M[i - 1][0].object(), M[i+1][0].object())+PDist(M[i][W-1].object(), M[i][1].object());
			M[i][0].setWeight(cur);
			row.add(cur);
			
			for(int j = 1; j < W-1; ++j) {
				cur = PDist(M[i - 1][j].object(), M[i+1][j].object())+PDist(M[i][j-1].object(), M[i][j+1].object());
				M[i][j].setWeight(cur);
				row.add(cur);
			}
			//Do Last Column M[i][W-1]
			cur = PDist(M[i-1][W-1].object(), M[i+1][W-1].object())+PDist(M[i][W-2].object(), M[i][0].object());
			M[i][W-1].setWeight(cur);
			row.add(cur);
			//Add row
			I.add(row);
		}
		//DO Last Row
		row = new ArrayList<Integer>(W);
		//M[H-1][0]
		cur = PDist(M[H - 2][0].object(), M[0][0].object())+PDist(M[H-1][W-1].object(), M[H-1][1].object());
		M[H-1][0].setWeight(cur);
		row.add(cur);
		//M[H-1][j]
		for(int j=1; j<W-1;j++){
			cur = PDist(M[H - 2][j].object(), M[0][j].object())+PDist(M[H-1][j-1].object(), M[H-1][j+1].object());
			M[H-1][j].setWeight(cur);
			row.add(cur);
		}
		//M[H-1][W-1]
		cur = PDist(M[H - 2][W-1].object(), M[0][W-1].object())+PDist(M[H-1][W-2].object(), M[H-1][0].object());
		M[H-1][W-1].setWeight(cur);
		row.add(cur);
		//Add rowy
		I.add(row);
		return I;
		
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

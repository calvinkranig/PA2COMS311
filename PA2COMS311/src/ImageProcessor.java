


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
	
	protected class Pixel{
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
	Pixel[][] M; // imageGraph
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
					M = new Pixel[H][W];
					for(int i = 0; i<M.length; i++) {
						nextLine = in.readLine();
						String[] tokens = nextLine.split(delims);
						for(int j= 0; j< tokens.length; j+=3){
							//Get Pixels
							Integer r = Integer.parseInt(tokens[j]);
							Integer g = Integer.parseInt(tokens[j+1]);
							Integer b = Integer.parseInt(tokens[j+2]);
							this.M[i][j/3]=new Pixel(r,g,b);
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
			YImportance = PDist(M[H - 1][j], M[i+1][j]);
		}
		else if(i == H - 1) {
			YImportance = PDist(M[i - 1][j], M[0][j]);
		}
		else {
			YImportance = PDist(M[i-1][j], M[i+1][j]);
		}
		
		if(j == 0) {
			XImportance = PDist(M[i][W], M[i][j+1]);
		}
		else if(j == W - 1) {
			XImportance = PDist(M[i][j-1], M[i][0]);
		}
		else {
			XImportance = PDist(M[i][j-1], M[i][j+1]);
		}
		
		return XImportance + YImportance;
	}



	public ArrayList<ArrayList<Integer>> getImportance(){
		
		ArrayList<ArrayList<Integer>> I = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < H; ++i) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			for(int j = 0; j < W; ++j) {
				row.add(j, getImportanceSingle(i, j));
			}
			I.add(row);
		}
		return I;
		
	}
	
	public void writeReduced(int k, String FName) {
		
	}
	

}

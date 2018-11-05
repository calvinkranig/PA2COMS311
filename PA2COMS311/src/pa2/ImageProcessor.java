package pa2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;



public class ImageProcessor {
	
	private class Pixel{
		int r;
		int g;
		int b;
		public Pixel(int r, int g, int b){
			this.r=r;
			this.g=g;
			this.b = b;
		}
		public int r() {
			return r;
		}
		public void setR(int r) {
			this.r = r;
		}
		public int g() {
			return g;
		}
		public void setG(int g) {
			this.g = g;
		}
		public int b() {
			return b;
		}
		public void setB(int b) {
			this.b = b;
		}
		
	}
	//First category is height 2nd is width
	Pixel[][] imageGraph;
	
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
					int height = Integer.parseInt(nextLine);
					// Get Width
					nextLine=in.readLine();
					int width = Integer.parseInt(nextLine);
					//Create imagegraph
					imageGraph = new Pixel[height][width];
					for(int i = 0; i<imageGraph.length; i++) {
						nextLine = in.readLine();
						String[] tokens = nextLine.split(delims);
						for(int j= 0; j< tokens.length; j+=3){
							//Get Pixels
							Integer r = Integer.parseInt(tokens[j]);
							Integer g = Integer.parseInt(tokens[j+1]);
							Integer b = Integer.parseInt(tokens[j+2]);
							this.imageGraph[i][j/3]=new Pixel(r,g,b);
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
}

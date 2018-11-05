package pa2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ImageProcessor {
	
	private int height;
	private int weight;
	private Pixel[][] M;
	
	public ImageProcessor(String FName) throws FileNotFoundException {
		File f = new File(FName);
		Scanner s = new Scanner(f);
		
		height = s.nextInt();
		weight = s.nextInt();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < weight; j++) {
				M[i][j] = new Pixel(s.nextInt(), s.nextInt(), s.nextInt());
			}
		}
	}
	
	public ArrayList<ArrayList<Integer>> getImportance(){
		return null;
		
	}
	
	public void writeReduced(int k, String FName) {
		
	}
	
	private class Pixel{
		private int red;
		private int green;
		private int blue;
		
		private Pixel(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
	}
}



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		/*// TODO Auto-generated method stub
		String fname = "SCCinput.txt";
		WGraph graph = new WGraph(fname);
		ArrayList<Integer> path1 = graph.V2V(1, 6, 2, 10);
		System.out.println(path1.toString());
		ArrayList<Integer> path2 = graph.V2V(3, 1, 3, 10);
		System.out.println(path2);
		System.out.println(graph.V2S(1, 1, path2));
		System.out.println(graph.S2S(path1, path2));
		
		fname = "image1.txt";
		String output = "output.txt";
		ImageProcessor ip = new ImageProcessor(fname);
		System.out.println(ip.getImportance());
		ip.writeReduced(1, output);
		System.out.println(ip.getImportance());
		ip.writeReduced(1, "output2.txt");
		
		
		ImageProcessor img = new ImageProcessor("mario.txt");
		System.out.println(img.getImportance());
		img.writeReduced(2, "marioReduced.txt");
		
		*/
		/*
		ImageProcessor img2 = new ImageProcessor("extratest1.txt");
		for(int x = 1; x <10 ; x++){
			ArrayList<ArrayList<Integer>> importance = img2.getImportance();
			for(ArrayList<Integer> list : importance){
				for(Integer i: list){
					System.out.print(i + "  ");
				}
				System.out.print("\n");
			}
			System.out.print("\n");
			String output = "output" + x + ".txt";
			img2.writeReduced(1, output);
			img2 = new ImageProcessor(output);
		}
		*/
		WGraph graph2 = new WGraph("extragraphtest1.txt");
		System.out.println(graph2.V2V(1, 2, 5, 6));
		ArrayList<Integer> l1 = new ArrayList(Arrays.asList(4,4,5,6,3,4));
		System.out.println(graph2.V2S(1, 2, l1));
		ArrayList<Integer> l2 = new ArrayList(Arrays.asList(1,2,2,2));
		ArrayList<Integer> l3 = new ArrayList(Arrays.asList(4,4,5,6));
		System.out.println(graph2.S2S(l1, l2));
	}

}

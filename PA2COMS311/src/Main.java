

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname = "graphinput1100.txt";
		WGraph graph = new WGraph(fname);
		ArrayList<Integer> path1 = graph.V2V(1, 1, 500, 1);
		System.out.println(path1.toString());
		ArrayList<Integer> path2 = graph.V2V(550, 1, 999, 1);
		System.out.println(path2);
		System.out.println(graph.V2S(500, 1, path2));
		System.out.println(graph.S2S(path1, path2));
		
		fname = "image1.txt";
		String output = "output.txt";
		ImageProcessor ip = new ImageProcessor(fname);
		System.out.print(ip.getImportance());
		ip.writeReduced(1, output);
	}

}

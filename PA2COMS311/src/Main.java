

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
	}

}

package pa2;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname = "graphinput.txt";
		WGraph graph = new WGraph(fname);
		ArrayList<Integer> path1 = graph.V2V(1,1,1,9);
		System.out.println(path1.toString());
		ArrayList<Integer> path2 = graph.V2V(1,12,1,19);
		System.out.println(path2.toString());
		System.out.println((graph.V2S(1, 20, path2)).toString());
		System.out.println(graph.S2S(path1, path2));
	}

}

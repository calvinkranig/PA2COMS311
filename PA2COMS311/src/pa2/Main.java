package pa2;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname = "unconnected.txt";
		WGraph graph = new WGraph(fname);
		ArrayList<Integer> path1 = graph.V2V(1,1,1,10);
		System.out.println(path1.toString());
		ArrayList<Integer> path2 = graph.V2V(3,1,3,10);
		System.out.println(path2.toString());
		System.out.println((graph.V2S(3,1, path2)).toString());
		System.out.println(graph.S2S(path2, path2));
	}

}

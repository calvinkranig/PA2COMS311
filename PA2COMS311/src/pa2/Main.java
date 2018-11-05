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
		ArrayList<Integer> path3 = new ArrayList<Integer>();
		path3.add(2);
		path3.add(444);
		System.out.println((graph.V2S(2,1, path2)).toString());
		System.out.println(graph.S2S(path1, path2));
	}

}

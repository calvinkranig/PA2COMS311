package pa2;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname = "graphinput.txt";
		WGraph graph = new WGraph(fname);
		ArrayList<Integer> path = graph.V2V(5, 6, 7, 8);
		System.out.print(path.toString());
	}

}

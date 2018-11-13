

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
		
		
		//Mario stuff
		String str = "";
		File f = new File("mario.png");
		Picture p = new Picture(f);
		String line = System.getProperty("line.separator");
		str += p.height() + line + p.width() + line;
		
		for(int i = 0; i < p.height(); ++i) {
			for(int j = 0; j < p.width(); ++j) {
				str += p.get(j, i).getRed() + " " + p.get(j, i).getGreen() + " " + p.get(j, i).getBlue() + " ";
			}
			str += line;
		}
		System.out.println(str);
		PrintWriter mario;
		try {
			mario = new PrintWriter("mario2.txt", "utf-8");
			mario.print(str);
			mario.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ImageProcessor img = new ImageProcessor("mario.txt");
		System.out.println(img.getImportance());
		img.writeReduced(2, "marioReduced.txt");
		
		Picture p2 = new Picture (33,33);
		str = "";
		str += p2.height() + line + p2.width() + line;
		
		for(int i = 0; i < p2.height(); ++i) {
			for(int j = 0; j < p2.width(); ++j) {
				str += p2.get(j, i).getRed() + " " + p2.get(j, i).getGreen() + " " + p2.get(j, i).getBlue() + " ";
			}
			str += line;
		}
		System.out.println(str);
		PrintWriter p2writer;
		try {
			p2writer = new PrintWriter("output.txt", "utf-8");
			p2writer.print(str);
			p2writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ImageProcessor img2 = new ImageProcessor("output.txt");
		System.out.println(img.getImportance());
		img.writeReduced(30, "marioReduced.txt");
	}

}

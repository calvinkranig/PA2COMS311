import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.*;

class ImageTest {

	@Test
	void test() throws FileNotFoundException, UnsupportedEncodingException {
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
		PrintWriter mario = new PrintWriter("mario.txt", "utf-8");
		mario.print(str);
		mario.close();
		
		ImageProcessor img = new ImageProcessor("mario.txt");
		
	}

}


package plugins;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Negative {

	public String main(String nameFile) {
		
		File file = new File(nameFile);
		BufferedImage bufferedImage = new BufferedImage(75, 50, BufferedImage.TYPE_INT_ARGB);
		try {
			bufferedImage = ImageIO.read(file);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgba = bufferedImage.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                bufferedImage.setRGB(x, y, col.getRGB());
            }
        }		
		File outputfile = new File("bufor.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return outputfile.getAbsolutePath();
		
	}
}

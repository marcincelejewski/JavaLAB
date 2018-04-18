package plugins;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blur {

	public String main(String nameFile) {

		File file = new File(nameFile);
		BufferedImage bufferedImage = new BufferedImage(75, 50, BufferedImage.TYPE_INT_ARGB);
		try {
			bufferedImage = ImageIO.read(file);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		int size = 15;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];

		for (int i = 0; i < data.length; i++) {
			data[i] = weight;
		}

		Kernel kernel = new Kernel(size, size, data);
		BufferedImageOp op = new ConvolveOp(kernel);
		bufferedImage = op.filter(bufferedImage, null);

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

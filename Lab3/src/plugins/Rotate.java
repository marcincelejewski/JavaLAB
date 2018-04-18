package plugins;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rotate {

	public String main(String nameFile) {
		
		File file = new File(nameFile);
		BufferedImage bufferedImage = new BufferedImage(75, 50, BufferedImage.TYPE_INT_ARGB);
		try {
			bufferedImage = ImageIO.read(file);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.PI, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import plugins.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class LoadIcons extends SwingWorker<String, Object> {

	@Override
	public String doInBackground() {
		IconExplorer.panel.removeAll();
		File[] listOfFiles = IconExplorer.folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile() && file.getName().endsWith(".jpg")) {

				System.out.println(file.getName());

				JLabel label = new JLabel();

				String plugin;
				if (IconExplorer.item_negative.isSelected())
					plugin = "plugins.Negative";
				else if (IconExplorer.item_rotate.isSelected())
					plugin = "plugins.Rotate";
				else if (IconExplorer.item_blur.isSelected())
					plugin = "plugins.Blur";
				else {
					plugin = "";
				}

				if (IconExplorer.map.containsKey(file.getName() + plugin) == true
						&& IconExplorer.map.get(file.getName() + plugin).get() != null) {
					label.setIcon((ImageIcon) IconExplorer.map.get(file.getName() + plugin).get());

				} else {
					BufferedImage bufferedImage = new BufferedImage(75, 50, BufferedImage.TYPE_INT_ARGB);

					try {
						if (plugin != "") {
							Load(plugin, file.getAbsolutePath());
							bufferedImage = ImageIO.read(new File("bufor.jpg"));
						} else {
							bufferedImage = ImageIO.read(file);
						}
					} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
							| IllegalArgumentException | InvocationTargetException | IOException e) {
						e.printStackTrace();
					}

					Image image = bufferedImage;
					image = image.getScaledInstance(75, 50, java.awt.Image.SCALE_SMOOTH); // scale it the // smooth
																							// way
					ImageIcon imageIcon = new ImageIcon(image);
					label.setIcon(imageIcon);
					WeakReference<ImageIcon> wr = new WeakReference<ImageIcon>(imageIcon);
					if (IconExplorer.map.containsKey(file.getName() + plugin) == true
							&& IconExplorer.map.get((file.getName() + plugin)).get() == null) {

						IconExplorer.map.remove(file.getName() + plugin);

						IconExplorer.map.put(file.getName() + plugin, wr);
					} else if (IconExplorer.map.containsKey(file.getName() + plugin) == false) {
						IconExplorer.map.put(file.getName() + plugin, wr);
					}

				}
				IconExplorer.panel.add(label);

			}
		}
		return null;
	}

	@Override
	protected void done() {
		IconExplorer.panel.revalidate();
		IconExplorer.panel.repaint();
	}

	public static String Load(String name, String namefile) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		
		MyClassLoader loader = MyClassLoader.Instance();
		Class<?> cl = loader.findClass(name);
		Object ob = cl.newInstance();

		Method md = cl.getMethod("main", new Class[] { String.class });

		return (String) md.invoke(ob, (Object) namefile);

	}
}
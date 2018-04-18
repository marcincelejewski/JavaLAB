import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import plugins.MyClassLoader;

import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class IconExplorer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999928982644627002L;
	private JPanel contentPane;
	public static JPanel panel;
	public static File folder;
	@SuppressWarnings("rawtypes")
	public static Map<String, WeakReference> map = new HashMap<String, WeakReference>();
	public static JMenuItem item_rotate;
	public static JMenuItem item_noEffects;
	public static JMenuItem item_negative;
	public static JMenuItem item_blur;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IconExplorer frame = new IconExplorer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IconExplorer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		item_noEffects = new JMenuItem("Brak efekt\u00F3w");
		item_noEffects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				item_noEffects.setSelected(true);
				item_rotate.setSelected(false);
				item_negative.setSelected(false);
				item_blur.setSelected(false);
			}
		});
		item_noEffects.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(item_noEffects);
		
		item_rotate = new JMenuItem("Obr\u00F3t");
		item_rotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item_noEffects.setSelected(false);
				item_rotate.setSelected(true);
				item_negative.setSelected(false);
				item_blur.setSelected(false);
			}
		});
		item_rotate.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(item_rotate);
		
		item_negative = new JMenuItem("Negatyw");
		item_negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item_noEffects.setSelected(false);
				item_rotate.setSelected(false);
				item_negative.setSelected(true);
				item_blur.setSelected(false);
			}
		});
		item_negative.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(item_negative);
		
		item_blur = new JMenuItem("Rozmycie");
		item_blur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item_noEffects.setSelected(false);
				item_rotate.setSelected(false);
				item_negative.setSelected(false);
				item_blur.setSelected(true);
			}
		});
		item_blur.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(item_blur);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 5));

		JTree tree = new JTree();
		tree.setModel(new FileSystemModel(new File("C:\\Users\\Marcin\\Pictures")));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				TreePath treePath = evt.getPath();
				if (treePath == null)
					return;
				else {
					String folderPath = createFilePath(treePath);
					folder = new File(folderPath);
					if (folder.isDirectory()) {
						new LoadIcons().execute();
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setSize(250, 332);
		scrollPane.setLocation(10, 10);

		contentPane.add(scrollPane);

		JScrollPane scrollPane2 = new JScrollPane(panel);
		scrollPane2.setSize(455, 390);
		scrollPane2.setLocation(270, 10);

		contentPane.add(scrollPane2);
		
		JButton btnUnload = new JButton("Wy\u0142aduj pluginy");
		btnUnload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyClassLoader.Unload();
				JOptionPane.showMessageDialog(null, "Wy³adowano pluginy");
			}
		});
		btnUnload.setBounds(10, 353, 250, 23);
		contentPane.add(btnUnload);

	}

	public static String createFilePath(TreePath treePath) {
		StringBuilder sb = new StringBuilder();
		Object[] nodes = treePath.getPath();
		sb.append(nodes[0].toString());
		for (int i = 1; i < nodes.length; i++) {
			sb.append(File.separatorChar).append(nodes[i].toString());
		}
		return sb.toString();
	}
}

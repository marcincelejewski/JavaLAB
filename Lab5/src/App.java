import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTable;

public class App extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8765376742106661141L;
	private ExaminedList list = new ExaminedList();
	private JPanel contentPane;
	private Connection connection = null;
	private JTextArea textURL;
	private JTable table;
	private PreparedStatement ps;
	private ResultSet rs;
	private JButton generate;
	private JButton save;
	private JButton load;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
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
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect("jdbc:sqlite:" + textURL.getText());
			}
		});
		btnConnect.setBounds(335, 11, 89, 23);
		contentPane.add(btnConnect);

		textURL = new JTextArea();
		textURL.setText("C:/Users/Marcin/bacteriaDB.db");
		textURL.setBounds(125, 15, 200, 16);
		contentPane.add(textURL);

		JLabel lblciekaDoBazy = new JLabel("\u015Acie\u017Cka do bazy");
		lblciekaDoBazy.setBounds(10, 15, 105, 14);
		contentPane.add(lblciekaDoBazy);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "GENOTYPE", "CLASS" }));
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(10, 74, 315, 268);
		contentPane.add(scroll);

		load = new JButton("Load examined");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showExamined();
			}
		});
		load.setBounds(10, 40, 315, 23);
		contentPane.add(load);

		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				serializationXML();
			}
		});
		save.setBounds(335, 40, 89, 23);
		contentPane.add(save);

		generate = new JButton("Generate");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generate();
			}
		});
		generate.setBounds(335, 77, 89, 23);
		contentPane.add(generate);
		
		save.setEnabled(false);
		load.setEnabled(false);
		generate.setEnabled(false);
	}

	protected boolean connect(String url) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
			
			load.setEnabled(true);
			generate.setEnabled(true);
			
			return true;

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public void showExamined() {

		try {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			list.getList().clear();
			model.setRowCount(0);
			Examined e;
			String query = "SELECT * FROM EXAMINED;";
			int g;
			String s;

			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				g = rs.getInt("GENOTYPE");
				s = rs.getString("CLASS");
				e = new Examined(g, s);
				list.add(e);
				model.addRow(new Object[] { g, s });
			}

			table.setModel(model);
			save.setEnabled(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean serializationXML() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ExaminedList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(list, new File("examined.xml"));
			return true;
		} catch (JAXBException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	protected void generate() {
		Random rand = new Random();
		int alpha = rand.nextInt(90) + 10;
		int beta = rand.nextInt(90) + 10;
		int gamma = rand.nextInt(90) + 10;

		int  x, y;
		double distance, min=Double.MAX_VALUE;
		String n, r, number=null, rank=null, genotype;
		
		genotype=Integer.toString(alpha)+Integer.toString(beta)+Integer.toString(gamma);
		
		String query = "SELECT ALPHA, BETA, NUMBER FROM FLAGELLA;";

		
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				
				x=rs.getInt("ALPHA");
				y=rs.getInt("BETA");	
				n=rs.getString("NUMBER");
				distance=distance(alpha, beta, x, y);
				if(distance<min) {
					number=n;
					min=distance;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		min=Double.MAX_VALUE;
		query = "SELECT BETA, GAMMA, RANK FROM TOUGHNESS;";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {				
				x=rs.getInt("BETA");
				y=rs.getInt("GAMMA");
				r=rs.getString("RANK");
				distance=distance(beta, gamma, x, y);
				if(distance<min) {
					rank=r;
					min=distance;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "SELECT GENOTYPE FROM EXAMINED WHERE GENOTYPE=?;";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, genotype);
			rs = ps.executeQuery();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			if (rs.next()) {				
				PreparedStatement ps2;
				ps2 = connection.prepareStatement("UPDATE EXAMINED SET CLASS = ? WHERE GENOTYPE = ?;");				
				ps2.setString(1, number+rank);
				ps2.setString(2, genotype);
				ps2.executeUpdate();
				list.update(Integer.parseInt(genotype), number+rank);
				model.setRowCount(0);
				for(Examined e : list.getList()) {
					model.addRow(new Object[] { e.getGenotype(), e.getClas() });
				}
				
			}
			else {
				PreparedStatement ps2;
				ps2 = connection.prepareStatement("INSERT INTO EXAMINED(GENOTYPE,CLASS) VALUES (?,?);");
				ps2.setString(1, genotype);
				ps2.setString(2, number+rank);
				ps2.executeUpdate();
				list.add(new Examined(Integer.parseInt(genotype),number+rank));
				model.addRow(new Object[] { genotype, number+rank });

				
				
			}
			table.setModel(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			query="INSERT INTO FLAGELLA(ALPHA,BETA,NUMBER) VALUES (?,?,?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, alpha);
			ps.setInt(2, beta);
			ps.setString(3, number);
			ps.executeUpdate();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			query="INSERT INTO TOUGHNESS(BETA,GAMMA,RANK) VALUES (?,?,?);";
			ps = connection.prepareStatement(query);
			ps.setInt(1, beta);
			ps.setInt(2, gamma);
			ps.setString(3, rank);
			ps.executeUpdate();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
	public double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x1,2.0)+Math.pow(y2-y1,2.0));
	}
}

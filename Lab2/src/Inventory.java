import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ChoiceFormat;
import java.text.NumberFormat;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Inventory extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6952759662137163251L;
	private JPanel contentPane;
	private List<Item> items = new ArrayList<Item>();
	private boolean add=true;
	private AddItem addItem;
	private JTable table;
	private JLabel lblPhoto;
	private DefaultTableModel model;
	private int index;
	private Locale locale;
	private float currencyConverter;
	private JMenu mnNewMenu;
	private JMenuItem mntmJzykPolski;
	private JMenuItem mntmJzykAngielski;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnRead;
	private JButton btnWrite;
	private String[] l;
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory frame = new Inventory();
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
	public Inventory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 743, 413);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu();
		menuBar.add(mnNewMenu);
		
		mntmJzykAngielski = new JMenuItem();
		mntmJzykAngielski.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				locale=new Locale("en","GB");
				currencyConverter = (float) 0.2;
				Reload(0);
				ReloadLabel();
				mntmJzykPolski.setEnabled(true);
				mntmJzykAngielski.setEnabled(false);
				
			}
		});
		mnNewMenu.add(mntmJzykAngielski);
		
		mntmJzykPolski = new JMenuItem();
		mntmJzykPolski.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				locale=new Locale("pl","PL");
				currencyConverter = 1;
				Reload(0);
				ReloadLabel();
				mntmJzykPolski.setEnabled(false);
				mntmJzykAngielski.setEnabled(true);
			}
		});
		mnNewMenu.add(mntmJzykPolski);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnAdd = new JButton();
		btnAdd.setBounds(465, 15, 119, 25);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item item;				
				if(add||!addItem.isVisible())
				{
					addItem  = new AddItem(l);
					add=false;
				}
				else
				{
					item=addItem.faddItem();
					if(item!=null)
					{
						int from=items.size();
						items.add(item);
						index=items.size()-1;
						Object[] data=new Object[4];
						data[0]=item.getName();
						data[1]=item.getQuantity();
						data[2]=item.getUnit();
						data[3]=item.getUnitPrice();
						
						ImageIcon img = new ImageIcon(item.getImagePath());
						lblPhoto.setIcon(img);
						
						model.addRow(data);
						addItem.setVisible(false);
						addItem.dispose();
						add=true;
						Reload(from);
					}
				}
				
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnAdd);
		
		btnRemove = new JButton();
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(index>=0&&items.size()>0)
				{
					items.remove(index);
					model.removeRow(index);
					lblPhoto.setIcon(null);
				}
			}
		});
		btnRemove.setBounds(598, 15, 119, 25);
		contentPane.add(btnRemove);
		
		btnRead = new JButton("Wczytaj");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DesarializationXML();
			}
		});
		btnRead.setBounds(465, 51, 119, 25);
		contentPane.add(btnRead);
		
		btnWrite = new JButton("Zapisz");
		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SerializationXML();
			}
		});
		btnWrite.setBounds(598, 51, 119, 25);
		contentPane.add(btnWrite);
		
		JScrollPane scrollPane = new JScrollPane(table = new JTable());
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index=table.getSelectedRow();
				ImageIcon img = new ImageIcon(items.get(index).getImagePath());
				lblPhoto.setIcon(img);				
			}
		});
		table.setModel(model=new DefaultTableModel(new Object[][] {},new String[] {"", "", "", ""}));
		
		scrollPane.setBounds(10, 13, 445, 332);
		contentPane.add(scrollPane);		
		scrollPane.setViewportView(table);
		
		lblPhoto = new JLabel("");
		lblPhoto.setBounds(465, 87, 256, 256);
		contentPane.add(lblPhoto);
		mntmJzykPolski.setEnabled(false);
		
		index=-1;
		currencyConverter = 1;
		locale = new Locale("pl","PL");

		ReloadLabel();
	        
	}
	public void SerializationXML()
	{
		try {
			XMLEncoder x=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("inventory.xml")));
			x.writeObject(items);
			x.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void DesarializationXML()
	{
		try {
			XMLDecoder x=new XMLDecoder(new BufferedInputStream(new FileInputStream("inventory.xml")));
			@SuppressWarnings("unchecked")
			ArrayList<Item> itemsLocal  = (ArrayList<Item>) x.readObject();
			x.close();
			int from=items.size();
			items.addAll(itemsLocal);
			Object[] data=new Object[4];
			for(Item item : itemsLocal)
			{
				data[0]=item.getName();
				data[1]=item.getQuantity();
				data[2]=item.getUnit();				
				data[3]=item.getUnitPrice();
				model.addRow(data);
			}
			if(items.size()>0)
			{
				ImageIcon img = new ImageIcon(items.get(0).getImagePath());
				lblPhoto.setIcon(img);
			}
			Reload(from);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void Reload(int from)
	{
		ResourceBundle bundle = ResourceBundle.getBundle("LabelsBundle",locale);
		double[] fileLimits = {1,2,5};
		String s;
		
		String[] fileItem = {bundle.getString("item1"),bundle.getString("item2"),bundle.getString("item5")};
		String[] fileKg = {bundle.getString("kilogram1"),bundle.getString("kilogram2"),bundle.getString("kilogram5")};
		String[] fileLiter = {bundle.getString("liter1"),bundle.getString("liter2"),bundle.getString("liter5")};
		
		ChoiceFormat choiceItem = new ChoiceFormat(fileLimits,fileItem);
		ChoiceFormat choiceKg = new ChoiceFormat(fileLimits,fileKg);
		ChoiceFormat choiceLiter = new ChoiceFormat(fileLimits,fileLiter);
		
		NumberFormat format=NumberFormat.getCurrencyInstance(locale);
		for(int i=from;i<table.getRowCount();i++)
		{
			model.setValueAt(format.format(items.get(i).getUnitPrice()*currencyConverter), i, 3);
			s=items.get(i).getUnit();
			if(s.equals("sztuka")||s.equals("item"))
			{
				model.setValueAt(choiceItem.format(items.get(i).getQuantity()), i, 2);
			}
			else if(s.equals("kilogram")) 
			{
				model.setValueAt(choiceKg.format(items.get(i).getQuantity()), i, 2);
			}
			else if(s.equals("litr")||s.equals("liter"))
			{
				model.setValueAt(choiceLiter.format(items.get(i).getQuantity()), i, 2);
			}
			else {
				System.out.println("Nieobsluzono w choice format");
			}
		}
		
	}
	public void ReloadLabel()
	{
		ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", locale);		
		
		l=new String[8];
		l[0]=labels.getString("name");
		l[1]=labels.getString("quantity");
		l[2]=labels.getString("unit");
		l[3]=labels.getString("unitPrice");
		l[4]=labels.getString("addImage");
		l[5]=labels.getString("info");
		l[6]=labels.getString("info2");
		l[7]=labels.getString("warning");
		
		
		mnNewMenu.setText(labels.getString("options"));	
		mntmJzykPolski.setText(labels.getString("polish"));
		mntmJzykAngielski.setText(labels.getString("english"));
		btnAdd.setText(labels.getString("add"));
		btnRemove.setText(labels.getString("remove"));
		btnRead.setText(labels.getString("read"));
		btnWrite.setText(labels.getString("write"));
		this.setTitle(labels.getString("aplication"));		
		model.setColumnIdentifiers(new String[] {l[0],l[1],l[2],l[3]});
	}
}

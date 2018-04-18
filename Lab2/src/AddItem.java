import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class AddItem extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textName;
	private JTextField textQuantity;
	private JTextField textUnit;
	private JTextField textUnitPrice;
	private String path;
	private String[] labels;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddItem dialog = new AddItem(args);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddItem(String[] l) {
		this.labels=l;
		setBounds(100, 100, 345, 248);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNazwa = new JLabel(labels[0]);
		lblNazwa.setBounds(12, 13, 105, 16);
		contentPanel.add(lblNazwa);
		
		JLabel lblIlo = new JLabel(labels[1]);
		lblIlo.setBounds(12, 42, 105, 16);
		contentPanel.add(lblIlo);
		
		JLabel lblJednostka = new JLabel(labels[2]);
		lblJednostka.setBounds(12, 71, 105, 16);
		contentPanel.add(lblJednostka);
		
		JLabel lblCenaJednostkowa = new JLabel(labels[3]);
		lblCenaJednostkowa.setBounds(12, 100, 105, 16);
		contentPanel.add(lblCenaJednostkowa);
		
		JButton btnAddImage = new JButton(labels[4]);
		btnAddImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(".\\photo"));
				chooser.setDialogTitle(labels[4]);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					path=file.getAbsolutePath();			
				}
			}
		});
		btnAddImage.setBounds(12, 129, 305, 25);
		contentPanel.add(btnAddImage);
		
		textName = new JTextField();
		textName.setBounds(129, 10, 188, 22);
		contentPanel.add(textName);
		textName.setColumns(10);
		
		textQuantity = new JTextField();
		textQuantity.setBounds(129, 39, 188, 22);
		contentPanel.add(textQuantity);
		textQuantity.setColumns(10);
		
		textUnit = new JTextField();
		textUnit.setBounds(129, 68, 188, 22);
		contentPanel.add(textUnit);
		textUnit.setColumns(10);
		
		textUnitPrice = new JTextField();
		textUnitPrice.setBounds(129, 97, 188, 22);
		contentPanel.add(textUnitPrice);
		textUnitPrice.setColumns(10);
		
		JLabel lblZatwierdDodanieProduktu = new JLabel(labels[5]);
		lblZatwierdDodanieProduktu.setHorizontalAlignment(SwingConstants.CENTER);
		lblZatwierdDodanieProduktu.setForeground(Color.RED);
		lblZatwierdDodanieProduktu.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblZatwierdDodanieProduktu.setBounds(12, 167, 305, 29);
		contentPanel.add(lblZatwierdDodanieProduktu);
	this.setVisible(true);
	}
	public Item faddItem()
	{
		String name=textName.getText();
		String quantity=textQuantity.getText();
		String unit=textUnit.getText();
		String unitPrice=textUnitPrice.getText();
		if(!name.isEmpty()&&!quantity.isEmpty()&&!unit.isEmpty()&&!unitPrice.isEmpty()&&!path.isEmpty())
		{
			Item item=new Item(name,path,Float.parseFloat(quantity),unit,Float.parseFloat(unitPrice));
			return item;
		}
		else
		{
			JOptionPane.showMessageDialog(null,labels[6],labels[7], JOptionPane.WARNING_MESSAGE);
			this.setVisible(false);
			this.dispose();
			return null;
		}
		
	} 
	
}

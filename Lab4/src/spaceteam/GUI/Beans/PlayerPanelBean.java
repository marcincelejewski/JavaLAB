package spaceteam.GUI.Beans;

import java.awt.EventQueue;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import spaceteam.Api.CaptainCommand;
import spaceteam.Client.ClientRemote;
import spaceteam.Server.GameEvent;
import spaceteam.Server.ServerRemote;

import javax.swing.JCheckBox;
import java.awt.Font;

public class PlayerPanelBean extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9042330466212552646L;
	private ServerRemote look_up;
	private ClientRemote client;
	private CaptainCommand<?> lastCaptainCommend;

	private JTextField txtPlayerName;
	private JTextArea txtAreaLog;

	String sliderName;
	JLabel lblSlider;
	JLabel lblSliderValue;
	JSlider slider;
	int sliderValue;

	String spinerName;
	JLabel lblSpinner;
	JSpinner spinner;

	String checkboxName;
	JCheckBox chckbxCheckBox;

	private int spinnerOldValue;
	private int sliderOldValue;
	JPanel panelTools;

	// Wlasciwosci proste
	private boolean isSpinerEnabled;
	private boolean isSliderEnabled;
	private boolean isCheckboxEnabled;

	// Wlasciwosci ograniczone
	private VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

	// Wlasciwosci zlozone
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new PlayerPanelBean());
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
	public PlayerPanelBean() {

		setBounds(100, 100, 435, 370);
		setLayout(null);

		panelTools = new JPanel();
		panelTools.setBounds(10, 247, 415, 110);
		add(panelTools);
		panelTools.setLayout(null);

		JLabel lblTools = new JLabel("Panel Kontrolny");
		lblTools.setBounds(10, 0, 104, 14);
		panelTools.add(lblTools);

		lblSpinner = new JLabel("Spinner label");
		lblSpinner.setBounds(10, 25, 94, 14);
		panelTools.add(lblSpinner);

		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int newValue = (Integer) spinner.getValue();

				changes.firePropertyChange("spinner", spinnerOldValue, newValue);
				spinnerOldValue = newValue;
			}
		});
		spinner.setModel(new SpinnerNumberModel(0, 0, 150, 1));
		spinner.setBounds(10, 50, 120, 20);
		panelTools.add(spinner);

		lblSlider = new JLabel("Slider Label");
		lblSlider.setBounds(140, 25, 66, 14);
		panelTools.add(lblSlider);

		lblSliderValue = new JLabel("0");
		lblSliderValue.setBounds(465, 25, 46, 14);
		panelTools.add(lblSliderValue);

		slider = new JSlider();
		slider.setFont(new Font("Tahoma", Font.PLAIN, 8));
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(10);
		slider.setValue(10);
		slider.setBounds(140, 50, 152, 49);
		panelTools.add(slider);
		slider.setMaximum(50);

		chckbxCheckBox = new JCheckBox("New check box");
		chckbxCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				changes.firePropertyChange("checkbox", !chckbxCheckBox.isSelected(), chckbxCheckBox.isSelected());
			}
		});
		chckbxCheckBox.setBounds(298, 49, 100, 23);
		panelTools.add(chckbxCheckBox);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int newValue = slider.getValue();

				try {
					setSliderValue(newValue);

					lblSliderValue.setText(Integer.toString(newValue));

					sliderOldValue = newValue;
				} catch (PropertyVetoException e) {
					slider.setValue(sliderOldValue);
				}
			}
		});
		sliderOldValue = slider.getValue();

		JPanel panelPlayer = new JPanel();
		panelPlayer.setBounds(10, 11, 415, 225);
		add(panelPlayer);
		panelPlayer.setLayout(null);

		JLabel lblName = new JLabel("Nick");
		lblName.setBounds(10, 11, 30, 14);
		panelPlayer.add(lblName);

		txtPlayerName = new JTextField();
		txtPlayerName.setEditable(false);
		txtPlayerName.setBounds(50, 8, 135, 20);
		panelPlayer.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JLabel lblLog = new JLabel("Komendy");
		lblLog.setBounds(10, 39, 120, 14);
		panelPlayer.add(lblLog);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 395, 155);
		panelPlayer.add(scrollPane);

		txtAreaLog = new JTextArea();
		txtAreaLog.setEditable(false);
		scrollPane.setViewportView(txtAreaLog);
		
		JLabel lblNewLabel = new JLabel("Spaceteam");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 32));
		lblNewLabel.setBounds(225, 11, 180, 42);
		panelPlayer.add(lblNewLabel);

		setSliderEnabled(false);
		setSpinerEnabled(false);
		setCheckboxEnabled(false);
	}

	public void appendLogMessage(String msg) {
		this.txtAreaLog.append(msg + '\n');
	}

	public void handleGameEvent(GameEvent event) {
		if (event == null)
			return;

		switch (event.getLogType()) {
		case EVENT_GAME_STARTED:
			appendLogMessage("Rozpoczêcie rozgrywki");
			break;
		case EVENT_GAME_ENDED:
			appendLogMessage("Koniec rozgrywki");
			break;
		case EVENT_CAPTAIN_SENDS_COMMAND:
			lastCaptainCommend = (CaptainCommand<?>) event.getEventData();
			appendLogMessage("Kapitan przesy³a rozkaz: " + lastCaptainCommend.getMessage());
			break;
		case EVENT_GAME_MEASURMENT_PROPERTY_CHANGED:
			appendLogMessage(event.getMessage());
			break;
		default:
			break;
		}

		this.repaint();
	}

	public CaptainCommand<?> getLastCaptainCommend() {
		return lastCaptainCommend;
	}

	// Proste
	public boolean getSpinerEnabled() {
		return isSpinerEnabled;
	}

	public void setSpinerEnabled(boolean enabled) {
		this.isSpinerEnabled = enabled;

		lblSpinner.setEnabled(enabled);
		spinner.setEnabled(enabled);
	}

	public boolean getSliderEnabled() {
		return this.isSliderEnabled;
	}

	public void setSliderEnabled(boolean enabled) {
		this.isSliderEnabled = enabled;

		lblSlider.setEnabled(enabled);
		lblSliderValue.setEnabled(enabled);
	}

	public boolean isCheckboxEnabled() {
		return this.isCheckboxEnabled;
	}

	public void setCheckboxEnabled(boolean isCheckboxEnabled) {
		this.isCheckboxEnabled = isCheckboxEnabled;
		this.chckbxCheckBox.setEnabled(isCheckboxEnabled);
	}

	// Wiazane
	public void addPropertyChangeListener(PropertyChangeListener p) {
		changes.addPropertyChangeListener(p);
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		changes.removePropertyChangeListener(p);
	}

	// Ograniczone
	public void addVetoableChangeListener(VetoableChangeListener v) {
		vetoes.addVetoableChangeListener(v);
	}

	public void removeVetoableChangeListener(VetoableChangeListener v) {
		vetoes.removeVetoableChangeListener(v);
	}

	public int getSliderValue() {
		return sliderValue;
	}

	public void setSliderValue(int sliderValue) throws PropertyVetoException {

		vetoes.fireVetoableChange("sliderValue", sliderOldValue, sliderValue);

		this.sliderValue = sliderValue;

		changes.firePropertyChange("sliderValue", sliderOldValue, sliderValue);

		sliderOldValue = sliderValue;
	}

	public ClientRemote getClient() {
		return client;
	}

	public void setClient(ClientRemote client) throws RemoteException {
		this.client = client;

		this.txtPlayerName.setText(this.getClient().getPlayer().getName());

		this.appendLogMessage("Czekaj na rozpoczêcie rozgrywki");

		repaint();
	}

	public ServerRemote getLook_up() {
		return look_up;
	}

	public void setLook_up(ServerRemote look_up) {
		this.look_up = look_up;
	}

	public String getSliderName() {
		return sliderName;
	}

	public void setSliderName(String sliderName) {
		this.sliderName = sliderName;
		this.lblSlider.setText(sliderName);
	}

	public String getSpinerName() {
		return spinerName;
	}

	public void setSpinerName(String spinerName) {
		this.spinerName = spinerName;
		this.lblSpinner.setText(spinerName);
	}

	public String getCheckboxName() {
		return checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
		this.chckbxCheckBox.setText(checkboxName);
	}
}

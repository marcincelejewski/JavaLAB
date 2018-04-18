package spaceteam.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import spaceteam.Api.CanHandleGameEvent;
import spaceteam.Api.CaptainCommand;
import spaceteam.Api.GameSession;
import spaceteam.Api.Player.Role;
import spaceteam.Api.SpaceshipMeasurements;
import spaceteam.Api.CptCommands.ComouflageCommand;
import spaceteam.Api.CptCommands.EnginePowerCommand;
import spaceteam.Api.CptCommands.ShieldStrengthCommand;
import spaceteam.Api.CptCommands.TurboCommand;
import spaceteam.Client.ClientRemote;
import spaceteam.Server.GameEvent;
import spaceteam.Server.ServerRemote;
import java.awt.Font;
import javax.swing.JTextArea;

public class CaptainPanel extends JFrame implements CanHandleGameEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054512887713011930L;
	ServerRemote look_up;
	ClientRemote client;

	private JPanel contentPane;
	private JTextField txtPlayersConnected;
	private JLabel lblSpaceShipStatus;
	private JTable tableSpaceship;
	private JButton btnStartGame;
	private JButton btnCommand;
	private JTextArea textArea;
	private int intScore;
	private String lastCommand;
	private boolean currTurbo = false;
	private boolean currComuflage = false;
	private JButton btnZakoczRozgrywk;

	HashSet<Role> playerRoles;
	private JTextField score;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaptainPanel frame = new CaptainPanel();
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
	public CaptainPanel() {
		setTitle("Panel Kapitana");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		intScore = 0;

		setBounds(100, 100, 535, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPlayersConnected = new JLabel("Ilo\u015B\u0107 graczy w pokoju");
		lblPlayersConnected.setBounds(10, 11, 157, 14);
		contentPane.add(lblPlayersConnected);

		txtPlayersConnected = new JTextField();
		txtPlayersConnected.setEditable(false);
		txtPlayersConnected.setBounds(177, 8, 20, 20);
		contentPane.add(txtPlayersConnected);
		txtPlayersConnected.setColumns(10);

		lblSpaceShipStatus = new JLabel("Status SSV Normandy");
		lblSpaceShipStatus.setBounds(10, 36, 160, 14);
		contentPane.add(lblSpaceShipStatus);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 500, 100);
		contentPane.add(scrollPane);

		tableSpaceship = new JTable();
		scrollPane.setViewportView(tableSpaceship);

		btnStartGame = new JButton("Rozpocznij Rozgrywk\u0119");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					GameEvent success = look_up.startGame();
					if (success == null) {
						return;
					}

					btnStartGame.setEnabled(false);
					btnCommand.setEnabled(true);
					btnZakoczRozgrywk.setEnabled(true);
					updateGui();
					updateTableSpaceship();

				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		btnStartGame.setBounds(10, 327, 187, 23);
		contentPane.add(btnStartGame);

		btnCommand = new JButton("Wy\u015Blij polecenie");
		btnCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateCommand();
			}
		});
		btnCommand.setEnabled(false);
		btnCommand.setBounds(10, 293, 499, 23);
		contentPane.add(btnCommand);

		btnZakoczRozgrywk = new JButton("Zako\u0144cz Rozgrywk\u0119");
		btnZakoczRozgrywk.setEnabled(false);
		btnZakoczRozgrywk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				appendLogMessage("Koniec Gry!\n Twoja dru¿yna zdoby³a " + Integer.toString(intScore) + " punktów");
				btnCommand.setEnabled(false);
				btnZakoczRozgrywk.setEnabled(false);
			}
		});
		btnZakoczRozgrywk.setBounds(322, 327, 187, 23);
		contentPane.add(btnZakoczRozgrywk);

		score = new JTextField();
		score.setText("0");
		score.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
		score.setEditable(false);
		score.setColumns(10);
		score.setBounds(436, 11, 73, 39);
		contentPane.add(score);

		JLabel lblScore = new JLabel("Score");
		lblScore.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
		lblScore.setBounds(318, 11, 108, 39);
		contentPane.add(lblScore);

		textArea = new JTextArea();

		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(10, 170, 500, 111);
		contentPane.add(scrollPane_1);
	}

	public CaptainPanel(ServerRemote look_up, ClientRemote client) throws RemoteException {
		this();

		this.look_up = look_up;
		this.client = client;

		updateGui();
	}

	@SuppressWarnings("unchecked")
	public void handleGameEvent(GameEvent event) throws RemoteException {
		if (event == null)
			return;

		switch (event.getLogType()) {
		case EVENT_GAME_STARTED:
			playerRoles = (HashSet<Role>) event.getEventData();
			break;

		case EVENT_GAME_ENDED:
			appendLogMessage("Koniec Gry!\n Twoja dru¿yna zdoby³a " + Integer.toString(intScore) + " punktów");
			break;

		case EVENT_CAPTAIN_SENDS_COMMAND:
			break;

		case EVENT_GAME_MEASURMENT_PROPERTY_CHANGED:
			try {
				updateGui();
				updateTableSpaceship();
				intScore++;
				score.setText(Integer.toString(intScore));
				appendLogMessage("Ustawiono " + lastCommand);
				btnCommand.setEnabled(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case EVENT_NEW_PLAYER_JOINED:
			int currentPlayersFromRemote = look_up.getGameSession().getPlayers().size();
			txtPlayersConnected.setText(Integer.toString(currentPlayersFromRemote));
			break;
		}
	}

	private void updateGui() throws RemoteException {
		GameSession session = look_up.getGameSession();
		int plyersConnected = session.getPlayers().keySet().size();
		txtPlayersConnected.setText(Integer.toString(plyersConnected));

		repaint();
	}

	private void updateTableSpaceship() throws RemoteException {
		SpaceshipMeasurements currentMeasurments = look_up.getGameSession().getMeasurements();

		String[] columnNames = { "Przyrz¹d", "Wartoœæ" };
		Object[][] data = { new String[] { "Moc Silników", Integer.toString(currentMeasurments.getShieldStrength()) },
				new String[] { "Turbo", Boolean.toString(currentMeasurments.isTurbo()) },
				new String[] { "Si³a Tarcz", Integer.toString(currentMeasurments.getEnginePower()) },
				new String[] { "Maskowanie", Boolean.toString(currentMeasurments.isTurbo()) } };
		DefaultTableModel model = new DefaultTableModel(data, columnNames);

		tableSpaceship.setModel(model);
	}

	public void appendLogMessage(String msg) {
		this.textArea.append(msg + '\n');
	}

	private void GenerateCommand() {

		Random rand = new Random();
		int a = 0;

		if (!playerRoles.contains(Role.Mechanik) && playerRoles.contains(Role.Obroñca)) {
			a = rand.nextInt(2);
		} else if (playerRoles.contains(Role.Mechanik) && !playerRoles.contains(Role.Obroñca)) {
			a = rand.nextInt(2) + 2;
		} else {
			a = rand.nextInt(4);
		}

		try {
			switch (a) {
			case 0: {
				int r = rand.nextInt(10);
				CaptainCommand<Integer> cmd = new ShieldStrengthCommand(r);
				lastCommand = "si³ê tarcz na wartoœæ " + Integer.toString(r);
				look_up.captainSendsCommend(cmd);
				break;
			}
			case 1: {
				currComuflage = !currComuflage;
				CaptainCommand<Boolean> cmd = new ComouflageCommand(currComuflage);
				look_up.captainSendsCommend(cmd);
				lastCommand = "Maskowanie jako " + Boolean.toString(currComuflage);
			}
				break;
			case 2: {
				int r = rand.nextInt(50);
				CaptainCommand<Integer> cmd = new EnginePowerCommand(r);
				look_up.captainSendsCommend(cmd);
				lastCommand = "moc silnika na wartoœæ " + Integer.toString(r);
			}
				break;
			case 3: {
				currTurbo = !currTurbo;
				CaptainCommand<Boolean> cmd = new TurboCommand(currTurbo);
				look_up.captainSendsCommend(cmd);
				lastCommand = "Turbo jako " + Boolean.toString(currTurbo);
				break;
			}

			}
			btnCommand.setEnabled(false);

			appendLogMessage("Ustawiæ " + lastCommand);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

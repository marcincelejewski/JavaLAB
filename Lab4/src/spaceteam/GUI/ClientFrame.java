package spaceteam.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import spaceteam.Api.CanHandleGameEvent;
import spaceteam.Api.Player;
import spaceteam.Api.Player.Role;
import spaceteam.Client.Client;
import spaceteam.Server.GameEvent;
import spaceteam.Server.GameServer;
import spaceteam.Server.ServerRemote;

public class ClientFrame extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2756577272357459381L;

	private ServerRemote look_up;

	private JPanel contentPane;
	private JTextField txtPlayerName;
	private JComboBox<Role> cbRole;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public ClientFrame() throws RemoteException, NotBoundException {
		setTitle("Spaceteam");
		init();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPlayername = new JLabel("Nick");
		lblPlayername.setBounds(10, 11, 65, 14);
		contentPane.add(lblPlayername);

		txtPlayerName = new JTextField();
		txtPlayerName.setText("Shepard");
		txtPlayerName.setBounds(10, 31, 140, 20);
		contentPane.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JButton btnJoinGame = new JButton("Do\u0142\u0105cz do rozgrywki");
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Role playerRole = (Role) cbRole.getSelectedItem();
				String name = txtPlayerName.getText().trim();

				Player p = new Player(name, playerRole);
				Client client = null;
				CanHandleGameEvent f = null;
				try {
					client = new Client(p);

					switch (p.getRole()) {
					case Kapitan:
						f = new CaptainPanel(look_up, client);
						break;
					case Obroñca:
						f = new ShieldPanel(look_up, client);
						break;
					case Mechanik:
						f = new EnginePanel(look_up, client);
						break;
					}

					client.setEventHandler(f);

					GameEvent r = look_up.joinGame(client, p);

					if (r == null) {
						return;
					}
					// TODO: Generic server response

					dispose();

					if (f != null)
						((JFrame) f).setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnJoinGame.setBounds(10, 62, 302, 23);
		contentPane.add(btnJoinGame);

		JLabel lblRole = new JLabel("Rola");
		lblRole.setBounds(172, 11, 46, 14);
		contentPane.add(lblRole);

		cbRole = new JComboBox<>();
		cbRole.setModel(new DefaultComboBoxModel<Role>(Role.values()));
		cbRole.setBounds(172, 31, 140, 20);
		contentPane.add(cbRole);
	}

	private void init() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(GameServer.SERVER_PORT);
		look_up = (ServerRemote) registry.lookup(GameServer.SERVER_LOOKUP);
	}
}

package spaceteam.GUI;

import java.awt.*;

import javax.swing.*;

import spaceteam.Api.CanHandleGameEvent;
import spaceteam.Client.ClientRemote;
import spaceteam.GUI.Beans.PlayerPanelBean;
import spaceteam.Server.GameEvent;
import spaceteam.Server.ServerRemote;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.beans.PropertyChangeEvent;

public class EnginePanel extends JFrame implements CanHandleGameEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3510018996197975430L;
	private PlayerPanelBean playerPanelBean;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnginePanel frame = new EnginePanel();
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
	public EnginePanel() {
		setTitle("Panel Silnika");
		getContentPane().setLayout(null);
		setBounds(100, 100, 475, 385);

		playerPanelBean = new PlayerPanelBean();
		playerPanelBean.setCheckboxName("Turbo");
		playerPanelBean.setCheckboxEnabled(true);
		playerPanelBean.setSpinerName("Wy³¹czony");
		playerPanelBean.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {

				if (e.getPropertyName() == "sliderValue") {
					try {
						playerPanelBean.getLook_up().setEnginePower((int) e.getNewValue());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}

				if (e.getPropertyName() == "checkbox") {
					try {
						playerPanelBean.getLook_up().setTurbo((boolean) e.getNewValue());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		playerPanelBean.setSliderEnabled(true);
		playerPanelBean.setSliderName("Moc silnika");
		playerPanelBean.setBounds(10, 11, 435, 325);
		getContentPane().add(playerPanelBean);
	}

	public EnginePanel(ServerRemote look_up, ClientRemote client) throws RemoteException {
		this();

		playerPanelBean.setLook_up(look_up);
		playerPanelBean.setClient(client);
	}

	public void handleGameEvent(GameEvent event) {
		playerPanelBean.handleGameEvent(event);
	}
}

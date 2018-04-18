package spaceteam.GUI;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import javax.swing.JFrame;

import spaceteam.Api.CanHandleGameEvent;
import spaceteam.Client.ClientRemote;
import spaceteam.GUI.Beans.PlayerPanelBean;
import spaceteam.Server.GameEvent;
import spaceteam.Server.ServerRemote;



public class ShieldPanel extends JFrame implements CanHandleGameEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6240732590905523380L;
	PlayerPanelBean playerPanelBean;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShieldPanel frame = new ShieldPanel();
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
	public ShieldPanel() {
		setTitle("Panel Tarcz");
		getContentPane().setLayout(null);
		setSize(475, 385);
		
		playerPanelBean = new PlayerPanelBean();
		playerPanelBean.setCheckboxName("Maskowanie");
		playerPanelBean.setCheckboxEnabled(true);
		
		playerPanelBean.setSliderName("Wy³¹czony");
		playerPanelBean.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if(e.getPropertyName() == "spinner") {
					try {
						playerPanelBean.getLook_up().setShieldStrength((int)e.getNewValue());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				else if (e.getPropertyName() == "checkbox") {
					try {
						playerPanelBean.getLook_up().setComouflage((boolean) e.getNewValue());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		playerPanelBean.setSpinerEnabled(true);
		playerPanelBean.setSpinerName("Si³a tarcz");
		playerPanelBean.setBounds(10, 11, 435, 325);
		getContentPane().add(playerPanelBean);
	}
	
	public ShieldPanel(ServerRemote look_up, ClientRemote client) throws RemoteException {
		this();
		
		playerPanelBean.setLook_up(look_up);
		playerPanelBean.setClient(client);
	}
	
	public void handleGameEvent(GameEvent event) {
		playerPanelBean.handleGameEvent(event);
	}
}

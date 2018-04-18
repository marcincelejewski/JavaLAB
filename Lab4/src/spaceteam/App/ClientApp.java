package spaceteam.App;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import spaceteam.GUI.ClientFrame;



public class ClientApp {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {


		ClientFrame f = new ClientFrame();
		f.setVisible(true);
	}
}

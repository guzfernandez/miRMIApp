package obligatorio.Controllers;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.common.Jugador;
import org.common.LoginController;
import org.server.services.JugadoresService;

public class LoginControllerImpl extends UnicastRemoteObject implements LoginController, Serializable {

	public LoginControllerImpl() throws RemoteException {
		super();
	}

	public Jugador login(String username, String password) throws RemoteException {
		Jugador jugador = JugadoresService.getInstance().getJugadorByUsername(username);
		return jugador;
	}
	
}

package clienteServidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ventanas.InicioUsuario;

public class Cliente {
	public static void main(String[] args) {
		RMIAgendaInterface server = null;
		try {
			System.out.println("Localizando el registro de objetos remitos");
			Registry registry = LocateRegistry.getRegistry("localhost", 5555);
			System.out.println("Obteniendo el stab del objeto remoto");
			server = (RMIAgendaInterface) registry.lookup("Agenda");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (server != null) {
			System.out.println("Conexion Realizada.");
			InicioUsuario app= new InicioUsuario();
			app.setServer(server);
			app.main(null);
		}
	}
}

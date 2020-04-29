package clienteServidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;

import clienteServidor.DatosContacto;

public class Server implements RMIAgendaInterface {
	private String bd = "adat_intercambio";
	private String login = "root";
	private String pwd = "";
	private String url = "jdbc:mysql://localhost/" + bd;
	private Connection conexion;

	public static void main(String[] args) {

		Registry reg = null;
		try {
			System.out.println("Crea el registro de objetos, escuchando en el puerto 555");
			reg = LocateRegistry.createRegistry(5555);
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido crear el registro");
			e.printStackTrace();
		}
		System.out.println("Creando el objeto servidor");
		Server serverObject = new Server();
		try {
			System.out.println("Inscribiendo el objeto servidor en el registro");
			System.out.println("Se le da un nombre único: ");
			reg.rebind("Agenda", (RMIAgendaInterface) UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
			e.printStackTrace();
		}
	}

	


	@Override
	public boolean consultaUsuario(String usuario, String contraseña) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DatosContacto sacarTabla() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void añadirDatos(DatosContacto datos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarTabla(DatosContacto datos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarDatos(int id) {
		// TODO Auto-generated method stub

	}

}

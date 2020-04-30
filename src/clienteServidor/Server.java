package clienteServidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import clienteServidor.DatosContacto;


public class Server implements RMIAgendaInterface {
	private static String usr;
	private static String pwd;
	private static String url;
	private static String driver;
	private static Connection conexion;
	private static PreparedStatement sentencia;
	private static String usuarioS;

	public static void main(String[] args) {

		Properties propiedades = new Properties();
		InputStream entrada = null;
		// acesso a db
		try {
			File miFichero = new File("config.ini");

			if (miFichero.exists()) {
				entrada = new FileInputStream(miFichero);

				propiedades.load(entrada);

				driver = propiedades.getProperty("driver");
				usr = propiedades.getProperty("usr");
				pwd = propiedades.getProperty("pwd");
				url = propiedades.getProperty("url");

				Class.forName(driver);
				try {
					conexion = DriverManager.getConnection(url, usr, pwd);
					System.out.println("Conexión OK");

				} catch (SQLException e) {
					System.out.println("Error en la conexión");
					e.printStackTrace();
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// rmi
		Registry reg = null;
		try {
			System.out.println("Crea el registro de objetos, escuchando en el puerto 5555");
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
	public boolean consultaUsuario(String usuario, String contraseña) throws RemoteException {
		usuarioS=usuario;
		boolean usuarioValido=false;
		String query="Select * From usuarios where nombreUsuario="+usuario;
		Statement stmt;
		try {
			stmt = conexion.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			if(rset.next()) {			
				if(contraseña.equals(rset.getString(2))) {
					usuarioValido= true;
				}
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuarioValido;

	}

	@Override
	public HashMap<Integer, DatosContacto> sacarTabla()  throws RemoteException {
		String query = "SELECT * FROM contactos where nombreUsuario="+usuarioS;
		HashMap<Integer, DatosContacto> map = new HashMap<Integer, DatosContacto>();
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				int id = rset.getInt("id");
				String nombre = rset.getString("nombre");
				String direccion = rset.getString("direccion");
				String telefono = rset.getString("telefono");
				DatosContacto datos = new DatosContacto(id, nombre, direccion, telefono);
				map.put(id, datos);
			}
			rset.close();
			stmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return map;
	}
	

	@Override
	public void añadirDatos(DatosContacto datos)  throws RemoteException{
		String sql = "INSERT INTO `contactos` (`id`, `nombre`, `direccion`, `telefono`,'nombreUsuario') VALUES (?,?,?,?,?)";
		try {

			sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, datos.getId());
			sentencia.setString(2, datos.getNombre());
			sentencia.setString(3, datos.getDireccion());
			sentencia.setString(4, datos.getTelefono());
			sentencia.setString(5, usuarioS);

			sentencia.executeUpdate();

		} catch (SQLException e) {
		e.printStackTrace();
		}
	}


	@Override
	public void modificarTabla(DatosContacto datos)  throws RemoteException{
		String sql = "UPDATE `contactos` SET `nombre` = ?, `direccion` = ?,`telefono` = ? WHERE `contactos`.`id` = ?";
		try {
			
			sentencia = conexion.prepareStatement(sql);

		
			sentencia.setString(1,datos.getNombre());
			sentencia.setString(2,datos.getDireccion());
			sentencia.setString(3, datos.getTelefono());
			sentencia.setInt(4, datos.getId());
			sentencia.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarDatos(int id)  throws RemoteException{
		String sql = "DELETE FROM `contactos` WHERE `contactos`.`id` = ?";
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			sentencia.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	


}

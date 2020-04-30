package clienteServidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import javax.swing.JOptionPane;

import clienteServidor.DatosContacto;
import ventanas.InicioUsuario;

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
	public boolean consultaUsuario(String usuario, String contraseña) {
		Connection cn = conectar();
		boolean usuarioValido = false;
		String query = "Select * From usuario where nombreUsuario='" + usuario+"'";
		if (cn != null) {
			System.out.println("Conectado");
			try {
				Statement stm = cn.createStatement();
				ResultSet rs = null;
				usuarioS = usuario;
				rs = stm.executeQuery(query);
				if (rs.next()) {
					if (contraseña.equals(rs.getString(2))) {
						usuarioValido = true;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return usuarioValido;

	}


	public static void añadirDatos(DatosContacto datos) {
		String sql = "INSERT INTO contactos (nombre, direccion, telefono,nombreUsuario) VALUES (?,?,?,?)";
		try {

			sentencia = conexion.prepareStatement(sql);

			sentencia.setString(1, datos.getNombre());
			sentencia.setString(2, datos.getDireccion());
			sentencia.setString(3, datos.getTelefono());
			sentencia.setString(4, InicioUsuario.correo);

			sentencia.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al crear contacto");
		}
	}


	public static Connection conectar() {
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
				conexion = DriverManager.getConnection(url, usr, pwd);

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
		return conexion;
	}
	


}

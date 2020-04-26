package conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static String urlDB;
	private static String usuarioDB;
	private static String pwdDB;
	

	public static void main(String[] args) {
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File miFichero = new File("config.ini");
			if (miFichero.exists()) {
				entrada = new FileInputStream(miFichero);
				// cargamos el archivo de configuración con los datos de la base de datos
				propiedades.load(entrada);
				// obtenemos las propiedades y asignamos dichos valores a sus variables
				urlDB = propiedades.getProperty("basedatos");
				usuarioDB = propiedades.getProperty("usuario");
				pwdDB = propiedades.getProperty("clave");
			} else
				System.err.println("Fichero no encontrado");
		} catch (IOException ex) {
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
	}

	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}

	public Connection conectar() {
		Connection conexion = null;
		main(null);
		try {
			conexion = DriverManager.getConnection(urlDB, usuarioDB, pwdDB);
			System.out.println("Conexión OK");

		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}

		return conexion;
	}
}

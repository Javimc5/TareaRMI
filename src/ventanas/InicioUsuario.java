package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clienteServidor.RMIAgendaInterface;
import clienteServidor.Server;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class InicioUsuario extends JFrame {
	static InicioUsuario frame;
	private JPanel contentPane;
	static JTextField txtUsuario;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JLabel lblNearEat;
	static JPasswordField passwordField;
	private JButton btnInicio;
	private static int contInicio = 0;
	public static String correo = "";
	public static RMIAgendaInterface server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new InicioUsuario();
					frame.setVisible(true);
					if (contInicio >= 3) {
						frame.setVisible(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame for usuario
	 */
	public InicioUsuario() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(137, 132, 180, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(55, 135, 72, 14);
		contentPane.add(lblUsuario);

		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(55, 166, 89, 14);
		contentPane.add(lblContrasea);

		lblNearEat = new JLabel("Inicio Usuario");
		lblNearEat.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		lblNearEat.setBounds(117, 28, 221, 102);
		contentPane.add(lblNearEat);

		passwordField = new JPasswordField();
		passwordField.setBounds(137, 163, 180, 20);
		contentPane.add(passwordField);

		JLabel lblNoTienesCuenta = new JLabel("No tienes cuenta?");
		lblNoTienesCuenta.setBounds(10, 236, 126, 14);
		contentPane.add(lblNoTienesCuenta);

		JButton btnRegistrate = new JButton("Registrate");
		btnRegistrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistroUsuario reg=new RegistroUsuario();
				reg.main(null);
				frame.setVisible(false);
			}
		});
		btnRegistrate.setBounds(117, 232, 111, 23);
		contentPane.add(btnRegistrate);

		btnInicio = new JButton("Inicio");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(sesionUsuario()) {
						
						MenuBusqueda inicio=new MenuBusqueda();
						inicio.main(null);
						frame.setVisible(false);
					};
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnInicio.setBounds(185, 186, 89, 23);
		contentPane.add(btnInicio);
	}

	/**
	 * Comprueba usuario y contraseña del usuario
	 * 
	 * @return True: si usuario y contraseña son correctos; False: si usuario o
	 *         contraseña son incorrectos
	 * @throws SQLException
	 */
	
	public void setServer(RMIAgendaInterface server) {
		this.server=server;
	}

	public static boolean sesionUsuario() throws SQLException {
		correo =txtUsuario.getText();
		String pass = passwordField.getText();
		 try {
			 boolean correcto=server.consultaUsuario(correo, pass);
			 if(correcto=true) {
				 JOptionPane.showMessageDialog(null, "Bienvenido " + correo + "\n\n\n");
			 }
			 else {
				 JOptionPane.showMessageDialog(null, "Usuario / Contraseña incorrectos");
			 }
			return correcto;
		} catch (RemoteException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		 return false;
	}

	
}

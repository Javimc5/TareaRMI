package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clienteServidor.Server;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class RegistroUsuario extends JFrame {

	protected static RegistroUsuario frame;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static JTextField textField;
	private static JPasswordField passwordField;
	private JButton btnVolver;

	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegistroUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame de registro de usuario.
	 */
	public RegistroUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNearEat = new JLabel("Registro");
		lblNearEat.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblNearEat.setBounds(146, 30, 89, 59);
		contentPane.add(lblNearEat);

		JLabel lblCorreo = new JLabel("Usuario:");
		lblCorreo.setBounds(10, 103, 74, 14);
		contentPane.add(lblCorreo);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 147, 97, 14);
		contentPane.add(lblContrasea);

		textField = new JTextField();
		textField.setBounds(69, 100, 282, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (crearUsuario()) {
						InicioUsuario.main(null);
						frame.setVisible(false);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCrear.setBounds(280, 206, 89, 23);
		contentPane.add(btnCrear);

		passwordField = new JPasswordField();
		passwordField.setBounds(99, 143, 252, 23);
		contentPane.add(passwordField);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				InicioUsuario.main(null);
			}
		});
		btnVolver.setBounds(10, 11, 89, 23);
		contentPane.add(btnVolver);
	}

	public static boolean crearUsuario() throws SQLException {
		
		Connection cn = Server.getConexion();
		Statement stm = cn.createStatement();
		ResultSet rs = null;

		String usuario = textField.getText();
		rs = stm.executeQuery("Select * from usuario where nombreUsuario='" + usuario + "'");
		if (!rs.next()) {
			String pass = passwordField.getText();
			if (pass.length() < 21 && pass.length() > 7) {
				try {
					//Insert en users para introducir un nuevo usuario con los datos introducidos en ventana
					String query = "Insert into usuario (nombreUsuario, password) values('"
							+ usuario + "','" + pass + "')";
					stm.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Cuenta creada con exito.");
					return true;
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error al crear cuenta.");
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Contraseña con formato no permitido. Introduzca una contraseña entre 7 y 21 caracteres.");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Correo ya registrado.");
			return false;
		}

	}
}

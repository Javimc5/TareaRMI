package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clienteServidor.DatosContacto;
import clienteServidor.Server;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearContacto extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldDireccion;
	private JTextField textFieldTelefono;
	private static CrearContacto frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new CrearContacto();
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
	public CrearContacto() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 397, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCrearContacto = new JLabel("Crear Contacto");
		lblCrearContacto.setBounds(10, 27, 166, 28);
		lblCrearContacto.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		contentPane.add(lblCrearContacto);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 86, 61, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDireccion = new JLabel("Direccion: ");
		lblDireccion.setBounds(10, 140, 61, 14);
		contentPane.add(lblDireccion);
		
		JLabel lblTelefono = new JLabel("Telefono: ");
		lblTelefono.setBounds(10, 198, 61, 14);
		contentPane.add(lblTelefono);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(81, 83, 230, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setColumns(10);
		textFieldDireccion.setBounds(81, 137, 230, 20);
		contentPane.add(textFieldDireccion);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(81, 195, 230, 20);
		contentPane.add(textFieldTelefono);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearUsuario();
				frame.setVisible(false);
			}

			
		});
		btnCrear.setBounds(119, 272, 128, 54);
		contentPane.add(btnCrear);
	}
	public void crearUsuario() {
				DatosContacto contacto=new DatosContacto(textFieldNombre.getText(), textFieldDireccion.getText(), textFieldTelefono.getText());
				Server.añadirDatos(contacto);
				
	}
}

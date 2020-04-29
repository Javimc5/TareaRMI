package clienteServidor;

public class DatosContacto {
	private int id;
	private String nombre;
	private String direccion;
	private String telefono;
	public DatosContacto(int id, String nombre, String direccion, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	
}

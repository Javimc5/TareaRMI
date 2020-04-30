package clienteServidor;

public class DatosContacto {
	private String nombre;
	private String direccion;
	private String telefono;
	public DatosContacto(String nombre, String direccion, String telefono) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
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

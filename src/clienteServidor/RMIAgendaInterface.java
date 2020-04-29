package clienteServidor;

import java.rmi.Remote;
import clienteServidor.DatosContacto;
public interface RMIAgendaInterface extends Remote {

	public boolean consultaUsuario(String usuario,String contrase�a);
	public DatosContacto sacarTabla();
	public void a�adirDatos(DatosContacto datos);
	public void modificarTabla(DatosContacto datos);
	public void eliminarDatos(int id);
}

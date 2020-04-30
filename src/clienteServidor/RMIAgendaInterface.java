package clienteServidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

import clienteServidor.DatosContacto;
public interface RMIAgendaInterface extends Remote {

	public boolean consultaUsuario(String usuario,String contrase�a) throws RemoteException;
	public HashMap<Integer, DatosContacto> sacarTabla() throws RemoteException;
	public void a�adirDatos(DatosContacto datos)throws RemoteException;
	public void modificarTabla(DatosContacto datos)throws RemoteException;
	public void eliminarDatos(int id);
}

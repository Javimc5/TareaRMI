package clienteServidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

import clienteServidor.DatosContacto;
public interface RMIAgendaInterface extends Remote {

	public boolean consultaUsuario(String usuario,String contraseña) throws RemoteException;
	public HashMap<Integer, DatosContacto> sacarTabla() throws RemoteException;
	public void añadirDatos(DatosContacto datos)throws RemoteException;
	public void modificarTabla(DatosContacto datos)throws RemoteException;
	public void eliminarDatos(int id);
}

package exo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteChat extends Remote {
	 public void send (String message) throws RemoteException;
	 public void addClient(RemoteClient c) throws RemoteException ; 
	 public void removeClient(RemoteClient t ) throws RemoteException ; 
}

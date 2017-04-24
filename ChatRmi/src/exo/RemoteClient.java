package exo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {
	void newMessage (String message) throws RemoteException ; 

}

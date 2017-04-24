package exo;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteHelloWorld extends Remote{
	//La remote exception permet de gérer les erreurs réseau 
	public String getString () throws RemoteException ; 

}

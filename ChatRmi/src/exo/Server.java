package exo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {


	public static void main(String args[]) throws RemoteException {

		if (args.length != 3){
			System.err.println("Usage: java " + Server.class.getName() + " nshost nsport name");
			System.exit(1);
		}

		RemoteChat server = new Chat(); 

		RemoteChat stub = (RemoteChat)UnicastRemoteObject.exportObject(server, 0); 

		final Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])); 

		registry.rebind(args[2], stub);
		System.out.println("Ready !");

	}

}

//pour lancer le registry il faut le lancer avec le usebasecodeonly = false 

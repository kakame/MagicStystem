package exo;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public static void main (String args []) throws RemoteException, NotBoundException{
		if (args.length != 3 ){
			System.err.println("Usage: java "+ Server.class.getName() + " nshost nsport name ");
			// nshost et nsport et name concernent le registery 
			System.exit(1);
		}
		Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
		RemoteHelloWorld test = (RemoteHelloWorld) registry.lookup(args[2]); 
		System.out.println (test.getString()); 
	}
}
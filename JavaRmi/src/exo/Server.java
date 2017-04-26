package exo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements RemoteHelloWorld {

	@Override
	public String getString() throws RemoteException {
		return "hello world";
	}

	public static void main (final String[] args ) throws RemoteException {
		if (args.length != 3 ){
			System.err.println("Usage: java "+ Server.class.getName() + " nshost nsport name ");
			// nshost et nsport et name concernent le registery 
			System.exit(1);
		}

		final RemoteHelloWorld hw = new Server () ; 
		//on rend le hw distant et 0 veut dire choisi un port au hasard 
		final Remote stub = UnicastRemoteObject.exportObject(hw, 0); 
		// localiser le registre des fonctions 
		final Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])); 
		registry.rebind(args[2], stub);
		System.out.println("Ready !");
	}

	
	// avant de lancer le code server il faut d'abord lancer dans le terminal rmiregistry numero de port 
	//rmiregistry 9000 & 
	//il faut lancer rmiregistry dans le repertoire projet/bin pour que ça marche 
	// dans ce cas la java sans "-cp bin"
	//si on fait pas ça  ç_a marche pas car : le rmiregistry est dans un autre repertoire et donc le stub va aller chercher dans le repertoire courant la classe il ne va pas la trouver donc une des solutions est d'aller directement dans le bon répertoire
}
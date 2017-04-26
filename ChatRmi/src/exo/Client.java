package exo;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client implements RemoteClient {
	private  String name ; 
	public Client (String name){
		this.name = name ; 
	}
	@Override
	public void newMessage(String message) {
		System.out.println ("[" + this.name + "] : " + message); 

	}

	public static void main(String args[]) throws RemoteException, NotBoundException {

		if (args.length != 3){
			System.err.println("Usage: java " + Server.class.getName() + " nshost nsport name");
			System.exit(1);
		}
		
		//se connecter au register
		Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
		// chopper le stub du serveur 
		RemoteChat test = (RemoteChat) registry.lookup(args[2]); 
		
		Scanner s = new Scanner (System.in);
		System.out.println("Enter name : ");
		
		//attention c'est le stub qu'il faut envoyer 
		//on cree une instance du client 
		RemoteClient c = new Client (s.nextLine().trim());
		//on cree un stub qui représente l'instance du client et que l'on va ajouter dans la liste de gestion des clients du serveur
		RemoteClient stub = (RemoteClient) UnicastRemoteObject.exportObject(c, 0); 
		//on utilise le stub du serveur pour rajouter le stub du client dans la liste du serveur 
		test.addClient(stub);
		
		
		while (true){
			test.send(s.nextLine().trim());
		}

	}


}

package exo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
	// id de connection 
	public static int uniqueId ; 

	//liste des clients 
	LinkedList al ; 

	// le port 
	private int port ; 

	private boolean keepGoing ; 

	public Server(int port){
		//init du port 
		this.port = port ; 

		// init de la liste 
		this.al = new LinkedList<ClientThread>() ; 
	}

	public void start() {
		this.keepGoing = true ; 
		try {
			// créer la socket et attente de connexion 
			ServerSocket serverSocket = new ServerSocket(this.port);

			while (this.keepGoing){
				//inint la socket d'écoute 
				Socket socket = serverSocket.accept() ; 

				//juste pour sortir au cas ou il est demandé par un client 
				if (!keepGoing){
					break ; 
				}

				// init du client qui vient de se connecter à la socket 
				ClientThread t = new ClientThread(socket, this); 

				//on ajoute le client à la liste 
				al.add(t); 

				//on start le client 
				t.start() ; 

			}

			//ce bloc va permettre de quitter le server donc de déconnecter tous les clients connectés 
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = (ClientThread) al.get(i) ; 
					try {						 
						tc.sInput.close();

						tc.sOutput.close();

						tc.client.close();

					}

					catch(IOException ioE) {

						// not much I can do

					}
				}
			}catch(Exception e){
				System.err.println(" ERROR : Exception closing the server and clients: " + e);
			}

		} catch (IOException e) {
			System.err.println(" Exception on new ServerSocket: " + e + "\n") ;
		} 

	}


	protected void stop() {
		keepGoing = false ; 

		// connect to myself as Client to exit statement
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);

		}catch ( Exception e){

		}	
	}


	synchronized void broadcast(String message) {
		for(int i = al.size(); --i >= 0;) {

			ClientThread ct = (ClientThread) al.get(i);

			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMsg(message)) {
				al.remove(i);
				this.broadcast("Disconnected Client " + ct.userName + " removed from list.");
			}
		}
	}

	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {	
			ClientThread ct = (ClientThread)al.get(i);
			// found it
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}


	public static void main(String args[]) {

		// check if port number is given 
		if (args.length != 1){
			System.err.println("Usage: java " + Server.class.getName() + " port");
			System.exit(1);
		}
		
		Server server = new Server (Integer.parseInt(args[0])) ; 
		server.start(); 

	}
}








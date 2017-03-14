package exo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

	//liste des clients 
	LinkedList<ClientThread> al ; 

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
			// server socket 
			ServerSocket serverSocket = new ServerSocket(this.port);

			while (this.keepGoing){
				//inint listening socket 
				Socket socket = serverSocket.accept() ; 

				// init who has just connected 
				ClientThread t = new ClientThread(socket, this, al.size()+1); 
				Thread tt = new Thread (t);

				//add client to list  
				al.add(t); 

				// starting the thread 
				tt.start() ; 
			}
		} catch (IOException e) {
			System.err.println(" ERROR :  Exception on new ServerSocket " ) ;
		} 

	}



	synchronized void spread (String message, ClientThread sender) {
		boolean stillAlive  = true ; 

		for(int i = 0 ;  i < al.size(); i++) {
			ClientThread ct =  al.get(i);

			// try to write to the Client if it fails remove it from the list
			// of course I will not try with myself :) 
			if (!ct.equals(sender)){
				stillAlive = ct.writeMsg(message);
			}

			if(!stillAlive) {
				al.remove(i);
			}
		}
	}

	synchronized void remove(int  id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); i++) {	
			ClientThread ct = al.get(i);

			// found it
			if(ct.id == id) {
				al.remove(i);
				// we inform others that this id has disconnected
				this.spread("Disconnected Client " + ct.userName + " removed from list.", ct);
				// output the server 
				System.out.println("Disconnected Client " + ct.userName + " removed from list.");
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








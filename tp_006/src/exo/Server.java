package exo;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {


	public static void main(String args[]) {
		// check if port number is given 
		if (args.length != 2){
			System.err.println("Usage: java " + Server.class.getName() + " port");
			System.exit(1);
		}

		try {
			ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
			while (true) {
				Socket connectionSocket = server.accept();
				Thread t = new Thread (new ClientThread(connectionSocket)); 
				t.start(); 
			}
		} catch (Exception e) {
			System.err.println("Error : " + e.getMessage());
		}
	}
}







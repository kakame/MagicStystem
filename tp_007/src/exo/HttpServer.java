package exo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private int port ; 

	HttpServer( int port ) {
		this.port = port ; 
	}

	public void start () {
		try {
			// server socket 
			ServerSocket serverSocket = new ServerSocket(this.port);

			while (true){
				//inint listening socket 
				Socket socket = serverSocket.accept() ; 

				// init who has just connected 
				ClientThread t = new ClientThread(socket); 
				Thread tt = new Thread (t);

				// starting the thread 
				tt.start() ; 
			}
		} catch (IOException e) {
			System.err.println(" ERROR :  Exception on new ServerSocket " ) ;
		} 
	}

	public static void main(String args[]) {

		// check if port number is given 
		if (args.length != 1){
			System.err.println("Usage: java " + HttpServer.class.getName() + " port");
			System.exit(1);
		}

		HttpServer server = new HttpServer (Integer.parseInt(args[0])) ; 
		server.start(); 
	}
}

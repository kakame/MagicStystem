package exo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private int port ; 

	HttpServer( int port ) {
		this.port = port ; 
	}




	public static String constructHttpHeader(int returnCode,long contentLength,String fileType) {
		String s = "HTTP/1.1 ";

		switch (returnCode) {
		case 200:
			s = s + "200 OK";
			break;
		case 404:
			s = s + "404 Not Found";
			break;
		}

		s = s + "\r\n"; 
		s = s + "Connection: close\r\n";
		s = s + "Server: MagicSystemServer \r\n"; 
		s = s + "Content-Length:"+contentLength+"\r\n";  
		switch (fileType) {
		case "jpeg":
			s = s + "Content-Type: image/jpeg\r\n";
			break;
		case "jpg":
			s = s + "Content-Type: image/jpeg\r\n";
			break;
		case "html":
			s = s + "Content-Type: text/html; charset=UTF-8\r\n";
			break;
		case "css":
			s = s + "Content-Type: text/css\r\n";
			break;
		case "js":
			s = s + "Content-Type: application/javascript\r\n";
			break;
		default:
			s = s + "Content-Type: text/html; charset=UTF-8\r\n";
			break;
		}

		s = s + "\r\n";

		return s;
	} 



	public void start () {
		try {
			// server socket 
			ServerSocket serverSocket = new ServerSocket(this.port);
			System.out.println("créer");

			while (true){
				//inint listening socket 
				Socket socket = serverSocket.accept() ; 
				System.out.println("accepter");
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

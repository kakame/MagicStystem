package exo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {

	private int port ; 

	HttpServer( int port ) {
		this.port = port ; 
	}




	/**
	 * @param returnCode HTTP Return code we handle only 200 & 404 & 400
	 * @param contentLength length of the content that we'll send
	 * @param fileType // extension to define MIME type of the content, we handled some type ...
	 * @return Http header 
	 */
	public static String constructHttpHeader(int returnCode,long contentLength,String fileType) {
		String s = "HTTP/1.0 ";

		switch (returnCode) {
		case 200:
			s = s + "200 OK";
			break;
		case 404:
			s = s + "404 Not Found";
			break;
		case 400:
			s = s + "400 Bad Request";
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
			s = s + "Content-Type: text/plain; charset=UTF-8\r\n";
			break;
		}

		s = s + "\r\n";

		return s;
	} 



	/**
	 * 
	 */
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
package exo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private int port ; 

	HttpServer( int port ) {
		this.port = port ; 
	}

	// reading the header from a buffer then getting the first line
	// splitting the first line, control in order to have only GET method &
	public String httpRequestHandler(BufferedReader buffer_req){
		try {
			String request = buffer_req.readLine();
			String[]param_request = request.split(""); 
			if(param_request[0].contains("GET") && param_request[3].contains("HTTP/1.0")){
				return param_request[2];
			}
			else{
				// we will send an error 404
				return "ERROR on request.";
			}
		} catch (IOException e) {
			System.out.println("ERROR: Reader on the BufferedReader");
			return null;
		}
	}


	public static String constructHttpHeader(int returnCode,int contentLength,String fileType) {
		String s = "HTTP/1.0 ";

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
		s = s + "Content-Length:"+contentLength;  
		switch (fileType) {
		case "JPEG":
			s = s + "Content-Type: image/jpeg\r\n";
			break;
		case "HTML":
			s = s + "Content-Type: text/html; charset=UTF-8\r\n";
			break;
		case "CSS":
			s = s + "Content-Type: text/css\r\n";
			break;
		case "JS":
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

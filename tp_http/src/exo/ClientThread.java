package exo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;

public class ClientThread implements Runnable {
	private Socket socket = null;
	protected BufferedReader input = null;
	protected DataOutputStream output = null;
	// extension to define MIME type
	protected String ext = "HTML";
	private final String rootPath = "/var/www";

	public ClientThread(Socket s ){
		this.socket = s;
	}

	/**
	 *  reading the header from a buffer then getting the first line
	 * splitting the first line, control in order to have only GET method
	 * 
	 */
	public void httpRequestHandler(BufferedReader buffer_req){
		try {

			String request = buffer_req.readLine(); //first line is the HTTP REQUEST
			String pressEnter = buffer_req.readLine(); // line 2 of the buffer in order to verify if  there is an enter press
			// i used null in fact it must be strict and we need a press enter
			if(pressEnter != null){
				// splitting the request, getting a string array " GET PATH HTTP/VERSION " 
				String[]param_request = request.split(" "); 
				if(param_request.length != 3) {
					errorHandler("Reques must be GET PATH HTTP/1.0 \n",400);

				}
				// We check if it's a GET request, and HTTP version, here it can be 1.0 or 1.1 , in order to test with webBrower 	
				else{	
					if(param_request[0].contains("GET") && (param_request[2].contains("HTTP/1.0") || param_request[2].contains("HTTP/1.1"))	){
						// calling the method handlePath
						handlePath(param_request[1]);
					}
					else{
						errorHandler("Request must be GET PATH HTTP/1.0 \n",400);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Buffered reader error");

		}
	}
	// handle the case where the file does not exist or a bad request from client
	public void errorHandler(String errMessage, int errorCode) {
		try {

			output.writeBytes(HttpServer.constructHttpHeader(errorCode,errMessage.length(), "html"));
			output.writeBytes(errMessage);
			output.flush();
		} catch (Exception e) {
			System.err.println("Error with the path "+ e.getMessage());
		}
	}

	// main function of the server, it handles the path
	public void handlePath(String path) throws IOException{

		String[] fileTab = null;
		String file = null;
		// we split the path in order to get the extension of the file
		String[] pathTab = path.split("/");	
		if(pathTab.length == 0){
			file = "index.html";
		}
		else{
			file = pathTab[pathTab.length-1];
		}
		fileTab = file.split("\\.");
		if(fileTab.length==0){
			ext = "html";
		}
		else
			ext = fileTab[fileTab.length-1];
		path = rootPath + path; 
		File f = new File(path);
		// only file, we don't handle the directories
		if(f.exists() && !f.isDirectory()) { 
			FileInputStream fis = new FileInputStream(f);
			output.writeBytes(HttpServer.constructHttpHeader(200, f.length(), ext));

			while (true) {
				// file readed line by line
				int line = fis.read();
				if (line == -1) {
					break; // EOF
				}
				output.write(line);
				output.flush();
			}
		}
		else{
			// case where the file is not found
			errorHandler("ERROR 404 FILE NOT FOUND \n",404);
		}




	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			output = new DataOutputStream(socket.getOutputStream());
			// handle the request & then generate a response
			httpRequestHandler(input);
			socket.close();
			input.close();
			output.close();

		}
		catch(IOException e){
			System.err.println("IO Problem");
		}
	}

}
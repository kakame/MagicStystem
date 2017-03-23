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
	protected String ext = "HTML";
	private final String rootPath = "/home/tp-home006/nchiout";

	public ClientThread(Socket s ){
		this.socket = s;
	}
	// reading the header from a buffer then getting the first line
	// splitting the first line, control in order to have only GET method &
	public void httpRequestHandler(BufferedReader buffer_req){
		try {
			String request = buffer_req.readLine();
			System.out.println(request);
			String[]param_request = request.split(" "); 
	
			if(param_request[0].contains("GET") && param_request[2].contains("HTTP/1.1")){
				System.out.println(param_request[1]);
				handlePath(param_request[1]);

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	public void fileNotFoundHandler() {
		try {

			// Envoi de l'erreur 404 : Fichier non-trouvé
			String retMessage = "<html><head></head><body>Fichier "
					+ " non trouvé...</body></html>\n";

			output.writeUTF(HttpServer.constructHttpHeader(404,1500, "HTML"));
			output.writeUTF(retMessage);
			System.out.println("on flush ?????");
			output.flush();
			System.out.println("j'ai flush");
		} catch (Exception e) {
			System.err.println("Erreur avec le chemin "+ e.getMessage());
		}
	}

	public void handlePath(String path) throws IOException{
		String[] pathTab = path.split("/");	
		
		String file = pathTab[pathTab.length-1];
		System.out.println(file);
		String[] fileTab = file.split("\\.");
		System.out.println(fileTab.length);
		
		System.out.println(file);
		if(fileTab.length==0){
			ext = "HTML";
		}
		else
		ext = fileTab[fileTab.length-1];
		path = rootPath + path; 
		System.out.println(path);
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
			FileInputStream fis = new FileInputStream(f);
			output.writeUTF(HttpServer.constructHttpHeader(200, f.length(), ext));
			while (true) {
				// Le fichier est lu ligne par ligne
				int line = fis.read();
				if (line == -1) {
					break; // Fin du fichier
				}
				output.write(line);
				output.flush();
			}
		}
		else{
			System.out.println("non trouve");
			fileNotFoundHandler();
		}




	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			output = new DataOutputStream(socket.getOutputStream());
			httpRequestHandler(input);
			socket.close();

		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}

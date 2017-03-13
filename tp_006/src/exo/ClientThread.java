package exo;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

class ClientThread implements Runnable {
	Socket client = null ; 

	public ClientThread(Socket c ) {
		this.client = c;
	}
	public void run() {
		try {
			System.out.println("Connected to client : "+client.getInetAddress().getHostName());
			BufferedWriter buffout = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));

			while (true){
				buffout.write(new Date().toString());
				buffout.newLine();
				//on va essayer de flusher dans le client et si ça ne marche pas on break pour sortir de cette boucle while et on passe à l'autre 
				try{
					buffout.flush();
				}catch (java.net.SocketException e){
					break ; 
				}
			}
			client.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
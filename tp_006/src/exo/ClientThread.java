package exo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ClientThread implements Runnable {
	Server s = null ; 
	Socket client = null ; 
	BufferedReader sInput ; 
	BufferedWriter sOutput ; 

	int id ; 

	String userName ; 

	public ClientThread(Socket c , Server s, int idd ) {
		this.s = s ; 
		this.client = c;
		id  = idd ; 
		userName  = c.getInetAddress().getHostAddress(); 
		try {

			sOutput = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
			sInput  = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

		}catch (IOException e){

			System.err.println("ERROR : Impossible de charger le buffer de lecture/écriture ");
			//message  for debug 
			e.printStackTrace();
			return ; 

		}
	}

	public void run() {
		String message   = "" ; 

		while(true) {

			// read a String (which is an object)
			try {
				message = sInput.readLine(); 

				// Actually i wouldn't chat on a chatserver without any quiting possibility haha  
				if (message != null  && message.contains("quitClient") ){
					break ; 
				}

			}
			catch (IOException e) {
				System.err.println("ERROR : Impossible de lire ce que le client envoi ");
				break;             
			}

			// when the message is null it could be because the client disconnected via ^c for example 
			//so we first check if the client is still here 
			if(!this.client.isConnected() || message == null ) {
				close();
				break;
			}

			// send message 
			this.s.spread(userName + ": " + message, this);


		}
		// remove myself from the arrayList containing the list of the connected Clients
		this.s.remove(id);
		close();
	}

	// try to close everything
	private void close() {
		// try to close the connection
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {}
		try {
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {};
		try {
			if(this.client != null) this.client.close();
		}
		catch (Exception e) {}
	}

	boolean writeMsg(String msg) {
		// TODO Auto-generated method stub
		// if Client is still connected send the message to it
		if(!this.client.isConnected()) {
			close();
			return false;
		}
		// write the message to the stream
		try {
			sOutput.write(msg);
			sOutput.newLine();
			try {
				sOutput.flush();
			}catch (java.net.SocketException e){
				System.err.println("ERROR : There is a problem while flushing the result");
				close();
				return false ; 
			}
		}catch(IOException e) {
			System.err.println("ERROR : There is a problem, the message has not been sent");
			//for debug
			e.printStackTrace();
		}
		return true;
	}


}
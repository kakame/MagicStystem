package exo;

import java.net.Socket;

public class ClientThread implements Runnable {

	Socket client = null ; 

	public ClientThread(Socket c) {
		this.client = c;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}

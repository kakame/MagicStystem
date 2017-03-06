package exo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	public static void main(String[] args) throws Exception {
		// first param of args will be port number 

		// check if port number is giver 
		if (args.length != 1){
			System.err.println("Usage: java " + Server.class.getName() + " port ");
			System.exit(1);
		}
		DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(args[0]));

		//init buffer of 1024 bytes 
		byte[] bufferListener = new byte[1024];

		while (true){

			// init du datagram packet avec le buffer listener 
			DatagramPacket receivePacket = new DatagramPacket(bufferListener, bufferListener.length);
			serverSocket.receive(receivePacket);

			// extract the data from message sent 
			String sentence = new String( receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);


		}

	}

}

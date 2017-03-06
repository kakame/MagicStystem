package exo;


import java.net.*;

public class Client {
	//System error to get System.err ...
	//

	

	public static void main(String[] args) throws Exception {
		if (args.length != 2){
			System.err.println("Usage: java " + Client.class.getName()+ " host port");
			System.exit(1);
		}
		
		// creating our message
		final String message = "Hello from the other side";
		// getting the srv ip @ from our args
		final InetAddress host = InetAddress.getByName(args[0]);
		// getting the srv port from our args
		final int port  = Integer.parseInt(args[1]);
		//  get an array of bytes ( buffer ) from our message
		final byte[] data = message.getBytes();
		// creating the packet 
		final DatagramPacket paquet = new DatagramPacket(data, data.length, host, port);
		// creating the socket 
		final DatagramSocket socket = new DatagramSocket();
		// sending our packet through our socket
		socket.send(paquet);
		// closing the socket
		socket.close();
	}

}

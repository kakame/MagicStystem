package exo;


import java.net.*;
import java.util.Scanner;

public class Client {
	
	

	public static void main(String[] args) throws Exception {
		if (args.length != 2){
			System.err.println("Usage: java " + Client.class.getName()+ " host port");
			System.exit(1);
		}
		Scanner buffer = new Scanner(System.in);
		
		// getting the srv ip @ from our args
		final InetAddress host = InetAddress.getByName(args[0]);
		// getting the srv port from our args
		final int port  = Integer.parseInt(args[1]);
		// creating the socket 
		final DatagramSocket socket = new DatagramSocket();
		final String quit = "clientquit";
		while(true){
			String stringData = buffer.next();
	
			if(stringData.equals(quit)){
				socket.close();
				buffer.close();
				System.exit(0);
				break;
			}
			
			byte[] data = stringData.getBytes();
			byte[] bufferListener = new byte[1024];
			
			DatagramPacket paquet = new DatagramPacket(data,data.length, host, port);
			socket.send(paquet);
			
			DatagramPacket paquetR = new DatagramPacket(bufferListener, bufferListener.length);
			
			socket.receive(paquetR);
			String mess = new String( paquetR.getData(), 0, paquetR.getLength());
			System.out.println("--"+ mess);
			
		}
		
	}

}

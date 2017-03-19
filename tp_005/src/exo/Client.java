package exo;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;


public class Client {



	public static void main(String[] args) throws Exception {
		if (args.length != 2){
			System.err.println("Usage: java " + Client.class.getName()+ " host port");
			System.exit(1);
		}

		// getting the srv ip @ from our args
		final InetAddress host = InetAddress.getByName(args[0]);
		// getting the srv port from our args
		final int port  = Integer.parseInt(args[1]);
		// creating the socket 
		final Socket  socket = new Socket(host,port);


		while(true){
			BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = buffer.readLine();
			if(message == null){
				System.out.println("Server down");
				System.exit(1);
				break;

			}
			System.out.println(buffer.readLine());
		}

	}

}
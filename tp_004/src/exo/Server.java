package exo;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

	public static void main(String[] args) throws Exception {
		// first param of args will be port number and second will delay time in nanosecond 


		// check if port number is given 
		if (args.length != 2){
			System.err.println("Usage: java " + Server.class.getName() + " port timeDelay");
			System.exit(1);
		}

		//init the delay 
		int delay = Integer.parseInt(args[1]) ; 


		//init server socket 
		ServerSocket serverSocket =new ServerSocket(Integer.parseInt(args[0]));

		while(true)
		{

			try {
				Socket connectionSocket = serverSocket.accept();
				System.out.println(connectionSocket.isConnected());

				BufferedWriter buffout = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));

				// send to the client 
				while (true){
					buffout.write(new Date().toString());
					buffout.newLine();
					buffout.flush();
					Thread.sleep(delay);
				}

			}finally{
				serverSocket.close();
			}
		}
	}
}







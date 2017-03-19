package exo;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private String host;
	private int port;
	private Socket socket;

	public static void main(String[] args) throws UnknownHostException, IOException {

		// check if port number & host name/Ip is given 
		if (args.length != 2){
			System.err.println("Usage: java " + Client.class.getName() + " 	host  port");
			System.exit(1);
		}

		new Client(args[0],Integer.parseInt(args[1])).run();

	}
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	// run for the thread which handle message sending
	public void run() throws IOException {
		try{
			//trying to connect to the socket
			socket = new Socket(host, port);
			System.out.println("Succeffuly connected: chatquit to quit the chat");
		}
		catch(UnknownHostException e){
			System.out.println("We couldn't reach the server " + host+ ":"+port);
			System.exit(1);

		}
		// Launching our listner thread
		new Thread(new ClientReceiver(socket.getInputStream())).start();
		//Scanner for reading
		Scanner sc = new Scanner(System.in);
		// I got some issues with the bufferedWriter
		// so i used a PrintStream

		PrintStream output = new PrintStream(socket.getOutputStream());
		while (sc.hasNextLine()) {
			String message = sc.nextLine();
			//quit the chat
			if(message.equals("chatquit")){
				output.close();
				sc.close();
				socket.close();
				System.out.println("Succeffully chat quit");
				System.exit(1);
				break;
			}
			//flushing my message
			output.println(message);
			output.flush();

		}
		//closing
		output.close();
		sc.close();
		socket.close();
	}
}


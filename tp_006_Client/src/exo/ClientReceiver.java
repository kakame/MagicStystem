package exo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientReceiver implements Runnable {
	//parameter passed by the client class 
	private InputStream server;
	private String message;

	public ClientReceiver(InputStream inputStream) {
		this.server = inputStream;
	}

	@Override
	public void run() {
		// our buffer to read 
		BufferedReader brIn = new BufferedReader(new InputStreamReader(server));

		while (true) {
			try {
				// to detect the EOF
				// readLine is implemented to return null when it's EOF	
				// we could also use bufer
				message = brIn.readLine();
				// condition to avoid the null spam when the server is closed 
				// there i close the client => no server => no chat
				if(message == null){
					System.out.println("Server closed");
					System.exit(1);
					break;

				}
				System.out.println(message);
			} catch (IOException e) {
				System.out.println("Problem with the buffer readed from the inputstream");
			}


		}
	}


}

package exo;

import java.rmi.RemoteException;
import java.util.LinkedList;


public class Chat implements RemoteChat {
	
	private LinkedList<RemoteClient> clients  ;  
	
	Chat () {
		this.clients = new LinkedList<RemoteClient>() ;  
	}


	@Override
	public void send(String message) throws RemoteException {
		synchronized (clients){
			for (RemoteClient t : clients){
				t.newMessage(message);
			}
		}
	}

	@Override
	public void addClient(RemoteClient t) throws RemoteException {
		synchronized (clients){
			this.clients.add(t);
		}
	}

	@Override
	public void removeClient(RemoteClient t) throws RemoteException {
		synchronized (clients){
			this.clients.remove(t);
		}
	}

}

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;


public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	int port_number;
	
	Server(Consumer<Serializable> call, int port){
	
		callback = call;
		port_number = port;
		server = new TheServer();
		server.start();
	}
	
	
	
	public void updateClients() {
		for(int i = 0; i < clients.size(); i++) {
			ClientThread t = clients.get(i);
			try {
			 t.out.writeObject(t.current);
			 t.out.reset();
			}
			catch(Exception e) {}
		}
	}
	
	
	public void updateClient(int i) {
		
		if (i > clients.size() - 1) {
			return;
		}
		else {
			ClientThread t = clients.get(i);
			try {
				t.out.writeObject(t.current);
				t.out.reset();
				
			}
			catch (Exception e) {}
		}
		
	}
		
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(port_number);){
		    System.out.println("Server is waiting for a client!");
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				c.setDaemon(true);
				count++;
				clients.add(c);
				callback.accept("client connected");	
				c.start();
				
				
			    }
			}//end of try
				catch(Exception e) {
					System.out.println("There is already a server open");
					//callback.accept(e);
				}
			}//end of while
		}
	
   
		class ClientThread extends Thread{ //reads from clients.
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			GameInfo current = new GameInfo();
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
				
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
								
				 while(true) {
					    try {
					    
					    	GameInfo data = (GameInfo) in.readObject();
					    	current = new GameInfo();
					    	current = data;
					    	current.clientid = count;
					    	callback.accept(current.clientid);	

					    	//reading a object then calculating it without waiting for next object.
					    	}
					    catch(Exception e) {
					    	//callback.accept("Client has left or something is wrong");
					    	callback.accept("Error");
					    	//clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	

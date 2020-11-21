import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	int client_port;
	String server_address_;
	boolean error_connecting = false;
	
	GameInfo current = new GameInfo();
	
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call, int port, String server_address){

		client_port = port;
	    server_address_ = server_address;
		callback = call;
	}
	
	
	public void run() {
		
		try {  //when hit start. 
		
		socketClient= new Socket(server_address_,client_port);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {
			System.out.println("ERROR CONNECTING");
			error_connecting = true;
			callback.accept(e);
		}
		
		while(true) {
			 
			try {
				
			if (socketClient.isClosed()) {
					error_connecting = true;
					socketClient.close();
					break;
			}
			
			
			
			else {
				
				
			GameInfo data = (GameInfo) in.readObject(); //read in object from server.
			current = data;
			callback.accept("accepted data");
			
			}
			
			}
			catch(Exception e) {
			current.error = true;
			callback.accept(e); //error connecting to server.
		
			}
		}
	
    }
	
	public void send(Serializable data) {
		
		try {
			out.writeObject(data);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Send Error");
			e.printStackTrace();
		}
	}

	public void setConnected(boolean s) {
		// TODO Auto-generated method stub
		this.error_connecting = s;
		
	}
	
	public boolean getConnected() {
		return this.error_connecting;
	}

}

import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {	   
		serverSocket = new ServerSocket();		
		serverSocket.setSoTimeout(100000);
		serverSocket.bind(new InetSocketAddress("127.0.0.1", port), 5000);
	}

	public void start() {
		ExecutorService exec = Executors.newFixedThreadPool(8);
		List<Thread> list = new ArrayList<Thread>();
		while(true) {
			try {
				// wait for client to accept the connection request
				Socket server = serverSocket.accept();
				
				Thread t = new Thread(new ServerThread(server));
				exec.submit(t);				
				list.add(t);
			}catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}// while loop
		
		try{
			// wait for each thread to finish the task
			for(Thread t : list) t.join();
				serverSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String [] args) {
		Scanner read = new Scanner(System.in);
		int port = 1800; //read.nextInt();
		try {			
			Server server = new Server(port);
			System.out.println("Server Started..");
			server.start();
			read.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class ServerThread implements Runnable {
	Socket server;
	public ServerThread(Socket s) {
		this.server = s;
	}
	
	@Override
	public void run() {
		try {
			// read line
			DataInputStream in = new DataInputStream(server.getInputStream());
			DataOutputStream out = new DataOutputStream(server.getOutputStream());

			for(int i=0 ; i<1000 ; ++i){				
				StringBuilder sb = new StringBuilder(in.readUTF());
				out.writeUTF(sb.toString());
			}
			
			// close the connection.
			server.close();
		} catch (Exception e) {		
			e.printStackTrace();
		}

	}

}

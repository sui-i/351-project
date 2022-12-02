package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
public class S_Main {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(351);
		S_Server server = new S_Server(serverSocket);
		Thread t1 = (new Thread (new Runnable() {
			@Override
			public void run() {
				server.startServer();
			}
		}));
		t1.start();
		
		System.out.println("[System] type /stop to stop the server");
		try (Scanner in = new Scanner(System.in)) {
			String inp = "";
			while (!inp.equals("/stop")) {
				inp = in.nextLine().strip();
			}
		}
		
		System.out.println("[System] Stop command detected.");
		server.closeServerSocket();
	
	}

}

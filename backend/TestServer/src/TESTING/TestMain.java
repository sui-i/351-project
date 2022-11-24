package TESTING;

import java.io.IOException;
import java.net.ServerSocket;

public class TestMain {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(35135);
		TestServer server = new TestServer(serverSocket);
		server.startServer();	
	}

}

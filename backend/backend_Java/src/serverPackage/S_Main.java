package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
public class S_Main {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);
		S_Server server = new S_Server(serverSocket);
		server.startServer();
	}

}

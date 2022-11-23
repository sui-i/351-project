package TESTING;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class TestServer {
	private static void print(String s) {System.out.println("[SERVER]: " + s);}
	private ServerSocket serverSocket;
	
	TestServer(ServerSocket serverSocket) {
		TestServer.print("Initiating...");
		this.serverSocket = serverSocket;
		TestServer.print("Initialized.");
	}
	
	public void startServer()
	{
		TestServer.print("Starting...");
		try 
		{
			while (!serverSocket.isClosed())
			{
				Socket socket = serverSocket.accept();
				TestServer.print("New client has connected.");
				TestClientHandeler clientHandler = new TestClientHandeler(socket);

				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		}
		catch (IOException e)
		{
			closeServerSocket();
		}
	}
	
	public void closeServerSocket()
	{
		TestServer.print("Stopping...");
		try {
			if (serverSocket!=null)
				serverSocket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		TestServer.print("Stopped.");
	}
}

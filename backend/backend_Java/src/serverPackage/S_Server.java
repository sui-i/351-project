package serverPackage;

import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;



public class S_Server {
	private static void print(String s) {System.out.println("[SERVER]: " + s);}
	private ServerSocket serverSocket;
	
	S_Server(ServerSocket serverSocket) {
		S_Server.print("Initiating...");
		this.serverSocket = serverSocket;
		S_Server.print("Initialized.");
	}
	
	public void startServer()
	{
		S_Server.print("Starting...");
		try 
		{
			while (!serverSocket.isClosed())
			{
				Socket socket = serverSocket.accept();
				S_Server.print("New client connected.");
				S_Client clientHandler = new S_Client(socket);

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
		S_Server.print("Stopping...");
		try {
			if (serverSocket!=null)
				serverSocket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		S_Server.print("Stopped.");
	}
	
	
}

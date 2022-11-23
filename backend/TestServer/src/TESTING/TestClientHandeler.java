package TESTING;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TestClientHandeler implements Runnable {
	private static void print(String s) {System.out.println("[SERVER]: " + s);}
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public TestClientHandeler(Socket socket)
	{
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader (socket.getInputStream ()));
		} catch (IOException e){
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
		
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if (socket!=null)socket.close();
			if (bufferedReader!=null)bufferedReader.close();
			if (bufferedWriter!=null)bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TestClientHandeler.print("Client has disconnected");
		return;
	}
	
	@Override
	public void run() {
		String ClientMessage;
		while (socket.isConnected()) {
			try {
				ClientMessage = bufferedReader.readLine();
				if (ClientMessage==null)ClientMessage = "Invalid message format";
				System.out.println("Recieved '" + ClientMessage + "'");
				bufferedWriter.write(ClientMessage);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				System.out.println("Now sent");
			}
			catch (IOException e){
				closeEverything(socket,bufferedReader,bufferedWriter);
				return;
			}
		}
	}

}

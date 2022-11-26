package serverPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

public class S_Client implements Runnable{
	private static void print(String s) {System.out.println("[SERVER]: " + s);}
	//private static final Path RootPath = Path.of("C:\\Users\\Pcito\\Desktop\\Univ\\Fall 2022 2023\\ece 351\\351-project");
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private ReservationHandler reservationHandler;
	
	public S_Client(Socket socket)
	{
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader (socket.getInputStream ()));
			
		} catch (IOException e){
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
		reservationHandler = ReservationHandler.gethandler(0);
		
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if (bufferedReader!=null)bufferedReader.close();
			if (bufferedWriter!=null)bufferedWriter.close();
			if (socket!=null)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		S_Client.print("A client has disconnected.");
	}
	
	@Override
	public void run() {
		String RequestToHandle;
		String ResponseToSend;
		
		while (socket.isConnected()) {
			try {
				RequestToHandle = bufferedReader.readLine();
				ResponseToSend = reservationHandler.handleRequest(RequestToHandle);
				if (ResponseToSend!=null) bufferedWriter.write(ResponseToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();	
			}
			catch (IOException e){
				closeEverything(socket,bufferedReader,bufferedWriter);
				return;
			}
		}
	}
}

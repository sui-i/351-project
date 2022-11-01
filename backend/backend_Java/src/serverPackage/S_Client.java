package serverPackage;
import java.time.Clock;
import java.util.HashMap;
import java.util.LinkedList;

import DataBasePackage.DB_API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class S_Client implements Runnable{
	private static void print(String s) {System.out.println("[SERVER]: " + s);}
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	boolean isLoggedIn;
	String clientUsername;
	String clientEmail;
	String clientPassword;
	String firstName;
	String lastName;

	
	public S_Client(Socket socket)
	{
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader (socket.getInputStream( )));
			isLoggedIn = false;
				
		} catch (IOException e){
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
		
	}
	
	private static int validateRoomID(String roomID) 
	{
		//checks if the roomID is within format returns 1
		//checks if the solar system is a valid returns 2
		//checks if the Planet is valid returns 3
		//checks if the Hotel is valid returns 4
		//checks if the room is within range of rooms 5
		//if all is well return 0
	}
	public int reserve(String roomID, int time)
	{
		if (!isLoggedIn)
			return -1;
		int roomErrorCode = S_Client.validateRoomID(roomID);
		if (roomErrorCode!=0)
		{
			return roomErrorCode;
		}
		//if room reserved is already reserved at hour, return 6
		
		//if all is well, reserve the room and return 0
	}
	public int unReserve(String roomID, int time)
	{
		if (!isLoggedIn)
			return -1;
		int roomErrorCode = S_Client.validateRoomID(roomID);
		if (roomErrorCode!=0)
		{
			return roomErrorCode;
		}
		//if this room is not reserved at the certain time by this user, return 6
		
		//if all is well, remove reserve the room and return 0
	}
	
	@Override
	public void run() {
		
		
	}
}

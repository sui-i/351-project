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
			this.bufferedReader = new BufferedReader(new InputStreamReader (socket.getInputStream ()));
			isLoggedIn = false;
				
		} catch (IOException e){
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
		
	}
	public void removeS_Client() {
		isLoggedIn = false;
		S_Client.print(String.format("A client has disconnected"));
	}
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		removeS_Client();
		try {
			if (bufferedReader!=null)bufferedReader.close();
			if (bufferedWriter!=null)bufferedWriter.close();
			if (socket!=null)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int Register(String username, String email, String password, String firstName, String lastName) {
		//if username already exists return 1
		//if email already exists return 2
		//if password is too small return 3
		//if database error return -1
		
		//sends a mail if any issue return 4
		//set new user in database (with Verified email = false, verification id = random number) then.) and return 0
		
	}
	
	public int Login(String username, String password) {
		//if username is not found return 1
		//if username is found but password is wrong, return 2
		//if email is not verified return 3
		//if database error return -1
		//else return 0, and set all the parameters according to the database.
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

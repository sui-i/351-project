package serverPackage;

import requestsrepliescodes.IdentificationCodes;
import requestsrepliescodes.ReservationCodes;

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
	

	/**INCOMPLETE.
	 * Registering the user uses the database interface to add a new user, and calls on the Email
	 * server to send a verification code.
	 * register does NOT login the user.
	 * 
	 * self explanatory parameters
	 * 
	 * @return ID: appropritate enum identification code
	 * */
	public IdentificationCodes Register(String username, String password, String email, String firstName, String lastName) {
		//if username already exists return 1
		//if email already exists return 2
		//if password is too small return 3
		//if database error return -1
		
		//sends a mail if any issue return 4
		//set new user in database (with Verified email = false, verification id = random number) then.) and return 0
		
	}
	/**INCOMPLETE.
	 * Login method is called to login a client, or provide feedback on why a login failed.
	 * if a login is successful, the isLoggedin Boolean is flagged.
	 * 
	 * self explanatory parameters
	 * 
	 * @return ID: appropriate enum identification code
	 * */
	public IdentificationCodes Login(String username, String password) {
		
		return IdentificationCodes.LoginSuccessful;
	}
	
	/**
	 * Logs the client out by reseting all parameters,
	 * */
	public void Logout() {
		isLoggedIn=false;
		clientUsername = clientEmail = clientPassword = firstName = lastName = null;
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
	
	
	/**INCOMPLETE.
	 * Handeling requests.
	 * 
	 * Requests come in 2 forms:
	 * 1-Identification requests
	 * 		a+login 						FORMAT: "Req110:username,password"
	 * 		b+registration					FORMAT: "Req120:username,password,email"
	 * 		c+verification					FORMAT: "Req130:username,verificationcode"
	 * 		d+logout						FORMAT: "Req140"
	 * 2-Reservation request:
	 * 		a+reserve						FORMAT: "Req210:{ROOMID},YYYY.MM.DD.HH-YYYY.MM.DD.HH" (start date-finish date)
	 * 		b+unreserve						FORMAT: "Req220:{ROOMID}"
	 * 
	 * 
	 * Replies by the server are:
	 * 0-Invalid request					FORMAT: "Rep000"
	 * 1-a. login replies
	 * 		+logged in successfully			FORMAT: "Rep110"
	 * 		+Email not verified				FORMAT: "Rep111"
	 * 		+Username not found				FORMAT: "Rep112"
	 * 		+Wrong Password					FORMAT: "Rep113"
	 * 
	 * 1-b. registration replies
	 * 		+Registered successfully		FORMAT: "Rep120"
	 * 		+Email not Available			FORMAT: "Rep121"
	 * 		+Email already exists			FORMAT: "Rep122"
	 * 		+Username already exists		FORMAT: "Rep123"
	 * 
	 * 1-c. verification replies
	 * 		+Verification successful 		FORMAT: "Rep130"
	 * 		+Wrong verificationCode			FORMAT: "Rep131"
	 * 		+User already verified			FORMAT: "Rep132"
	 * 
	 * 1-d. logout
	 * 		+logout successful				FORMAT: "Rep140"
	 * */
	public String handleRequest(String request)
	{
		
		return "Rep000";
	}
	
	@Override
	public void run() {
		String RequestToHandle;
		String ResponseToSend;
		
		while (socket.isConnected()) {
			try {
				RequestToHandle = bufferedReader.readLine();
			}
			catch (IOException e){}
		}
		
		
	}
}

package serverPackage;

import requestsrepliescodes.IdentificationCodes;
import requestsrepliescodes.ReservationCodes;

import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;
import java.util.Date;
public class ReservationHandler {
	private static HashMap<Long,ReservationHandler> Clients = new HashMap<>();
	private static Random rand = new Random();
	private static Date date = new Date();
	private static int userConnections = 0;
	private static int checkAt = 10;
	
	final long TOKENID;
	private boolean isLoggedIn;
	
	
	long lastSeen; //Should be updated after method call.
	String clientUsername;
	String clientEmail;
	String clientPassword;
	String firstName;
	String lastName;
	
	/**
	 * Constructing a reservation handler is not a public task, it should be verified by a token.
	 * While constructing a new reservation handler, check if other reservation handler are inactive,
	 * if any is unseen for 10 minutes, kick them out.
	 * 
	 * 
	 * @return a client handler object
	 */
	private ReservationHandler(){
		long Tok = 0; while (Tok==0) Tok = rand.nextLong();
		TOKENID=Tok;
		lastSeen = date.getTime();
		Clients.put(TOKENID, this);
		if (userConnections++==checkAt) {
			userConnections=0;
			checkAt*=2;
			for (Long T: Clients.keySet())
				if (date.getTime() - Clients.get(T).lastSeen < 10*60*1000)
				{
					Clients.remove(T);
					userConnections--;
				}
		}
		if (userConnections<checkAt/4)
			checkAt= Math.max(checkAt/2, 10);
		
	}
	/**
	 * The public handler constructor first checks if a user with the same token is already connected.
	 * if not, it creates a new reservation handler with a random token and returns it.
	 * 
	 * @param long integer: Token
	 * @return ReservationHandler
	 * */
	public static ReservationHandler gethandler(long Token) {
		if (Token!=0 && Clients.containsKey(Token))
			return Clients.get(Token);
		return new ReservationHandler();
	}
	
	/**INCOMPLETE.
	 * Registering the user uses the database interface to add a new user, and calls on the Email
	 * server to send a verification code.
	 * register does NOT login the user.
	 * 
	 * self explanatory parameters
	 * 
	 * @return ID: appropriate enum identification code
	 * */
	public static IdentificationCodes Register(String username, String password, String email, String firstName, String lastName) {
		//TODO:DO
		return IdentificationCodes.RegistrationSuccessul;
	}
	
	/**
	 * Used to Verify email adderesses.
	 * returns either wrong or successful.
	 * */
	public static IdentificationCodes VerifyEmail(String username, String VerificationCode) {
		//TODO:DO
		return IdentificationCodes.VerificationSuccessful;
	}
	
	/**INCOMPLETE.
	 * Login method is called to login a client, or provide feedback on why a login failed.
	 * if a login is successful, the isLoggedin Boolean is flagged, .
	 * 
	 * self explanatory parameters
	 * 
	 * @return ID: appropriate enum identification code
	 * */
	private IdentificationCodes Login(String username, String password) {
		//TODO:DO
		return IdentificationCodes.LoginSuccessful;
	}
	
	/**
	 * Logs the client out by reseting all parameters.
	 * This function DOES NOT remove the client from the clients map,
	 * as it gives the chance for a login attempt.
	 * */
	private IdentificationCodes Logout() {
		isLoggedIn=false;
		clientUsername = clientEmail = clientPassword = firstName = lastName = null;
		return IdentificationCodes.LogoutSuccessful;
	}
	
	
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null)
	        return false;
	    try
	        {Integer.parseInt(strNum);}
	    catch (NumberFormatException nfe)
	        {return false;}
	    return true;
	}
	
	/**
	 * checks if the roomID is within format 		
	 * checks if the solar system is a valid 		
	 * checks if the Planet is valid 				
	 * checks if the Hotel is valid 					
	 * checks if the room is within range of rooms 	
		
	 * @param roomID
	 * @return appropriate ReservationCode
	 */
	private static ReservationCodes validateRoomID(String roomID) 
	{
		if (roomID.length()!=16 || !isNumeric((String) roomID.subSequence(12, 15)))
			return ReservationCodes.RoomIDInvalid;
		
		
		return ReservationCodes.RoomFoundSuccessfully;
	}
	
	/**
	 * Reserve first checks if the room is available by calling validateRoomID, 
	 * then tries to reserve the room. If it fails, it returns the appropriate error
	 * 
	 * @param roomID
	 * @param time
	 * @return appropriate ReservationCode
	 */
	private ReservationCodes Reserve(String roomID, int start, int end)
	{
		if (!isLoggedIn)
			return ReservationCodes.IndentityError;
		ReservationCodes roomCode = ReservationHandler.validateRoomID(roomID);
		if (roomCode!=ReservationCodes.RoomFoundSuccessfully)
			return roomCode;
		
		//TODO: RESERVE
		
		return ReservationCodes.RoomStatusChangedSuccessfully;
	}
	
	/**
	 * unReserve first checks if the room is available by calling validateRoomID, 
	 * then tries to unreserve the room. If it fails, it returns the appropriate error
	 * 
	 * @param roomID
	 * @param time
	 * @return appropriate ReservationCode
	 */
	private ReservationCodes unReserve(String roomID, int start, int end)
	{
		if (!isLoggedIn)
			return ReservationCodes.IndentityError;
		ReservationCodes roomCode = ReservationHandler.validateRoomID(roomID);
		if (roomCode!=ReservationCodes.RoomFoundSuccessfully)
			return roomCode;

		return ReservationCodes.RoomStatusChangedSuccessfully;
	}
	
	
	/**
	 * Handling requests.
	 * 
	 * Requests come in 2 forms:
	 * 1-Identification requests
	 * 		a+login 						FORMAT: "Req110:username,password"
	 * 		b+registration					FORMAT: "Req120:username,password,email,firstname,lastname"
	 * 		c+verification					FORMAT: "Req130:username,verificationcode"
	 * 		d+logout						FORMAT: "Req140"
	 * 2-Reservation request:
	 * 		a+reserve						FORMAT: "Req210:{ROOMID},YYYY.MM.DD.HH-YYYY.MM.DD.HH" (start date-finish date)
	 * 		b+unreserve						FORMAT: "Req220:{ROOMID},YYYY.MM.DD.HH-YYYY.MM.DD.HH"
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
		lastSeen = date.getTime();
		String def = "Rep000";
		if (request==null)
			return def;
		
		//HANDLE IDENTIFICATIONS:
		if (request.length()<6 || !request.subSequence(0, 3).equals("Req"))
			return def;
		String RCode = (String) request.subSequence(3, 6);
		if ("110".equals(RCode)) //login
		{
			if (request.length()<8)
				return def;
			String[] parser = request.split(":");
			if (parser.length!=2)
				return def;
			String[] Cred = parser[1].split(",");
			if (Cred.length!=2)
				return def;
			return "Rep"+Login(Cred[0],Cred[1]).ID;	
		}
		if ("120".equals(RCode)) //register
		{
			System.out.println("Got here");
			if (request.length()<8)
				return def;
			String[] parser = request.split(":");
			if (parser.length!=2)
				return def;
			String[] Cred = parser[1].split(",");
			if (Cred.length!=5)
				return def;
			return "Rep"+Register(Cred[0],Cred[1],Cred[2],Cred[3],Cred[4]).ID;	
		}
		if ("130".equals(RCode)) //verify email
		{
			if (request.length()<8)
				return def;
			String[] parser = request.split(":");
			if (parser.length!=2)
				return def;
			String[] Cred = parser[1].split(",");
			if (Cred.length!=2)
				return def;
			return "Rep"+VerifyEmail(Cred[0],Cred[1]).ID;	
		}
		if (request.subSequence(3, 6)=="140") //logout
		{
			return "Rep"+Logout().ID;
		}
		
		//HANDLE RESERVATIONS:
		String[] parser = request.split(":");
		if (parser.length!=2)
			return def;
		String[] Param = parser[1].split(",");
		if (Param.length!=2)
			return def;
		
		//TODO: HANDLE TIMES
		String[] timeRange = Param[1].split("-");
		String RoomID = Param[0];
		
		if ("210".equals(RCode)) //Reserve
			return "Rep"+Reserve(RoomID,0,0).ID;
		if ("220".equals(RCode)) //Unreserve
			return "Rep"+unReserve(RoomID,0,0).ID;
		return def;
	}
}
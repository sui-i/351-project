package serverPackage;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import DataBasePackage.DB_API;
import DataBasePackage.DB_UserInformation;
import emailVerificationServer.EmailAPI;
import requestsrepliescodes.IdentificationCodes;
import requestsrepliescodes.ReservationCodes;
import requestsrepliescodes.UserTypeCodes;

public class ReservationHandler {
	private static HashMap<Long, ReservationHandler> Clients = new HashMap<>();
	private static Random rand = new Random();
	private static Date date = new Date();
	private static int userConnections = 0;
	private static int checkAt = 10;

	private DB_API db;
	final long TOKENID;
	private long lastSeen; // Should be updated after method call.

	UserTypeCodes accountType;
	DB_UserInformation clientInfo;
	String clientUsername;
	String clientEmail;
	String clientPassword;
	String clientFirstName;
	String clientLastName;

	/**
	 * Constructing a reservation handler is not a public task, it should be
	 * verified by a token.
	 * While constructing a new reservation handler, check if other reservation
	 * handler are inactive,
	 * if any is unseen for 10 minutes, kick them out.
	 * 
	 * 
	 * @return a client handler object
	 */
	private ReservationHandler() {
		db = new DB_API();
		db.ConnectDB();
		long Tok = 0;
		while (Tok == 0)
			Tok = rand.nextLong();
		TOKENID = Tok;
		lastSeen = date.getTime();
		Clients.put(TOKENID, this);
		if (userConnections++ == checkAt) {
			userConnections = 0;
			checkAt *= 2;
			for (Long T : Clients.keySet())
				if (date.getTime() - Clients.get(T).lastSeen < 10 * 60 * 1000) {
					Clients.remove(T);
					userConnections--;
				}
		}
		if (userConnections < checkAt / 4)
			checkAt = Math.max(checkAt / 2, 10);

	}

	/**
	 * The public handler constructor first checks if a user with the same token is
	 * already connected.
	 * if not, it creates a new reservation handler with a random token and returns
	 * it.
	 * 
	 * @param long integer: Token
	 * @return ReservationHandler
	 */
	public static ReservationHandler gethandler(long Token) {
		if (Token != 0 && Clients.containsKey(Token))
			return Clients.get(Token);
		return new ReservationHandler();
	}

	/**
	 * Updates the info of the user based on the database user information object.
	 * 
	 * @param info: database user information object.
	 */
	private void updateInfo(DB_UserInformation info) {
		this.clientInfo = info;
		this.clientUsername = info.getuserName();
		this.clientEmail = info.getEmail();
		this.clientFirstName = info.getfirstName();
		this.clientLastName = info.getlastName();
		this.accountType = info.getUserType();
	}

	/**
	 * INCOMPLETE.
	 * Registering the user uses the database interface to add a new user, and calls
	 * on the Email
	 * server to send a verification code.
	 * register does NOT login the user.
	 * 
	 * self explanatory parameters
	 * 
	 * @return ID: appropriate enum identification code
	 */
	public IdentificationCodes Register(String username, String password, String email, String firstName,
			String lastName) {
		// Search for username, if it exists and is verified, dump
		UserTypeCodes idcByUsername = db.checkMembershipUserName(username);
		if (idcByUsername.equals(UserTypeCodes.InternalError))
			return IdentificationCodes.InternalError;
		if (idcByUsername.equals(UserTypeCodes.VerifiedUser) || idcByUsername.equals(UserTypeCodes.Admin))
			return IdentificationCodes.UsernameAlreadyExists;

		UserTypeCodes idcByEmail = db.checkMembershipUserName(username);
		if (idcByEmail.equals(UserTypeCodes.VerifiedUser) || idcByUsername.equals(UserTypeCodes.Admin))
			return IdentificationCodes.EmailAlreadyExists;

		// then create a registration code and try to send it by mail, if that fails,
		// dump
		String verificationCode = "";
		for (int i = 0; i < 6; i++)
			verificationCode += (char) rand.nextInt((int) 'A', (int) 'Z');
		// TODO: switch it to MailCodes
		boolean mailCode = EmailAPI.send("Verify your email!", "Your verification code is: " + verificationCode, email);
		if (!mailCode)
			return IdentificationCodes.EmailSendingError;

		// then add the new user.
		// remove email or username if it exists
		if (idcByEmail.equals(UserTypeCodes.NonVerifiedUser))
			db.deleteUserByEmail(username);
		if (idcByUsername.equals(UserTypeCodes.NonVerifiedUser))
			db.deleteUserByUsername(username);

		IdentificationCodes register = db.RegisterUser(username, email, password, firstName, lastName,
				verificationCode);
		if (!register.equals(IdentificationCodes.RegistrationSuccessul))
			return register;
		return IdentificationCodes.RegistrationSuccessul;
	}

	/**
	 * Used to Verify email addresses.
	 * returns either wrong or successful.
	 */
	public IdentificationCodes VerifyEmail(String username, String verificationCode) {
		// search for username, if its not found or is already verified, dump
		UserTypeCodes idc = db.checkMembershipUserName(username);
		if (idc.equals(UserTypeCodes.InternalError))
			return IdentificationCodes.InternalError;
		if (idc.equals(UserTypeCodes.NotFound))
			return IdentificationCodes.UsernameNotFound;
		if (!idc.equals(UserTypeCodes.NonVerifiedUser))
			return IdentificationCodes.UserAlreadyVerified;

		// check if the verification code matches the one in the DB. if not, dump
		DB_UserInformation userInfo = db.getUserInfo(username);
		if (userInfo == null)
			return IdentificationCodes.InternalError;
		if (!userInfo.getVerificationCode().equals(verificationCode))
			return IdentificationCodes.WrongVerrificationCode;

		// change the Account to verified user in DB, change the verification code field
		// to an empty value
		if (!db.verifyAccount(username))
			return IdentificationCodes.InternalError;
		return IdentificationCodes.VerificationSuccessful;
	}

	/**
	 * INCOMPLETE.
	 * Login method is called to login a client, or provide feedback on why a login
	 * failed.
	 * if a login is successful, the isLoggedin Boolean is flagged, .
	 * 
	 * self explanatory parameters
	 * 
	 * @return ID: appropriate enum identification code
	 */
	public IdentificationCodes Login(String username, String password) {
		// TODO:check if username password pair matches. if so, isLoggedIn = True and
		// import all userInfo
		// else dump.
		UserTypeCodes idc = db.checkMembershipUserName(username);
		if (idc.equals(UserTypeCodes.InternalError))
			return IdentificationCodes.InternalError;
		if (idc.equals(UserTypeCodes.NotFound))
			return IdentificationCodes.UsernameNotFound;
		if (!idc.equals(UserTypeCodes.NonVerifiedUser))
			return IdentificationCodes.EmailNotVerified;
		DB_UserInformation info = db.getUserInfo(username);
		updateInfo(info);
		return IdentificationCodes.LoginSuccessful;
	}

	/**
	 * Logs the client out by reseting all parameters.
	 * This function DOES NOT remove the client from the clients map,
	 * as it gives the chance for a login attempt.
	 */
	public IdentificationCodes Logout() {
		accountType = UserTypeCodes.NotLoggedIn;
		clientUsername = clientEmail = clientPassword = clientFirstName = clientLastName = null;
		return IdentificationCodes.LogoutSuccessful;
	}

	/**
	 * Get all user info
	 * 
	 * @return array[4] String of {username, email, firstname, lastname}
	 */
	public String[] getUserInfo() {
		if (!accountType.equals(UserTypeCodes.NotLoggedIn) && clientUsername != null && clientEmail != null
				&& clientLastName != null && clientFirstName != null)
			return String.format("%s,%s,%s,%s", clientUsername, clientEmail, clientFirstName, clientLastName)
					.split(",");
		return new String[] { "", "", "", "" };
	}

	/**
	 * deletes the account with the given username
	 * 
	 * @param username
	 * @return appropriate IdentificationCode
	 */
	public IdentificationCodes DeleteAccount(String username) {
		UserTypeCodes idc = db.checkMembershipUserName(username);
		if (idc.equals(UserTypeCodes.InternalError))
			return IdentificationCodes.InternalError;
		if (idc.equals(UserTypeCodes.NotFound))
			return IdentificationCodes.UsernameNotFound;
		if (accountType.equals(UserTypeCodes.NotLoggedIn))
			return IdentificationCodes.InsufficientPermissions;
		// check if logged in user is an admin, if so, delete the user associated with
		// username.
		// check if logged in user is the one holding the username, if so, also delete.
		if (clientUsername.equals(username) || accountType.equals(UserTypeCodes.Admin)) {
			db.deleteUserByUsername(username);
			return IdentificationCodes.AccountDeletedSuccessfully;
		}
		// else dump
		return IdentificationCodes.InternalError;
	}

	/**
	 * resends a new verification code for the given username and updates the
	 * database
	 * 
	 * @param username
	 * @return appropriate IdentificationCode
	 */
	public IdentificationCodes ResendVerificationCode(String username) {
		UserTypeCodes idc = db.checkMembershipUserName(username);
		if (idc.equals(UserTypeCodes.InternalError))
			return IdentificationCodes.InternalError;
		if (idc.equals(UserTypeCodes.NotFound))
			return IdentificationCodes.UsernameNotFound;

		// resend and check if it is sent, then update database of username
		DB_UserInformation userInfo = db.getUserInfo(username);
		if (userInfo == null)
			return IdentificationCodes.InternalError;
		if (!userInfo.getUserType().equals(UserTypeCodes.NonVerifiedUser))
			return IdentificationCodes.UserAlreadyVerified;

		boolean mailCode = EmailAPI.send("Verify your email!",
				"Your verification code is: " + userInfo.getVerificationCode(), userInfo.getEmail());
		if (!mailCode)
			return IdentificationCodes.EmailSendingError;

		return IdentificationCodes.VerificationCodeResentSuccessfully;
	}

	/**
	 * Reserve first checks if the room is available by calling validateRoomID,
	 * then tries to reserve the room. If it fails, it returns the appropriate error
	 * 
	 * @param roomID
	 * @param time
	 * @return appropriate ReservationCode
	 */
	public ReservationCodes Reserve(String roomID, String startTime, String finishTime) {
		if (accountType.equals(UserTypeCodes.NotLoggedIn))
			return ReservationCodes.IndentityError;
		ReservationCodes roomCode = validateRoomID(roomID);
		if (roomCode != ReservationCodes.RoomFoundSuccessfully)
			return roomCode;

		// RESERVE
		ReservationCodes rc = db.checkRoomAvailability(roomID, startTime, finishTime);
		if (!rc.equals(ReservationCodes.RoomAvailable))
			return rc;
		rc = db.Reserve(clientUsername, roomID, startTime, finishTime);
		if (rc.equals(ReservationCodes.RoomAvailable))
			return rc;
		return ReservationCodes.RoomStatusChangedSuccessfully;
	}

	/**
	 * unReserve first checks if the room is available by calling validateRoomID,
	 * then tries to unreserve the room. If it fails, it returns the appropriate
	 * error
	 * 
	 * @param roomID
	 * @param time
	 * @return appropriate ReservationCode
	 */
	public ReservationCodes unReserve(String roomID, String startTime) {
		if (accountType.equals(UserTypeCodes.NotLoggedIn))
			return ReservationCodes.IndentityError;
		ReservationCodes roomCode = validateRoomID(roomID);
		if (roomCode != ReservationCodes.RoomFoundSuccessfully)
			return roomCode;
		// unReserve
		return db.CancelReservation(clientUsername, roomID, startTime);
	}

	/**
	 * Rescheduling is about unreserving a room at its old time, then scheduling it
	 * at the new time.
	 * 
	 * Careful consideration:
	 * if the unreserve operation was successful and the reserve unsuccessful we
	 * must reserver again at the initial time.
	 * =>This function adds a huge threadlock to the database.
	 * 
	 * @param roomID
	 * @param time
	 * @return appropriate ReservationCode
	 */
	public ReservationCodes Reschedule(String roomID, String oldStartTime, String newStartTime, String newFinishTime) {

		if (accountType.equals(UserTypeCodes.NotLoggedIn))
			return ReservationCodes.IndentityError;
		ReservationCodes roomCode = validateRoomID(roomID);
		if (roomCode != ReservationCodes.RoomFoundSuccessfully)
			return roomCode;

		ReservationCodes rc = unReserve(roomID, oldStartTime);
		if (!rc.equals(ReservationCodes.RoomStatusChangedSuccessfully))
			return rc;
		rc = Reserve(roomID, newStartTime, newFinishTime);
		if (!rc.equals(ReservationCodes.RoomStatusChangedSuccessfully)) {
			//rereserve if unreserve was fine but reserve failed.
			return rc;
		}

		return ReservationCodes.RoomStatusChangedSuccessfully;
	}

	/**
	 * Checks if room is available given a time interval
	 * 
	 * @param roomID
	 * @param time
	 * @return appropriate ReservationCode
	 */
	public boolean CheckIfRoomIsAvailable(String roomID, String startTime, String finishTime) {
		ReservationCodes roomCode = validateRoomID(roomID);
		if (roomCode != ReservationCodes.RoomFoundSuccessfully)
			return false;
		
		//check if its free within time range.
		ReservationCodes rc = db.checkRoomAvailability(roomID, startTime, finishTime);
		if (!rc.equals(ReservationCodes.RoomAvailable))
			return false;		
		return true;
	}

	/**
	 * Handling requests.
	 * 
	 * Requests come in 2 forms:
	 * 1-Identification requests
	 * a+login 									FORMAT: "Req110:username,password"
	 * b+registration 							FORMAT: "Req120:username,password,email,firstname,lastname"
	 * c+verification 							FORMAT: "Req130:username,verificationcode"
	 * d+logout 								FORMAT: "Req140"
	 * e+get all info 							FORMAT: "Req150"
	 * f+delete account 						FORMAT: "Req160:username"
	 * g+resend verification code 				FORMAT: "Req170"
	 * 2-Reservation request:
	 * a+reserve 								FORMAT: "Req210:{ROOMID},YYYY-MM-DD HH:MM:SS^YYYY-MM-DD HH:MM:SS"
	 * (start date^finish date)
	 * b+unreserve 								FORMAT: "Req220:{ROOMID},YYYY-MM-DD HH:MM:SS" (start date)
	 * c+reschedule 							FORMAT: "Req240:{ROOMID},YYYY-MM-DD HH:MM:SS^YYYY-MM-DD HH:MM:SS^YYYY-MM-DD HH:MM:SS" (old start date^new start date^new finish date)
	 * 
	 * Replies by the server are:
	 * 0-a. Invalid request 					FORMAT: "Rep000"
	 * 0-b. Internal error 						FORMAT: "Rep231"
	 * 1-a. login replies
	 * +logged in successfully 					FORMAT: "Rep110"
	 * +Email not verified 						FORMAT: "Rep111"
	 * +Username not found 						FORMAT: "Rep112"
	 * +Wrong Password 							FORMAT: "Rep113"
	 * +Insufficient permissions 				FORMAT: "Rep115"
	 * 
	 * 1-b. registration replies
	 * +Registered successfully 				FORMAT: "Rep120"
	 * +Email not Available 					FORMAT: "Rep121"
	 * +Email already exists 					FORMAT: "Rep122"
	 * +Username already exists 				FORMAT: "Rep123"
	 * 
	 * 1-c. verification replies
	 * +Verification successful 				FORMAT: "Rep130"
	 * +Wrong verificationCode 					FORMAT: "Rep131"
	 * +User already verified 					FORMAT: "Rep132"
	 * 
	 * 1-d. logout
	 * +logout successful 						FORMAT: "Rep140"
	 * 
	 * 1-e. get all info
	 * +not logged in 							FORMAT: "Rep115"
	 * +Info gotten  							FORMAT: "Rep150:username,email,firstname,lastname"
	 * 
	 * 1-f. delete account
	 * +Account deleted successfully  			FORMAT: "Rep160"
	 * +Not enough permissions 					FORMAT: "Rep115"
	 * 
	 * 1-g. resend verification code
	 * +verification code resent  				FORMAT: "Rep170"
	 * +Email is already verified  				FORMAT: "Rep115"
	 * +Email not Available 					FORMAT: "Rep121"
	 * 
	 * 
	 * 2-a. reserve
	 * /b. Unreserve
	 * +Identity error 							FORMAT: "Rep115"
	 * 	(user not logged or 
	 * 			insufficient permissions)
	 * +Room status changed 					FORMAT: "Rep210"
	 * +Room already reserved 					FORMAT: "Rep211"
	 * +Room reservation time Invalid 			FORMAT: "Rep212"
	 * +Invalid date format 					FORMAT: "Rep231"
	 * +Room rescheduling failed 				FORMAT: "Rep241"
	 */
	public String handleRequest(String request) throws Exception {
		lastSeen = date.getTime();
		String def = "Rep000";
		if (request == null)
			return def;

		// HANDLE IDENTIFICATIONS:
		if (request.length() < 6 || !request.subSequence(0, 3).equals("Req"))
			return def;
		String RCode = (String) request.subSequence(3, 6);
		if ("110".equals(RCode)) // login
		{
			if (request.length() < 8)
				return def;
			String[] parser = request.split(":");
			if (parser.length != 2)
				return def;
			String[] Cred = parser[1].split(",");
			if (Cred.length != 2)
				return def;
			return "Rep" + Login(Cred[0], Cred[1]).ID;
		}
		if ("120".equals(RCode)) // register
		{
			System.out.println("Got here");
			if (request.length() < 8)
				return def;
			String[] parser = request.split(":");
			if (parser.length != 2)
				return def;
			String[] Cred = parser[1].split(",");
			if (Cred.length != 5)
				return def;
			return "Rep" + Register(Cred[0], Cred[1], Cred[2], Cred[3], Cred[4]).ID;
		}
		if ("130".equals(RCode)) // verify email
		{
			if (request.length() < 8)
				return def;
			String[] parser = request.split(":");
			if (parser.length != 2)
				return def;
			String[] Cred = parser[1].split(",");
			if (Cred.length != 2)
				return def;
			return "Rep" + VerifyEmail(Cred[0], Cred[1]).ID;
		}
		if ("140".equals(RCode)) // logout
		{
			return "Rep" + Logout().ID;
		}
		if ("150".equals(RCode)) // Get all User info
		{
			if (accountType.equals(UserTypeCodes.NotLoggedIn))
				return "Rep" + IdentificationCodes.InsufficientPermissions.ID;
			String[] info = getUserInfo();
			return String.format("Rep150:%s,%s,%s,%s", info[0], info[1], info[2], info[3]);
		}
		if ("160".equals(RCode)) // Delete Account
		{
			String[] parser = request.split(":");
			if (parser.length != 2)
				return def;
			return "Rep" + DeleteAccount(parser[1]).ID;
		}
		if ("170".equals(RCode)) // Resend verification codes
		{
			String[] parser = request.split(":");
			if (parser.length != 2)
				return def;
			return "Rep" + ResendVerificationCode(parser[1]).ID;
		}

		// HANDLE RESERVATIONS:
		String[] parser = request.split(":", 2);
		if (parser.length != 2)
			return def;
		String[] Param = parser[1].split(",");
		if (Param.length != 2)
			return def;

		// TODO: HANDLE TIMES
		String[] timeRange = Param[1].split("\\^");
		String RoomID = Param[0];

		if ("210".equals(RCode) && timeRange.length == 2) // Reserve
			return "Rep" + Reserve(RoomID, timeRange[0], timeRange[1]).ID;
		if ("220".equals(RCode) && timeRange.length == 1) // Unreserve
			return "Rep" + unReserve(RoomID, timeRange[0]).ID;
		if ("240".equals(RCode) && timeRange.length == 3) // Reschedule
			return "Rep" + Reschedule(RoomID, timeRange[0], timeRange[1], timeRange[2]).ID;

		return def;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null)
			return false;
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * checks if the roomID is within format
	 * checks if the solar system is a valid
	 * checks if the Planet is valid
	 * checks if the Hotel is valid
	 * checks if the room is within range of rooms
	 * 
	 * @param roomID
	 * @return appropriate ReservationCode
	 */
	public ReservationCodes validateRoomID(String roomID) {
		if (roomID.length() != 16 || !isNumeric((String) roomID.subSequence(12, 15)))
			return ReservationCodes.RoomIDInvalid;

		//check with the database for everything.
		db.ValidateRoom(roomID);
		return ReservationCodes.RoomFoundSuccessfully;
	}

}
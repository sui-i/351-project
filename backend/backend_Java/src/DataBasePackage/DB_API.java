package DataBasePackage;
import Security.md5;


import roomsPackage.R_InformationDB;
import requestsrepliescodes.IdentificationCodes; 
import requestsrepliescodes.ReservationCodes;
import requestsrepliescodes.UserTypeCodes;
import requestsrepliescodes.ValidateSynthax;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Prerequistes: 
 * <ol>
 * 		<li> Java 8 + running on your machine </li>
 * 		<li> Postgresql server installed and running on your machine (this project uses Postgres 15)</li>
 * 		<li> ADD {@code postgresql-42.5.1.jar} file to project libraries (class path) found in {@code backend/JarFile/postgresql-42.5.1.jar} </li>
 * 		<li> Update {@code 'YourDataBase'} in {@code DB_URL} to your DataBase name  </li>
 * 		<li> Run the {@code CreateTables.sql} file found in {@code /DataBase/CreateTables.sql}</li>
 * 		<li> Enjoy our application :) </li>
 * </ol>
 */

//checkMembershipEmail(String)

public class DB_API {
	
	private Connection conn;
	boolean connectionInitiliazed=false;
	/**
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * --------------------------------
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
	private static HashMap<String,String> TableNames;
	private boolean created=false;
	private static final String JDBC_DRIVER ="org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost/'YourDataBase'";
    private static final String USER = "postgres";
    private static final String PASS ="YourPassword";
    private static HashMap<String,DB_UserInformation> users;
	private static HashMap<String,R_InformationDB> rooms;
	private static HashMap<String,String> IDCodes;
    
	public DB_API() {
		if(!created) {
			created = true;
			TableNames = new HashMap<>();
			TableNames.put("Credentials","users_credentials");TableNames.put("Info","users_info");
			TableNames.put("Reservation","users_reservation_history");
			TableNames.put("Rooms","room_info");TableNames.put("RoomsHistory","room_reservation_history");
		}
		
	}
	

	
	/**
	 * Must be used for each thread
	 * TO-DO: Thread locks
	 * Instantiate the connection conn with the database;
	 * @return  <ul> 
	 * 				<li> True if connection is  successful</li>
	 * 				<li> False if otherwise </li>
	 * 			</ul>
	 */
	
	public  boolean ConnectDB() {
		try {
			Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");
            
            conn = DriverManager.getConnection(DB_URL  , USER, PASS);
            return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * Extracts the information of the user from the database;
	 * Table Used: users_info
	 * Query Used: Select username,firstName,lastName,phoneNumber,birthDate,Location from users_info where username = ....;
	 * @return  <ul> 
	 * 				<li> {@code DB_UserInformation} instance if user is Found</li>
	 * 				<li> Null if otherwise </li>
	 * 			</ul>
	 */
	 public  DB_UserInformation getUserInfo(String username) {
		
		//Assumption
		
		if(! checkMembershipUserName(username)) {
			//UserNameNotFoundException
			return null;
		}
		
		try {
			if(ConnectDB()) {
				String C_firstName = "",C_lastName= "",C_phoneNumber= "",C_birthDate="",C_Location="";
				String query = String.format("Select username,firstName,lastName,phoneNumber,birthDate,Location from %s where username = '%s' ;",TableNames.get("Info"),username);
				ArrayList<HashMap<String,String>> results= extractQuery(query, new String[] {"username","first_name","last_name","phone_number","birthdate","location"});
				
				if(results.size()==1){
					C_firstName=results.get(0).get("first_name");
					C_lastName=results.get(0).get("last_name");
					C_phoneNumber=results.get(0).get("phone_number");
					C_birthDate=results.get(0).get("birthdate");
					C_Location=results.get(0).get("location");
				}
				else{
					return null;
				}

		        return new DB_UserInformation(username,C_firstName,C_lastName,C_phoneNumber,C_birthDate,C_Location);
			}
			else {
				// TO-DO : return special instance of the DB_UserInformation
				
				return null;
			}
		}
		
		catch (Exception e) {
			// TO-DO : return special instance of the DB_UserInformation
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	/**
	 * Query : Select FirstName from {table} where username is {username} ;
	 * @param username : username 
	 * @return  FirstName From DataBase
	 */
	
	 public String getFirstName(String username) 
	
	{
		if(users.containsKey(username)) {
			return users.get(username).getfirstName();
		}
		DB_UserInformation user=getUserInfo(username);
		if(user!=null){ 
			users.put(username,user);
			return user.getfirstName();

		}
		return null;
		//returns FirstName From DataBase
		
	}


	/**
	 * Suggestion : merging the two getLastName and getFirstName into one (only one query is needed)
	 * Query : Select LastName from {table} where username is {username} ;
	 * @param username : username 
	 * @return  LatName From DataBase
	 */
	 public String getLastName(String username)
	{
		if(users.containsKey(username)) {
			return users.get(username).getlastName();
		}
		// Throwing Exception
		DB_UserInformation user=getUserInfo(username);
		if(user!=null){ 
			users.put(username,user);
			return user.getlastName();

		}
		return null;
	}
	
	/**
	 * Checks if username exists in the database;
	 * Table Used: users_credentials
	 * <br>
	 * Query Used: <br>
	 * 		Select username from userscredentials where username = {@code username} ;
	 * @param username : 
	 * @return  <ul> 
	 * 				<li> True if username exists</li>
	 * 				<li> False if no such username exist </li>
	 * 			</ul>
	 * 
	 * 
	 */
	 public boolean checkMembershipUserName(String username)
		{
		
			assert conn !=null : "No connection mate";
			try {
				String query = String.format("Select username from userscredentials where username = '%s' ;",username);
				ArrayList<HashMap<String,String>> results= extractQuery(query, new String [] {"username"});
	
				if(results.size()==0 || results.size()>1) return false;
				else
					return true;
			}
			
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			//return true;
			//returns if username has an account
		}
	
	/**
	 * Checks if email exists in the database;
	 * Table Used: users_credentials
	 * <br>
	 * Query Used: <br>
	 * 		Select email from userscredentials where email = {@code username} ;
	 * @param email : 
	 * @return  <ul> 
	 * 				<li> True if email exists</li>
	 * 				<li> False if no such email exist </li>
	 * 			</ul>
	 * 
	 * 
	 */
	public boolean checkMembershipEmail(String email){
		assert conn !=null : "No connection mate";
		try {
			String query = String.format("Select email from userscredentials where username = '%s' ;",email);
			ArrayList<HashMap<String,String>> results= extractQuery(query, new String [] {"username"});

			if(results.size()==0 || results.size()>1) return false;
			else
				return true;
		}
		
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	return false;
        }
		//return true;
		//returns if username has an account

	}
	
	
	/**
	 * Checks if the credentials of the username are valid in the database;
	 * Table Used: users_credentials
	 * <br>
	 * Query Used: <br>
	 * &nbsp &nbsp		SELECT username,password,email from {@code users_credentials} where username = {@code username};
	 * @param username : username 
	 * @param  password : password
	 * @return  <ul> 
	 * 				<li> {@code IdentificationCodes.LoginSuccessful } if credentials were correct </li>
	 * 				<li> {@code IdentificationCodes.UsernameNotFound} (self explanotory).</li>
	 * 				<li> {@code IdentificationCodes.UsernameAlreadyExists }  </li>
	 * 				<li> {@code IdentificationCodes.InternalError}  </li>
	 * 				<li> {@code IdentificationCodes.EmailNotVerified} </li>
	 *  			<li> {@code IdentificationCodes.WrongPassword}  </li>
	 * 			</ul>
	 */

	 //IdentificationCodes
	 public  IdentificationCodes checkLoginCredentials(String username, String password,String email)
	{
		
		
		if(!ValidateSynthax.checkEmail(email)) return IdentificationCodes.EmailNotVerified;
		try {
			assert conn !=null : "No connection mate";
			String query = String.format("Select username,password,email from %s where username = '%s' ;",TableNames.get("credentials"),username);
			ArrayList<HashMap<String,String>> results= extractQuery(query,new String [] {"username","password","email"});
			if(results.size()==0){
				return IdentificationCodes.UsernameNotFound;
			}
			if(results.size()>1 ){
				//Report replicated usernames
				return IdentificationCodes.UsernameAlreadyExists;
			}

			else{
        	String c_password=results.get(0).get("password");
        	String c_email= results.get(0).get("email");
        	String passwordHash= md5.getMd5(password);
			if(! c_email.equals(email)) return IdentificationCodes.EmailNotVerified;
			if(! c_password.equals(passwordHash)) return IdentificationCodes.WrongPassword;
			return IdentificationCodes.LoginSuccessful;
			}

        	
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return IdentificationCodes.InternalError;
        }
        
		
	}

	
	/**
	 * Query : Insert INTO userscredentials (username,password,email,date_of_creation,last_login) VALUES () ;
	 * @param username : username 
	 * @param  password : password
	 * @param email : email
	 * @return  <ul> 
	 * 				<li> {@code RegistrationSuccessul} if user was successfully added</li>
	 * 				<li> {@code UsernameAlreadyExists} if user already exists </li>
	 * 				<li> {@code InternalError } if some other error occurs </li>
	 * 			</ul>
	 * 
	 */
	 public  IdentificationCodes RegisterUser(String username, String email, String password, String FirstName,String LastName,String VerificationCode)
		{
			
			try {
				assert conn != null : "No Connection Mate";
				if(checkMembershipUserName(username)==true) {
					return IdentificationCodes.UsernameAlreadyExists;
				}
				else {
					//TO-DO: Implement the codes
					String time= "NOW()";
					String lastLogin = "NOW()";
					password= md5.getMd5(password);
					String query = String.format("Insert INTO %s (username,password,email,date_of_creation,last_login,userType,verification_code) VALUES('%s','%s','%s',%s,%s,%s,'%s') ;",TableNames.get("credentials"),username,password,email,time,lastLogin,UserTypeCodes.NonVerifiedUser,VerificationCode);  
					String query2= String.format("INSERT INTO users_info (username,first_name,last_name) VALUES ('%s','%s','%s');",username,FirstName,LastName);
					if (!insertQuery(query+query2)) return IdentificationCodes.RegistrationSuccessul;
					return IdentificationCodes.InternalError;
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return IdentificationCodes.InternalError;
			}
		}

	
	/**
	 * TO-DO: IMPLEMENT ADMIN, VERIFIED USER, and NON VERIFIED USER
	 * Query : Insert INTO userscredentials (username,password,email,date_of_creation,last_login) VALUES () ;
	 * @param username : username 
	 * @param  password : password
	 * @param email : email
	 * @return  <ul> 
	 * 				<li> 0 if user was successfully added</li>
	 * 				<li> 1 if user already exists </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */
	public  IdentificationCodes RegisterAdmin(String username, String email, String password)
	{
		
		try {
			assert conn != null : "No Connection Mate";
			if(checkMembershipUserName(username)==true) {
				return IdentificationCodes.UsernameAlreadyExists;
			}
			else {
				//TO-DO: Implement the codes
				String time= "NOW()";
				String lastLogin = "NOW()";
				password= md5.getMd5(password);
				String query = String.format("Insert INTO %s (username,password,email,date_of_creation,last_login,userType) VALUES('%s','%s','%s',%s,%s,%s,'%s') ;",TableNames.get("credentials"),username,password,email,time,lastLogin,UserTypeCodes.Admin);  
				String query2= String.format("INSERT INTO users_info (username,first_name,last_name) VALUES ('%s','%s','%s');",username,"Admin","Admin");
				if (!insertQuery(query+query2)) return IdentificationCodes.RegistrationSuccessul;
				return IdentificationCodes.InternalError;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return IdentificationCodes.InternalError;
		}
	}
	

	/**
	 *  TO-DO: IMPLEMENT ADMIN, VERIFIED USER, and NON VERIFIED USER
	 * 
	 * Query : Insert INTO userscredentials (username,password,email,date_of_creation,last_login) VALUES () ;
	 * @param username : username 
	 * @param  password : password
	 * @param email : email
	 * @return  <ul> 
	 * 				<li> 0 if user was successfully added</li>
	 * 				<li> 1 if user already exists </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 * 
	 */
	public  IdentificationCodes VerifyRegistration(String username,String code ){
		//Do-Something: A counter you can try the code up to three times 
		if(IDCodes.get(username)!= null && IDCodes.get(username).equals(code) ){

		}
		// Return INVALID CODE - TRY AGAIN
		return IdentificationCodes.InternalError;
	}
	

	/**
	 * NOT FINISHED : HANDLING BOOLEAN SHIT
	 * Query : "Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;
	 * @param ID : ID of the room 
	 * @return  <ul> 
	 * 				<li> {@code ReservationCodes.RoomAvailable} if available</li>
	 * 				<li> 1 if the times aren't available </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */

	public  ReservationCodes checkRoomAvailability(String RoomID,String BookIn,String BookOut) {
		assert conn !=null : "No connection mate";
		try {
			if(!ValidateSynthax.checkTime(BookIn) || !ValidateSynthax.checkTime(BookOut)) return ReservationCodes.InvalidDateFormat;

			String query="";
			ArrayList<HashMap<String,String>> results= new ArrayList<>();
			
			TimeStamp usersDate= new TimeStamp(BookIn);
			TimeStamp booked_until = new TimeStamp(BookOut);

        	if(usersDate.compareTo(booked_until)<=0 ){
        		//Note Valid Date Bro
				return ReservationCodes.RoomReservationtimeInvalid;
			}

			else{
				query = String.format("SELECT count(*) FROM room_reservation_history where NOT((booked_in <= '%s')  and  (booked_out >= '%s')) AND cancelled = false ; ",booked_until.toString(),usersDate.toString());
				results = extractQuery(query, new String [] {"count"});
				if(results.size()==1){
					if(results.get(0).get("count")!=null){
						if(Integer.parseInt(results.get(0).get("count"))==0) return ReservationCodes.RoomAvailable ; // return 0;
						else return ReservationCodes.RoomAlreadyReserved;	
					}
					else return ReservationCodes.InternalError;
					
				}
				return ReservationCodes.InternalError;
			}
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return ReservationCodes.InternalError;
        }
}

	/**
	 * Query : "Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;
	 * @param ID : ID of the room 
	 * @return  <ul> 
	 * 				<li> 0 if reservation was successfully</li>
	 * 				<li> 1 if overlap observed </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */

	//TO-DO: Finish it 
	public  ReservationCodes Reserve(String username,String RoomID,String BookIn,String BookOut) {
		assert conn !=null : "No connection mate";
		try {
			if(!ValidateSynthax.checkTime(BookIn) || !ValidateSynthax.checkTime(BookOut)) return ReservationCodes.InvalidDateFormat;
			ReservationCodes available= checkRoomAvailability(RoomID,BookIn,BookOut);
			if(available.equals(ReservationCodes.RoomAvailable)){
				String query=String.format("Select reservation_id from room_reservation_history  ORDER BY reservation_id desc limit 1; ");
				 
				ArrayList<HashMap<String,String>> results= extractQuery(query, new String [] {"reservation_id"});
				int newId;
				if(results.size()==0){
					newId=1;

				}
				else{
					if(results.get(0).get("reservation_id")!=null){
						newId= Integer.parseInt(results.get(0).get("reservation_id"));
					}

					else return ReservationCodes.InternalError;
				}
			
			TimeStamp Book_In = new TimeStamp(BookIn);
			TimeStamp Book_Out = new TimeStamp(BookOut);
			//Note here we are inserting two seperate records in room_reservation_history and users_reservation_history
			query = String.format("INSERT INTO room_reservation_history (reservation_id,room_id,booked_in,booked_until,cancelled) values (%s,'%s','%s','%s',false) ; ",newId,RoomID,Book_In.toString(),Book_Out.toString());
			if(! insertQuery(query)) return ReservationCodes.RoomNotReserved;
			//MEOW: TO DO : 

			query = String.format("INSERT INTO users_reservation_history (username,room_id,reservation_id,reservation_date,check_in,check_out,cancelled) values ('%s','%s',%s,NOW(),%s,'%s',null) ; ",username,RoomID,newId,Book_In.toString(),Book_Out.toString());

			if(! insertQuery(query)) return ReservationCodes.RoomNotReserved;
			return ReservationCodes.RoomStatusChangedSuccessfully;


			}
			return available;
			
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return ReservationCodes.InternalError;
        }
}
	/**
	 * Table:  room_info
	 * Query : "Select * from {@code room_info} where RoomId = {@code ID} ;
	 * @param ID : ID of the room 
	 * @return  <ul> 
	 * 				<li> 0 if the room is validated</li>
	 * 				<li> 1 if user already exists </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 				<li> 3 if some other error occurs </li>
	 * 				<li> 4 if some other error occurs </li>	
	 * 				<li> 5 if some other error occurs </li>
	 * 			</ul>
	 */
	public ReservationCodes ValidateRoom(String RoomID)
	{

	assert conn !=null : "No connection mate";
	if(RoomID.length()!=16){
		return ReservationCodes.RoomIDInvalid;
	}
	try {
		HashMap<String,ReservationCodes> Errors = new HashMap<>();
		
		String s="room_type|num_of_beds|floor|price_per_night|planet_name|solar_system_name|hotel_name";
		String [] fields=  s.split("|");
		//String [] fields=  new String [] {"num_of_beds","floor","price_per_night","booked_until","solar_system","planet","hotel","room_type"};
		ReservationCodes [] errors = new ReservationCodes [] {ReservationCodes.IndentityError,ReservationCodes.IndentityError,ReservationCodes.IndentityError,ReservationCodes.IndentityError,ReservationCodes.PlanetNotFound,ReservationCodes.SolarSystemNotFound,
		ReservationCodes.HotelNotFound};
		for(int i=0; i<fields.length;i++){
			Errors.put(fields[i],errors[i]);
		}
		//String query = String.format("Select * from %s where room_id = '%s' ;",TableNames.get("Rooms"),RoomID);
		String query= String.format(
					"""
					SELECT room_info.* , planet_info.simple_name as "planet_name", solar_system_info.simple_name as "solar_system_name" ,hotel_info.simple_name as "hotel_name" from room_info 
					INNER JOIN planet_info on SUBSTRING('%s' from 5 for 4)=planet_info.simple_id
					INNER JOIN solar_system_info  on SUBSTRING('%s' from 1 for 4)=solar_system_info.simple_id
					INNER JOIN hotel_info  on SUBSTRING('%s' from 9 for 4)=hotel_info.simple_id;
					""",RoomID
				);
		ArrayList<HashMap<String,String>> results= extractQuery(query, fields);

		
		if(results.size()==0){
			return ReservationCodes.RoomIDInvalid;
		}
		else if (results.size()>1){
			//Duplicated id Numbers
			return ReservationCodes.IndentityError;
		}

		
		for(String field : fields){
			if(results.get(0).get(field)==null){
				return Errors.get(field);
			}				
		}
		//Query for extracting the information
		
		
		R_InformationDB roomInformation = new R_InformationDB.Builder(RoomID, results.get(0).get("booked_until" )).Planet(results.get(0).get("planet_name" )).Hotel(
				results.get(0).get("hotel_name" )).SolarSystem(results.get(0).get("solar_system_name" )).NumOfBeds(Integer.parseInt(results.get(0).get("num_of_beds" ))).PricePerNight(
				Double.parseDouble(results.get(0).get("price_per_night" ))).Floor(Integer.parseInt(results.get(0).get("floor" ))).build();
		//Add RoomInfo to static HashMapCache for the rooms info

		rooms.put(RoomID,roomInformation);
		return ReservationCodes.RoomFoundSuccessfully;
		
		
	
	}

	catch(Exception e) {
		System.out.println(e.getMessage());
		return ReservationCodes.InternalError;
	}
	//checks if the roomID is within format returns 1
	//checks if the solar system is a valid returns 2
	//checks if the Planet is valid returns 3
	//checks if the Hotel is valid returns 4
	//checks if the room is within range of rooms 5
	//if all is well return 0
	}
	
	
	/**
	 * TO-DO: Update on previous queries
	 * Executes an update query that sets cancelled field in the table to the date of cancellation
	 *  <br>
	 * The value of invalid columns will be null <br>
	 * // Question: Exception Handling
	 * @param username : String 
	 * @param RoomID : String
	 * @param ReservationDate : String of format "YYYY-MM-DD HH:MM:SS.MSMSMS"
	 * @return <ul>
	 * 				<li> {@code ReservationCodes.RoomStatusChangedSuccessfully} if cancellation was successful </li>
	 * 				<li> {@code ReservationCodes.RoomRechedulingFailed} if not</li>
	 * 				<li> {@code ReservationCodes.InternalError} if duplicate RoomID obtained</li>
	 * 				<li> {@code ReservationCodes.InternalError} if some error occured </li>
	 * 		   </ul>
	 *
	 */
	public ReservationCodes CancelReservation(String username, String RoomID,String ReservationDate) {
		assert conn != null : "No Connection mate";
		if(checkMembershipUserName(username)){
			try{
				if(!ValidateSynthax.checkTime(ReservationDate)) return ReservationCodes.InvalidDateFormat;
				
				String reservation_id="";
				String query = String.format("SELECT * from users_reservation_history where username= '%s' AND room_id= %s AND reservation_date=%s ; ", username, RoomID,ReservationDate);
				ArrayList<HashMap<String,String>> results= extractQuery(query, new String [] {"reservation_id"});
				if(results.size()==1){
					reservation_id= results.get(0).get("reservation_id");
					if(reservation_id==null) return ReservationCodes.RoomRechedulingFailed;
				}
				else{
					return ReservationCodes.RoomRechedulingFailed;
				}
				String query1=String.format("UPDATE users_reservation_history SET cancelled=NOW() WHERE reservation_id=%s;", reservation_id);
				String query2=String.format("UPDATE room_reservation_history SET cancelled=true WHERE reservation_id=%s;", reservation_id);
				if(! insertQuery(query1)) return ReservationCodes.RoomRechedulingFailed;
				if(! insertQuery(query2)) return ReservationCodes.RoomRechedulingFailed;
				return ReservationCodes.RoomStatusChangedSuccessfully;
			}

			catch(Exception e){
				e.printStackTrace();
				return ReservationCodes.InternalError;
			}
			
		}
		else{
			return ReservationCodes.RoomReservationInvalid;
		}
	
	}

	/**
	 * Executes a get query and extracts the information from it into ArrayList of {@code Hashmap<String,String>}
	 *  <br>
	 * The value of invalid columns will be null <br>
	 * // Question: Exception Handling
	 * @param query : String (Must be SQL query)
	 * @param keys : String [] Column names of the table.
	 * @return <ul>
	 * 				<li> </li>
	 * 				<li> </li>
	 * 		   </ul>
	 */
	public ArrayList<HashMap<String,String>> extractQuery(String query, String [] keys ){
		assert conn!=null : "No connection mate";
		try{
			Statement stmt= conn.createStatement();
			ResultSet rs= stmt.executeQuery(query);
			ArrayList<HashMap<String,String>> results= new ArrayList<>();
			while(rs.next()){
				HashMap<String,String> result= new HashMap<>();
				for(String key : keys){
					try{
						result.put(key,rs.getString(key));
					}
					catch(Exception e){
						result.put(key,null);
					}		
				}
				results.add(result);
			

			}
			stmt.close();
			return results;
		}
		catch(Exception e){
			e.printStackTrace();
			return new ArrayList<>();
		}
		
	}

	/**
	 * Executes an insert query and extract the information from it
	 * // Question: Exception Handling
	 * @param query
	 * @param keys
	 * @return
	 */
	public boolean insertQuery(String query){
		assert conn!=null : "No connection mate";
		try{
			Statement stmt= conn.createStatement();
			int result=stmt.executeUpdate(query);
			stmt.close();
			if(result==0) return false;
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * TO-DO: Scrape the info of the rooms also
	 * Returns reservation history of the user ;
	 * @param username
	 * @return 
	 */
	public ArrayList<HashMap<String,String>> getReservationHistoryUser(String username){
		if(!checkMembershipUserName(username)) return null;
		String query1= String.format(
			"""
			SELECT users_reservation_history.* ,  solar_system_info.simple_name as "solar_system_name" , planet_info.simple_name as "planet_name", hotel_info.simple_name as "hotel_name" from users_reservation_history 
			INNER JOIN room_info on users_reservation_history.room_id=room_info.room_id
			INNER JOIN planet_info on SUBSTRING(room_info.room_id from 5 for 4)=planet_info.simple_id
			INNER JOIN solar_system_info  on SUBSTRING(room_info.room_id from 1 for 4)=solar_system_info.simple_id
			INNER JOIN hotel_info  on SUBSTRING(room_info.room_id from 9 for 4)=hotel_info.simple_id 
			WHERE username='%s';
				""",username
		);
		//String query1 = String.format("Select * from user_reservation_history where username='%s';", username);
		//String query2 = String.format("Select * from user_reservation_history where username='%s';", username);
		String fields="room_id|reservation_date|check_in|check_out|cancelled|solar_system_name|planet_name|hotel_name";
		String [] fields1= fields.split("|");
		//String [] fields1=  new String [] {"room_id","reservation_date","check_in","check_out","cancelled"};
		//String [] fields2=  new String [] {"num_of_beds","floor","price_per_night","booked_until","solar_system","planet","hotel","room_type"};
		ArrayList<HashMap<String,String>> results= extractQuery(query1,fields1);
		return results;

	}

}

package DataBasePackage;
import Security.md5;
import cmdClientPackage.C_InformationDB;

import roomsPackage.R_InformationDB;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
// Prerequiste: ADD .jar file to project libraries

public class DB_API {
	
	private Connection conn;
	boolean connectionInitiliazed=false;
	/**
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
	public static HashMap<String,String> TableNames;
	static boolean created=false;
	static final String JDBC_DRIVER ="org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/Hostellar";
    static final String USER = "postgres";
    static final String PASS ="YourPassword";
    private static HashMap<String,C_InformationDB> users;
    
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
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * --------------------------------
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
	/**
	 * Extracts the information of the user from the database;
	 * Table Used: users_info
	 * Query Used: Select username,firstName,lastName,phoneNumber,birthDate,Location from users_info where username = ....;
	 * @return  <ul> 
	 * 				<li> {@code C_InformationDB} instance if user is Found</li>
	 * 				<li> Null if otherwise </li>
	 * 			</ul>
	 */
	 public  C_InformationDB getUserInfo(String username) {
		
		//Assumption
		
		if(! checkMembershipUserName(username)) {
			//UserNameNotFoundException
			return null;
		}
		
		try {
			if(ConnectDB()) {
				String C_firstName = "",C_lastName= "",C_phoneNumber= "",C_birthDate="",C_Location="";
				Statement stmt= conn.createStatement();
				String query = String.format("Select username,firstName,lastName,phoneNumber,birthDate,Location from %s where username = '%s' ;",TableNames.get("Info"),username);
		        ResultSet rs= stmt.executeQuery(query);
		       
		        while(rs.next()) {
		        	//Extracting data
		        	String c_username = rs.getString("username");
		        	
		        	if(c_username.equals(username) ) {
		        		C_firstName= rs.getString("first_name");
		        		C_lastName = rs.getString("last_name");
		        		C_phoneNumber = rs.getString("phone_number");
		        		C_birthDate = rs.getString("birthdate");
		        		C_Location = rs.getString("location");
		        	}
	        	
		        }
		        
		        stmt.close();
		        return new C_InformationDB(username,C_firstName,C_lastName,C_phoneNumber,C_birthDate,C_Location);
			}
			else {
				// TO-DO : return special instance of the C_InformationDB
				
				return null;
			}
		}
		
		catch (Exception e) {
			// TO-DO : return special instance of the C_InformationDB
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
		// Throwing Exception
		return "";
		//returns FirstName From DataBase
		
	}
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

	/**
	 * Suggestion : merging the two getLastName and getFirstName into one (only one query is needed)
	 * Query : Select LastName from {table} where username is {username} ;
	 * @param username : username 
	 * @return  LatName From DataBase
	 */
	 public static String getLastName(String username)
	{
		if(users.containsKey(username)) {
			return users.get(username).getlastName();
		}
		// Throwing Exception
		return "";
		//returns LastName From DataBase
	}
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
			Statement stmt= conn.createStatement();
			String query = String.format("Select username from userscredentials where username = '%s' ;",username);
	        ResultSet rs= stmt.executeQuery(query);
	       
	        while(rs.next()) {
	        	//Extracting data
	        	String c_username = rs.getString("username");
	        	
	        	if(c_username.equals(username) ) {
	        		return true;
	        	}
        	
	        }
	        stmt.close();
	        return false;
	        
	        
	       
		}
		
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	return false;
        }
		//return true;
		//returns if username has an account
		}
	
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
	/**
	 * Checks if the credentials of the username are valid in the database;
	 * Table Used: users_credentials
	 * <br>
	 * Query Used: <br>
	 * &nbsp &nbsp		SELECT username,password,email from {@code users_credentials} where username = {@code username};
	 * @param username : username 
	 * @param  password : password
	 * @return  <ul> 
	 * 				<li> 0 if username and password are valid, and email is verified.</li>
	 * 				<li> 1 if they are invalid </li>
	 * 				<li> 2 if they are valid but email is not verified </li>
	 * 			</ul>
	 */
	 public  int checkLoginCredentials(String username, String password,String email)
	{
		
		assert conn !=null : "No connection mate";
		try {
			Statement stmt= conn.createStatement();
			String query = String.format("Select username,password,email from %s where username = '%s' ;",TableNames.get("credentials"),username);
	        ResultSet rs= stmt.executeQuery(query);
	       
	        	//Extracting data
        	String c_username = rs.getString("username");
        	String c_password= rs.getString("password");
        	String c_email= rs.getString("email");
        	String passwordHash= md5.getMd5(password);
        	if(c_username.equals(username)&& c_password.equals(passwordHash) && c_email.equals(email) ) {
        		return 0;
        	}
        	else if (c_username.equals(username)&& c_password.equals(passwordHash)) {
        		return 2;
        	}
        	else {
        	
        		return 1;
        	}
	        
	       
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return -1;
        }
        
		//returns 0 if username and password are valid, and email is verified.
		//returns 1 if they are invalid
		//returns 2 if they are valid but email is not verified
		//returns -1 if they was an error
	}

	/**
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
	/**
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
	 public  int RegisterUser(String username, String email, String password)
		{
			try {
				if(checkMembershipUserName(username)==true) {
					return 1;
				}
				else {
					Statement stmt = conn.createStatement();
					//TO_DO:
					// Hash the password bro
					String time= "NOW()";
					String lastLogin = "NOW()";
					password= md5.getMd5(password);
					String query = String.format("Insert INTO %s (username,password,email,date_of_creation,last_login) VALUES('%s','%s','%s',%s,%s) ;",TableNames.get("credentials"),username,password,email,time,lastLogin);  
					
					//System.out.println(query);
					stmt.execute(query );
					
					return 0;
					
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return 2;
			}
			
			//adds a new user
			//returns 0 if user was successfully added
			//returns 1 if user already exists
			//returns 2 if some other error occurs
		}
	/**
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
	/**
	 * Query : "Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;
	 * @param ID : ID of the room 
	 * @return  <ul> 
	 * 				<li> 0 if user was successfully added</li>
	 * 				<li> 1 if user already exists </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */

	//TO-DO: Finish it 
	public  int checkRoomAvailability(int RoomID,String BookIn,String BookOut) {
		assert conn !=null : "No connection mate";
		try {
			Statement stmt= conn.createStatement();
			String query = String.format("Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;",TableNames.get("Rooms"),RoomID);
	        ResultSet rs= stmt.executeQuery(query);
	       
	        	//Extracting data
        	Boolean availability = rs.getBoolean("Available");
			String time = rs.getString("booked_until");
			TimeStamp usersDate= new TimeStamp(date);
			TimeStamp booked_until = new TimeStamp(time);
        	if(!availability && usersDate.compareTo(booked_until)<=0 ){
        		// Can't book this shit
				return 6;
			}

			else{
				//"Insert INTO %s () VALUES ()"
				//tables.get(RoomsHistory)
				query = String.format("Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;",TableNames.get("Rooms"),RoomID);
				
				
				// Redesign the dataBase
				
				
				return 0;

			}
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return -1;
        }
}

	/**
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
	/**
	 * Query : "Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;
	 * @param ID : ID of the room 
	 * @return  <ul> 
	 * 				<li> 0 if user was successfully added</li>
	 * 				<li> 1 if user already exists </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */

	//TO-DO: Finish it 
	public  int Reserve(String username,int RoomID,String date) {
		assert conn !=null : "No connection mate";
		try {
			Statement stmt= conn.createStatement();
			String query = String.format("Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;",TableNames.get("Rooms"),RoomID);
	        ResultSet rs= stmt.executeQuery(query);
	       
	        	//Extracting data
        	Boolean availability = rs.getBoolean("Available");
			String time = rs.getString("booked_until");
			TimeStamp usersDate= new TimeStamp(date);
			TimeStamp booked_until = new TimeStamp(time);
        	if(!availability && usersDate.compareTo(booked_until)<=0 ){
        		// Can't book this shit
				return 6;
			}

			else{
				//"Insert INTO %s () VALUES ()"
				//tables.get(RoomsHistory)
				query = String.format("Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;",TableNames.get("Rooms"),RoomID);
				
				
				// Redesign the dataBase
				
				
				return 0;

			}
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return -1;
        }
}
	/**
	 * HashMap for the tableNames (static)
	 * Key (String) | Value (tableName)
	 * Credentials  | users_credentials
	 * Info		    | users_info
	 * Reservation  | users_reservation_history
	 * Rooms  	    | room_info
	 * RoomsHistory | room_reservation_history
	 */
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
	 * 
	 * 
	 */
	public int ValidateRoom(int ID)
	{

	assert conn !=null : "No connection mate";
	try {
		Statement stmt= conn.createStatement();
		String query = String.format("Select * from %s where room_id = '%s' ;",TableNames.get("Rooms"),ID);
		ResultSet rs= stmt.executeQuery(query);
		//Check if ID is valid
		if(rs.getFetchSize()==0){
			//No such ID exists
			return -1;

		}
		while(rs.next()) {
			int numberOfBeds= rs.getInt("num_of_beds");
			int floor = rs.getInt("floor");
			double PricePerNight = rs.getDouble("price_per_night");
			String bookedUntil = rs.getString("booked_until");
			R_InformationDB roomInformation = new R_InformationDB.Builder(ID, bookedUntil).NumOfBeds(numberOfBeds).PricePerNight(PricePerNight).Floor(floor).build();
		}
		stmt.close();
		return 0;
		
		
	
	}

	catch(Exception e) {
		System.out.println(e.getMessage());
		return -1;
	}
	//checks if the roomID is within format returns 1
	//checks if the solar system is a valid returns 2
	//checks if the Planet is valid returns 3
	//checks if the Hotel is valid returns 4
	//checks if the room is within range of rooms 5
	//if all is well return 0
	}


}

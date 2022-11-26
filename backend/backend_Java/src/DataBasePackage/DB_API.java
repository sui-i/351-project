package DataBasePackage;
import Security.md5;
import cmdClientPackage.C_InformationDB;

import roomsPackage.R_InformationDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.security.Timestamp;
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
	 * --------------------------------
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
	 * Checks if the credentials of the username are valid in the database;
	 * Table Used: users_credentials
	 * <br>
	 * Query Used: <br>
	 * &nbsp &nbsp		SELECT username,password,email from {@code users_credentials} where username = {@code username};
	 * @param username : username 
	 * @param  password : password
	 * @return  <ul> 
	 * 				<li> -1 if there was an error </li>
	 * 				<li> 0 if username and password are valid, and email is verified.</li>
	 * 				<li> 1 if they are invalid </li>
	 * 				<li> 2 if they are valid but email is not verified </li>
	 * 			</ul>
	 */
	 public  int checkLoginCredentials(String username, String password,String email)
	{
		
		assert conn !=null : "No connection mate";
		try {
			String query = String.format("Select username,password,email from %s where username = '%s' ;",TableNames.get("credentials"),username);
			ArrayList<HashMap<String,String>> results= extractQuery(query,new String [] {"username","password","email"});
			if(results.size()==0 || results.size()>1 ){
				//Report replicated usernames
				return 1;
			}

			else{
			String c_username = results.get(0).get("username");
        	String c_password=results.get(0).get("password");
        	String c_email= results.get(0).get("email");
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

        	
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return -1;
        }
        
		
	}

	
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
	 * NOT FINISHED : HANDLING BOOLEAN SHIT
	 * Query : "Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;
	 * @param ID : ID of the room 
	 * @return  <ul> 
	 * 				<li> 0 if available</li>
	 * 				<li> 1 if the times aren't available </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */

	public  int checkRoomAvailability(int RoomID,String BookIn,String BookOut) {
		assert conn !=null : "No connection mate";
		try {

			String query=String.format("Select booked_until,Available from %s where RoomId = '%s' order by booked_until desc ;",TableNames.get("Rooms"),RoomID);
			ArrayList<HashMap<String,String>> results= extractQuery(query, new String [] {"Available","booked_until"})
			
			if(results.size()==1){

				boolean availability = Boolean.parseBoolean(results.get(0).get("Available"));
				String time= results.get(0).get("booked_until");
			}
			else{
				return 2;
			}
			TimeStamp usersDate= new TimeStamp(BookIn);
			TimeStamp booked_until = new TimeStamp(time);

        	if(!availability && usersDate.compareTo(booked_until)<=0 ){
        		// Can't book this shit
				return 1;
			}

			else{
				query = String.format("SELECT count(*) FROM room_reservation_history where NOT ((booked_in <= '%s')  and  (booked_out >= '%s')) ; ",booked_until.toString(),usersDate.toString());
				results = extractQuery(query, new String [] {"count"});
				if(results.size()==1){
					if(results.get(0).get("count")!=null){
						if(Integer.parseInt(results.get(0).get("count"))==0)return 0;
						else return 1;	
					}
					else return 2;

					
				}
				return 2;
			}
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return 2;
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
	public  int Reserve(String username,int RoomID,String BookIn,String BookOut) {
		assert conn !=null : "No connection mate";
		try {
			int available= checkRoomAvailability(RoomID,BookIn,BookOut);
			if(available==0){
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
					//TO_DO handle exception
					else return 2;
				}
			
			java.sql.Timestamp Book_In = new TimeStamp(BookIn);
			java.sql.Timestamp Book_Out = new TimeStamp(BookOut);
			//Note here we are inserting two seperate records in room_reservation_history and users_reservation_history
			query = String.format("INSERT INTO room_reservation_history (reservation_id,room_id,booked_in,booked_until) values (%s,%s,'%s','%s') ; ",newId,RoomID,Book_In.toString(),Book_Out.toString());
			if(! insertQuery(query)) return 2;
			
			query = String.format("INSERT INTO users_reservation_history (username,room_id,reservation_date,check_in,check_out,cancelled) values ('%s',%s,NOW(),%s,'%s',null) ; ",username,RoomID,RoomID,Book_In.toString(),Book_Out.toString());

			if(! insertQuery(query)) return 2;
			return 0;


			}

			else return 1;
			
		}
		
        catch(Exception e) {
        	System.out.println(e.getStackTrace());
        	return 2;
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
	 * 
	 * 
	 */
	public int ValidateRoom(int RoomID)
	{

	assert conn !=null : "No connection mate";
	try {

		String query = String.format("Select * from %s where room_id = '%s' ;",TableNames.get("Rooms"),RoomID);

		ArrayList<HashMap<String,String>> results= extractQuery(query, new String [] {"num_of_beds","floor","price_per_night","booked_until"});

		
		if(results.size()==0){
			//No such ID exists
			return -1;
		}

		for(int i=0; i<results.size();i++){
			R_InformationDB roomInformation = new R_InformationDB.Builder(RoomID, results.get(0).get("booked_until" )).NumOfBeds(results.get(0).get("num_of_beds" )).PricePerNight(
				results.get(0).get("price_per_night" )).Floor(results.get(0).get("floor" )).build();

		}
		
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
			stmt.executeQuery(query);
			stmt.close();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


}

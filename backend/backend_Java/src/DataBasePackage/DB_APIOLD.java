package DataBasePackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
// Prerequiste: ADD .jar file to project libraries
import java.util.HashMap;

import Security.md5;


public class DB_APIOLD {
	
	/*
	 * TO-DO ConnectFunction (Which connections to database, either one connection or multiple connections depending on number of threads
	 * Ask the professor tomorrow in the class
	 */
	//Connection
	//private static Connection conn ;
	private static Connection conn;
	// Connection 2 for getting user's info
	private static Connection conn1;
	//private Statement stmt;
	static HashMap<String,String> TableNames;
	static boolean created=false;
	static final String JDBC_DRIVER ="org.postgresql.Driver";
	static final String DB_URL_ = "jdbc:postgresql://localhost/";
    static final String DB_URL = "jdbc:postgresql://localhost/Hostellar";
    static final String DB_URL_Info = "jdbc:postgresql://localhost/UsersInfo";
    
    static final String USER = "postgres";
    static final String PASS ="YourPassword";
	private static HashMap<String,Integer> cache;
	
	// Debate whether cache them or no
	private static HashMap<String,DB_UserInformation> users;
	
	// For caching at max 10 userNames.
	
	public DB_APIOLD() {
		if(!created) {
			created = true;
			TableNames = new HashMap<>();
			TableNames.put("Credentials","UsersCredentials");TableNames.put("Info","UsersInfo");
			TableNames.put("Reservation","UsersReservation");
			TableNames.put("Rooms","RoomsInfo");
		}
		
	}
	/**
	 * Instantiate the connection conn with the database;
	 * @return  <ul> 
	 * 				<li> True if connection is  successful</li>
	 * 				<li> False if otherwise </li>
	 * 			</ul>
	 */
	public boolean ConnectDB1() throws Exception{
		try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");
            conn = DriverManager.getConnection(DB_URL_, USER, PASS);

            return true;
            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
		
	}
	public static  boolean ConnectDB2() throws Exception{
		try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");
            conn1 = DriverManager.getConnection(DB_URL_Info , USER, PASS);

            return true;
            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
		
	}
	
	
	/**
	 * Must be used for each thread
	 * TO-DO: Thread locks
	 * Creates a connection conn with the database ;
	 * @return  <ul> 
	 * 				<li> Connection if connection is  successful</li>
	 * 				<li> Null if otherwise </li>
	 * 			</ul>
	 */
	
	public static Connection ConnectDB() {
		try {
			Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");

            /**
             * line disconnected by Ahmad
             * 
             * Connection conn = DriverManager.getConnection(DB_URL_ + TableNames.get(table_name)  , USER, PASS);
             */
            
            return conn;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	 public static DB_UserInformation getUserInfo(String username) {
		
		//Assumption
		
		if(! checkMembership(username)) {
			
			return null;
		}
		
		try {
			if(ConnectDB2()) {
				String C_firstName = "",C_lastName= "",C_phoneNumber= "",C_birthDate="",C_Location="";
				Statement stmt= conn1.createStatement();
				String query = String.format("Select username,firstName,lastName,phoneNumber,birthDate,Location from userscredentials where username = '%s' ;",username);
		        ResultSet rs= stmt.executeQuery(query);
		       
		        while(rs.next()) {
		        	//Extracting data
		        	String c_username = rs.getString("username");
		        	
		        	if(c_username.equals(username) ) {
		        		C_firstName= rs.getString("firstName");
		        		C_lastName = rs.getString("lastName");
		        		C_phoneNumber = rs.getString("phoneNumber");
		        		C_birthDate = rs.getString("birthDate");
		        		C_Location = rs.getString("Location");
		        	}
	        	
		        }
		        
		        stmt.close();
		        return new DB_UserInformation(username,C_firstName,C_lastName,C_phoneNumber,C_birthDate,C_Location);
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
	
	 public static String getFirstName(String username) 
	
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
	 * @param username : 
	 * @return  <ul> 
	 * 				<li> True if username exists</li>
	 * 				<li> False if no such username exist </li>
	 * 			</ul>
	 */
	 public  static boolean checkMembership(String username)
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
	 * Query : Select username, password from  {table} where username is username;
	 * @param username : username 
	 * @param  password : password
	 * @return  <ul> 
	 * 				<li> 0 if username and password are valid, and email is verified.</li>
	 * 				<li> 1 if they are invalid </li>
	 * 				<li> 2 if they are valid but email is not verified </li>
	 * 			</ul>
	 */
	 public  static int checkLoginCredentials(String username, String password,String email)
	{
		
		assert conn !=null : "No connection mate";
		try {
			Statement stmt= conn.createStatement();
			String query = String.format("Select username,password,email from userscredentials where username = '%s' ;",username);
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
	 public  static int RegisterUser(String username, String email, String password)
	{
		try {
			if(checkMembership(username)==true) {
				return 1;
			}
			else {
				Statement stmt = conn.createStatement();
				//TO_DO:
				// Hash the password bro
            	String time= "NOW()";
            	String lastLogin = "NOW()";
            	password= md5.getMd5(password);
            	String query = String.format("Insert INTO userscredentials (username,password,email,date_of_creation,last_login) VALUES('%s','%s','%s',%s,%s) ;",username,password,email,time,lastLogin);  
            	
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
}

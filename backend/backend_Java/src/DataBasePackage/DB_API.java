package DataBasePackage;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
// Prerequiste: ADD .jar file to project libraries
public class DB_API {
	//Connection
	private static Connection conn ;
	//private Statement stmt;
	static final String JDBC_DRIVER ="org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/DatabaseName";

    static final String USER = "postgres";
    static final String PASS ="YourPassword";
	private static HashMap<String,Integer> cache;
	// For caching at max 10 userNames.

	public DB_API(String DB_Name,String USER , String PASS) {
		try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            
        }
        catch(Exception e){
            e.printStackTrace();
        }
		
	}
	public DB_API(String PASS) {
		try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            
        }
        catch(Exception e){
            e.printStackTrace();
        }
		
	}
	public DB_API() {
		try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database ... ");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            
        }
        catch(Exception e){
            e.printStackTrace();
        }
		
	}
	/**
	 * Query : Select FirstName from {table} where username is {username} ;
	 * @param username : username 
	 * @return  FirstName From DataBase
	 */
	
	static public String getFirstName(String username) 
	{
		
		//returns FirstName From DataBase
		
	}

	/**
	 * Suggestion : merging the two getLastName and getFirstName into one (only one query is needed)
	 * Query : Select LastName from {table} where username is {username} ;
	 * @param username : username 
	 * @return  LatName From DataBase
	 */
	static public String getLastName(String username)
	{
		//returns LastName From DataBase
	}
	/**
	 * @param username : 
	 * @return  <ul> 
	 * 				<li> True if username exists</li>
	 * 				<li> False if no such username exist </li>
	 * 			</ul>
	 */
	static public boolean checkMembership(String username)
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
	static public int checkLoginCredentials(String username, String password,String email)
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
        	
        	if(c_username == username && c_password == password && c_email==email ) {
        		return 0;
        	}
        	else if (c_username == username && c_password == password) {
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
	 * Query : Insert Into {table} (username,password) values (username,hash(password));
	 * @param username : username 
	 * @param  password : password
	 * @param email : email
	 * @return  <ul> 
	 * 				<li> 0 if user was successfully added</li>
	 * 				<li> 1 if user already exists </li>
	 * 				<li> 2 if some other error occurs </li>
	 * 			</ul>
	 */
	static public int RegisterUser(String username, String email, String password)
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

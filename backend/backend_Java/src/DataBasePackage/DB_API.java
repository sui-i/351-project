package DataBasePackage;

import java.util.HashMap;

public class DB_API {
	private static HashMap<String,Integer> cache;
	// For caching at max 10 userNames.


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
	static public int checkLoginCredentials(String username, String password)
	{
		//returns 0 if username and password are valid, and email is verified.
		//returns 1 if they are invalid
		//returns 2 if they are valid but email is not verified
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
		//adds a new user
		//returns 0 if user was successfully added
		//returns 1 if user already exists
		//returns 2 if some other error occurs
	}
}

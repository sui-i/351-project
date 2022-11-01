package DataBasePackage;

public class DB_API {
	static public String getFirstName(String username) 
	{
		//returns FirstName From DataBase
	}
	static public String getLastName(String username)
	{
		//returns LastName From DataBase
	}
	static public boolean checkMembership(String username)
	{
		//returns if username has an account
	}
	static public int checkLoginCredentials(String username, String password)
	{
		//returns 0 if username and password are valid, and email is verified.
		//returns 1 if they are invalid
		//returns 2 if they are valid but email is not verified
	}
	static public int RegisterUser(String username, String email, String password)
	{
		//adds a new user
		//returns 0 if user was successfully added
		//returns 1 if user already exists
		//returns 2 if some other error occurs
	}
}

package serverPackage;
import java.time.Clock;
import DataBasePackage.DB_API;

public class S_Client {
	int sessionID;
	String username;
	String firstName;
	String lastName;
	
	public S_Client(String Username)
	{
		firstName = DB_API.getFirstName(username);
		lastName = DB_API.getLastName(username);
		lastContactTime = time.time();
	}
}

package serverPackage;



public class C_Client {
	String username;
	String firstName;
	String lastName;
	C_Client(String Username)
	{
		firstName = DB_getFirstName(username);
		lastName = DB_getLastName(username);
	}
}

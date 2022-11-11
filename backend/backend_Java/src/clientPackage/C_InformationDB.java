package clientPackage;

public class C_InformationDB {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String birthDate;
	private String Location;
	
	public C_InformationDB(String username, String firstname, String lastname,String phonenumber, String birthdate, String location) 
	{
		// Shouldn't these be setters? ~Mounif
		userName=username;
		firstName= firstname;
		lastName = lastname;
		phoneNumber= phonenumber;
		birthDate= birthdate;
		Location =location;
	}
	
	public String getuserName() {
		return userName;
	}
	public String getfirstName() {
		return firstName;
	}
	public String getlastName() {
		return lastName;
	}
	public String getphoneNumber() {
		return phoneNumber;
	}
	public String getbirthDate() {
		return birthDate;
	}
	public String getLocation() {
		return Location;
	}
	

}

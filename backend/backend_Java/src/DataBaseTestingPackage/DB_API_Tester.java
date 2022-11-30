package DataBaseTestingPackage;

import DataBasePackage.*;
public class DB_API_Tester {
	public static void main(String[] args) {
		DB_API instance = new DB_API();
		instance.ConnectDB();
		System.out.println(instance.RegisterAdmin("hmk56", "hmk57@mail.aub.edu","78999301"));
	
		//System.out.println(instance.deleteUserByUsername("hmk56"));
		//System.out.println(instance.RegisterAdmin("hmk56", "hmk57@mail.aub.edu","78999301"));
		DB_UserInformation info=instance.getUserInfo("hmk56");
		System.out.println(info.getPasswordHash());
		//System.out.println(instance.checkLoginCredentials("hmk56", "78999301", "hmk57@mail.aub.edu"));
		//System.out.println(instance.deleteUserByUsername("hmk57"));
		//System.out.println(instance.RegisterUser("hmk57","hmk57@mail.aub.edu", "23123", "Hashem","Khodor", "2312"));
		//System.out.println(instance.getUserInfo("hmk56").getEmail());
		//System.out.println();
		
	}

}

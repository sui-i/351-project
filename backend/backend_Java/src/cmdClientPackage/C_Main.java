package cmdClientPackage;

import java.io.IOException;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import requestsrepliescodes.ValidateIdentitySynthax;
public class C_Main {
	private static void pressAnyKeyToContinue() {
		System.out.println("Press any key to continue.");
		try {
			System.in.read();
		} catch (IOException e2) {}
	}
	private Scanner cmdIn;
	private C_Client web;
	C_Main(Socket socket){
		this.cmdIn = new Scanner(System.in);
		web = new C_Client(socket);
	}
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("localhost",351);
		} catch (IOException e) {
			System.err.println("App could not connect to the server...");
			pressAnyKeyToContinue();
			return;
		}
		C_Main clientApp = new C_Main(socket);
		try {
			clientApp.startApp();
		} catch (Exception e) {
			System.err.println("A fatal error has occured.");
			e.printStackTrace();
		}
		pressAnyKeyToContinue();
	}
	
	public void startApp() {
		System.out.println("Welcome to Hosteler commandLine reservation applet.");
		List<Character> options = Arrays.asList(new Character[] {'L','R','V','B','U','Q'});
		char option = 'X';
		boolean running = true;
		while (running) {
			while (!options.contains(option)) {
				try {
					option = selectOptionsMenu();
				} catch (IOException e) {
					System.out.println("[Error] Invalid option selected.");
					option = 'X';
				}
			}
			if (option=='Q') {
				System.out.println("Thank you for visiting Hosteler!");
				web.closeEverything();
				running = false;
				continue;
			}
			if (option=='L')
				LoginCMDInterface();
			if (option=='R')
				RegisterCMDInterface();
			if (option=='V')
				VerifyEmailCMDInterface();
			option = 'X';
		}
	}
	
	public char selectOptionsMenu() throws IOException {
		System.out.println("Please select one of the following options");
		System.out.println("    -L  Login");
		System.out.println("    -R  Register");
		System.out.println("    -V  Verify email adress");
		System.out.println("    -B  Reserve a room");
		System.out.println("    -U  Unreserve a room");
		System.out.println("    -Q  Quit the app");
		return cmdIn.nextLine().charAt(0);
	}
	
	
	/*
	 * Set of functions that define the CMD interface.
	 * 
	 * */
	public void LoginCMDInterface() {
		//take username and password and check their validity
		System.out.println("Login:\nPlease enter your username: ");
		String username = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkUsername(username)) {
			System.out.println("Username must not contain spaces or commas");
			return;
		}
		System.out.println("Please enter your password: ");
		String password = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkPassword(password)) {
			System.out.println("Password must be at least 8 characters long and not contain commas");
			return;
		}
		
		//Call login and print its receive.
		System.out.println(web.AttemptLogin(username, password));
		
	}
	public void RegisterCMDInterface() {
		System.out.println("Register:\nPlease enter your username: ");
		String username = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkUsername(username)) {
			System.out.println("Username must not contain spaces or commas");
			return;
		}
		System.out.println("Please enter your password: ");
		String password = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkPassword(password)) {
			System.out.println("Password must be at least 8 characters long and not contain commas");
			return;
		}
		System.out.println("Re-enter your password: ");
		if (!password.equals(cmdIn.nextLine())) {
			System.out.println("Passwords must match.");
			return;
		}
		
		System.out.println("Please enter your email: ");
		String email = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkEmail(email)) {
			System.out.println("Email must follow emails' format.");
			return;
		}
		
		System.out.println("Please enter your first name: ");
		String firstName = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkName(firstName)) {
			System.out.println("first name must only contain uppercase and lowerCase characters and spaces.");
			return;
		}
		
		System.out.println("Please enter your last name: ");
		String lastName = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkName(lastName)) {
			System.out.println("last name must only contain uppercase and lowerCase characters and spaces.");
			return;
		}
		
		//Call login and print its receive.
		System.out.println(web.AttemptRegister(username, password, email, firstName, lastName));
	}
	public void VerifyEmailCMDInterface() {
		System.out.println("Email verification:\nPlease enter your username: ");
		String username = cmdIn.nextLine();
		if (!ValidateIdentitySynthax.checkUsername(username)) {
			System.out.println("Username must not contain spaces or commas");
			return;
		}
		System.out.println("Please enter your verificationCode: ");
		String verificationCode = cmdIn.nextLine();
		
		System.out.println(web.AttemptVerifyEmail(username, verificationCode));
	}
	
	public void ReserveCMDInterface() {
		
	}
	public void UnreserveCMDInterface() {
		
	}

}

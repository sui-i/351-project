package serverPackage;
import java.time.Clock;
import java.util.HashMap;
import java.util.LinkedList;

import DataBasePackage.DB_API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class S_Client implements Runnable{
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	String clientUsername;
	String clientEmail;
	String clientPassword;
	String firstName;
	String lastName;

	
	public S_Client(Socket socket)
	{
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader (socket.getInputStream( )));
			this.clientUsername = bufferedReader.readLine();
			this.clientPassword = bufferedReader.readLine();
			
			
			//TODO: add thread locks to registrations
			if (DB_API.checkMembership(clientUsername))
			{
				if (!DB_API.checkLoginCredentials(clientUsername, clientPassword))
					//TODO: Signal to the client that username or password does not exist
					//or that email is not verified.
					//and kill the client
			}
			else
			{
				//Ask the user if they want to register, and let them provide their email
				
				int RegistrationErrorCode = DB_API.RegisterUser(clientUsername, clientEmail, clientPassword);
				if (RegistrationErrorCode == 0) {}
				else if (RegistrationErrorCode == 1) {}
				else if (RegistrationErrorCode == 2)
					 //TODO: Signal the error to the user and kill the client
				//TODO: send a verification mail
			}
		}
		
	}

	@Override
	public void run() {
		
		
	}
}

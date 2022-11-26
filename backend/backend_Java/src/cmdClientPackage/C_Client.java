package cmdClientPackage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.Socket;


/**
 * Defines The client side commandline application.
 * 
 */
public class C_Client {
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public C_Client(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader (socket.getInputStream ()));
			this.socket = socket;
				
		} catch (IOException e){
			closeEverything();
		}
	}
	
	public void closeEverything() {
		try {
			if (bufferedReader!=null)bufferedReader.close();
			if (bufferedWriter!=null)bufferedWriter.close();
			if (socket!=null)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String request) {
		try {
			bufferedWriter.write(request);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			closeEverything();
		}
	}
	public String receive() {
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			closeEverything();
		}
		return null;
	}
	
	public String sendAndReceive(String request) {
		send(request);
		return receive();
	}
	
	public String AttemptLogin(String username, String password)
	{
		send(String.format("Req110:%s,%s",username,password));
		String reply = receive();
		if ("Rep000".equals(reply)) return "Request was not understood.";
		if ("Rep110".equals(reply)) return "Login successful!";
		if ("Rep111".equals(reply)) return "Your email is not verified.";
		if ("Rep112".equals(reply)) return "Your username was not found.";
		if ("Rep113".equals(reply)) return "Wrong password.";
		return "Try again, Server reply incomprehensible: " + reply;
	}

	public String AttemptRegister(String username, String password, String email, String firstName, String lastName)
	{
		send(String.format("Req120:%s,%s,%s,%s,%s",username,password,email,firstName,lastName));
		String reply = receive();
		if ("Rep000".equals(reply)) return "Request was not understood.";
		if ("Rep120".equals(reply)) return "Registeration successful!";
		if ("Rep121".equals(reply)) return "This email is not available.";
		if ("Rep123".equals(reply)) return "This username is already Taken.";
		if ("Rep122".equals(reply)) return "This email already exists.";
		return "Try again, Server reply incomprehensible: " + reply;
	}
	
	public String AttemptVerifyEmail(String username, String VerificationCode)
	{
		send(String.format("Req130:%s,%s",username,VerificationCode));
		String reply = receive();
		if ("Rep000".equals(reply)) return "Request was not understood.";
		if ("Rep130".equals(reply)) return "Verification successful!";
		if ("Rep131".equals(reply)) return "You entered the wrong verification code.";
		if ("Rep132".equals(reply)) return "You are already verified.";
		return "Try again, Server reply incomprehensible: " + reply;
	}
	
	public String AttemptLogout()
	{
		send(String.format("Req140"));
		String reply = receive();
		if ("Rep000".equals(reply)) return "Request was not understood.";
		if ("Rep140".equals(reply)) return "Logout successful!";
		return "Try again, Server reply incomprehensible: " + reply;
	}

}

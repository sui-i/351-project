package emailVerificationServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class RetiredEmailServer{
	// Expected to use/configure the smtp library 
	//This server just sends verification codes associated with usernames.
	//in return it excepts an email of the following form:
	//Username
	//PIN CODE
	//if the pin code sent with username is valid, it turns a boolean in the database.
	
	public static final int smtpPORT = 25;
	public static final String accountsEmail = "Verify-Yourself@Hosteler.com";
	public static final String InvoicesEmail = "Invoice@Hosteler.com";
	public static boolean send(String Message, String to, String from) {
		Socket socket = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;
		String mailServerName = "smtp." + to.split("@")[1];
		try {
			socket = new Socket(mailServerName, smtpPORT);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			closeEverything(socket,bufferedReader,bufferedWriter);
			return false;
		}
		try {
			bufferedWriter.write(String.format("HELO %s", mailServerName)); bufferedWriter.newLine();
			bufferedReader.readLine();
			bufferedWriter.write(String.format("MAIL From:<%s>", from)); bufferedWriter.newLine();
			bufferedReader.readLine();
			bufferedWriter.write(String.format("RCPT TO:<%s>", to)); bufferedWriter.newLine();
			bufferedReader.readLine();
			bufferedWriter.write("DATA"); bufferedWriter.newLine();
			bufferedWriter.write(Message); bufferedWriter.newLine();
			bufferedWriter.write("."); bufferedWriter.newLine();
			bufferedWriter.write("QUIT"); bufferedWriter.newLine();
		} catch (IOException e) {
			closeEverything(socket,bufferedReader,bufferedWriter);
			return false;
		}
		closeEverything(socket,bufferedReader,bufferedWriter);
		return true;
	}
	
	public static void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if (bufferedReader!=null)bufferedReader.close();
			if (bufferedWriter!=null)bufferedWriter.close();
			if (socket!=null)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(send("Hey nerd", "ahmadelhajj110@gmail.com", accountsEmail));
	}
}

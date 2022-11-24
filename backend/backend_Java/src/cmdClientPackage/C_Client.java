package cmdClientPackage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.Socket;

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
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if (bufferedReader!=null)bufferedReader.close();
			if (bufferedWriter!=null)bufferedWriter.close();
			if (socket!=null)socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void AttemptLogin(String username, String password)
	{
		
	}

	public void AttemptRegister(String username, String password, String email, String firstName, String lastName)
	{
		
	}
	
	public void AttemptVerifyEmail(String username, String VerificationCode)
	{
		
	}
}

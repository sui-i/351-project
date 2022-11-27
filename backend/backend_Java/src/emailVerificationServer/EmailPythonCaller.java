package emailVerificationServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import requestsrepliescodes.MailCodes;
public class EmailPythonCaller {
	public static MailCodes send(String messageSubject, String messageBody, String recipient) {
		StringBuilder reply = new StringBuilder();
		int returnCode = 1;
		try {
			Process process = (new ProcessBuilder("python", System.getProperty("user.dir") + "\\src\\emailVerificationServer\\EmailAPICaller.py",
					messageSubject, messageBody, recipient)).start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String x;
			while ((x=reader.readLine()) != null) 
				reply.append(x + "\n");
			returnCode = process.exitValue();
			if (returnCode==MailCodes.EmailSentSuccessfully.ID)//350
				return MailCodes.EmailSentSuccessfully;
			if (returnCode==MailCodes.EmailRecipientNotFound.ID)//351
				return MailCodes.EmailRecipientNotFound;
			if (returnCode==MailCodes.EmailServiceNotAvailable.ID)//352
				return MailCodes.EmailServiceNotAvailable;
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (!(350<=returnCode && returnCode<=352))
				System.err.println("\n---------------\nPython Error: "+returnCode+"\n" + reply.toString() + "\n---------------\n");
		return MailCodes.InternalError;
	}
	
	public static void main(String[] args) {
		System.out.println(send("New subject",
								"Your Verification Code is: 999999",
								"nojive981@kuvasi.cm"));
	}
}

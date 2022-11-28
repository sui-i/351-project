package emailVerificationServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import requestsrepliescodes.MailCodes;
public class EmailPythonCaller {
	/** Send Emails from HostelerHotel@gmail.com
	 * Uses a python script to send an email to a recipient. It is used to send verification mails.
	 * 
	 * Limitations:
	 * 		Can only be used to send strings as bodyMessage
	 * 		if time left TODO: figure out support for html messageBody.
	 * @param self explanatory
	 * @return the appropriate MailCode
	 */
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
		System.out.println(send("Mounif 7aj tne2 ya khayye",
								"Mounif enta ktir bt ne2 please wa2if tne2 7a nmout",
								"mounifelkhatib@gmail.com"));
	}
}

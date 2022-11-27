package emailVerificationServer;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

public class EmailAPI {
	public static boolean send(String subject, String message, String to)
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.auth",true);
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port",587);
		properties.put("mail.smtp.starttls.enable",true);
		properties.put("mail.transport.protocol","smtp");
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("hostelerhotel@gmail.com","MounifKtirBiNe2");
			}
		});
		System.out.println("test");
		Message mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(String.format("New message from Hosteller!\n%s\nThank you for using Hostller.", message), "text/html");
			Address addressTo = new InternetAddress(to);
			mimeMessage.setRecipient(Message.RecipientType.TO, addressTo);
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(send("Verify your email", "your verification code is : 1232141", "nojiveb981@kuvasin.com"));
	}
}

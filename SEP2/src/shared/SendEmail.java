package shared;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 *SendEmail class responsible for sending emails
 */
public class SendEmail {
	private final String senderEmailID = "studentsep2@gmail.com";
	private final String senderPassword = "student123456789";
	private final String emailSMTPserver = "smtp.gmail.com";
	private final String emailServerPort = "465";
	private String receiverEmailID;
	private String emailSubject;
	private String emailBody;

	/**
	 * Constructor for SendEmail. Upon initialization sends an email to receiver.
	 * @param receiverEmailID e-mail id of receiver
	 * @param subject subject of email
	 * @param body body of email
	 */
	public SendEmail(String receiverEmailID,String subject, String body)
	{ // Receiver Email Address
		this.receiverEmailID=receiverEmailID;
		// Subject
		this.emailSubject=subject;
		// Body
		this.emailBody=body;
		Properties props = new Properties();
		props.put("mail.smtp.user",senderEmailID);
		props.put("mail.smtp.host", emailSMTPserver);
		props.put("mail.smtp.port", emailServerPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", emailServerPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		SecurityManager security = System.getSecurityManager();
		try
		{
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(props, auth);
		MimeMessage msg = new MimeMessage(session);
		msg.setText(emailBody);
		msg.setSubject(emailSubject);
		msg.setFrom(new InternetAddress(senderEmailID));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
		Transport.send(msg);
		System.out.println("E-mail with User Credentials sent to "+ receiverEmailID);
		}
		catch (Exception mex)
		{
			System.out.println("Error sending E-mail to "+ receiverEmailID);
				mex.printStackTrace();
		}
	}

	/**
	 * class SMTPAuthenticator serves for authentication while sending e-mail
	 */
	public class SMTPAuthenticator extends javax.mail.Authenticator
	{
		/**
		 * method returning PasswordAuthenication object upon authentication
		 */
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(senderEmailID, senderPassword);
		}
	}

}


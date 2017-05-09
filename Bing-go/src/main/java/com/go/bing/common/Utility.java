package com.go.bing.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.validator.internal.util.privilegedactions.GetMethodFromPropertyName;

import com.go.bing.model.User;

public class Utility {

	private Properties properties;

	public Utility() {
		readAllProperties();
	}
	
	public void sendMail(User recepient) {
		// Recipient's email ID needs to be mentioned.
		String to = recepient.getEmailId();

		// Sender's email ID needs to be mentioned
		String from = "no-reply-binggo@gmail.com";
		final String username = "bingobearcats@gmail.com";// change accordingly
		final String password = "bingobearcats123";// change accordingly

		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Activation Email");

			// Now set the actual message
			message.setText("Hello, \n Please click on the below link to activate your account.\n\n" + createActivationLink(recepient));

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private String createActivationLink(User recepient) {
		return "http://" + getPropertyValue("bing.go.server.host") + ":" + getPropertyValue("bing.go.server.port") +"/approve/" + recepient.getUserId();
	}

	private void readAllProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("application.properties");

		this.properties = new Properties();
    	try {
			this.properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPropertyValue(String key) {
		return this.properties.getProperty(key);
	}
}

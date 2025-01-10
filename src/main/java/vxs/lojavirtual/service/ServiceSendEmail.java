package vxs.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;

@Service
public class ServiceSendEmail {

	private final String userName = "projetoemailvitorxavier@gmail.com";
	private final String password = "mfqvlphmjlfkodqp";

	@Async
	public void enviarEmailHtml(String assunto, String messagem, String emailDestino)
			throws UnsupportedEncodingException, MessagingException {

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(userName, password);
			}

		});

		session.setDebug(true);

		Address[] toUser = InternetAddress.parse(emailDestino);

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(userName, "Xavier - Loja virtual"));
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assunto);
		message.setContent(messagem, "text/html; charset=utf-8");
	
        
		Transport.send(message);
		
		
		
	}

}

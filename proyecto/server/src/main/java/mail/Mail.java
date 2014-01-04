package mail;

import java.util.Properties;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
	
	/**
	 * Autores: Pallas, Martin, Recuenco.
	 */

	
	public static void main(String[] args) {

	    final String username = "cjperez8086@gmail.com";
	    final String password = "club8086";

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("IW7I"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("richisuarg5@gmail.com"));
	        message.setSubject("Informe de monitorización - IW7I");
	        //message.setText("Le enviamos el informe de la pagina www.vayapardo.com, adjuntado en un documento pdf.");

	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	       

	        Multipart multipart = new MimeMultipart();
	        
	        // Create the message part
	        BodyPart messageBodyPart2 = new MimeBodyPart();
	        // Fill the message
	        messageBodyPart2.setText("Le enviamos el informe de la pagina www.vayapardo.com, adjuntado en un documento pdf.");
	        messageBodyPart2.setContent("Le enviamos el informe de la pagina www.vayapardo.com, adjuntado en un documento pdf.", "text/html");
	        multipart.addBodyPart(messageBodyPart2); //se añade
	        
	        
	        //Crete de attachment file part
	        messageBodyPart = new MimeBodyPart();
	        
	        
	        String file = "tabla.pdf";
	        String fileName = "tabla.pdf";
	        DataSource source = new FileDataSource(file);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);

	        message.setContent(multipart);

	        System.out.println("Sending");

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	  }

}

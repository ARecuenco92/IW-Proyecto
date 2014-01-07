package mail;

import java.util.Properties;


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

public class Mail2 {
	
	/**
	 * Autores: Pallas, Martin, Recuenco.
	 */

	private String pageName; //Nombre de la página que se monitoriza
	private String emailAddress;   //Dirección de email al cual mandamos el informe
	
	public Mail2(String pageName, String emailAddress){
		this.pageName = pageName;
		this.emailAddress = emailAddress;	
	}
	public String sendMail() {

	    final String username = "cjperez8086@gmail.com";
	    final String password = "uvbvnmilqiourhwn";

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
	                InternetAddress.parse(emailAddress));
	        message.setSubject("Informe de monitorización - IW7I");

            Multipart multipart = new MimeMultipart();
	        
	        // Create the message part
	        BodyPart messageBodyPart2 = new MimeBodyPart();
	        // Fill the message
	        messageBodyPart2.setText("Le enviamos el informe de la pagina "+pageName+", adjuntado en un documento pdf.");
	        messageBodyPart2.setContent("Le enviamos el informe de la pagina "+pageName+", adjuntado en un documento pdf.", "text/html");
	        multipart.addBodyPart(messageBodyPart2); //se añade
	        
	        message.setContent(multipart);

	        System.out.println("Sending2");

	        Transport.send(message);

	        System.out.println("Done2");
	        
	        return "MENSAJE ENVIADO SIN ERRORES";

	    } catch (MessagingException e) {
	        e.printStackTrace();
	        return e.getMessage()+" "+e.getLocalizedMessage();
	    }
	  }

}

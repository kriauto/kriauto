package example.angularspring.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Service("senderService")
public class SenderServiceImpl implements SenderService {

	@Override
	public int sendSms(String from, String to, String message) {
		System.out.println("from -> "+from+"to -> "+to+"message -> "+message);
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		HttpPost request ;
		HttpResponse response;
		int status = 0 ;

		try {
		    request = new HttpPost("http://panel.smspm.com/gateway/c9ecee9259af98dccfef3603371fea26e267739a/api.v1/send?phone="+to+"&sender="+from+"&message="+message+"&output=json");
		    response = httpClient.execute(request);
		    status = response.getStatusLine().getStatusCode();
		    System.out.println("status -> "+response.getStatusLine().getStatusCode());
		    // handle response here...
		}catch (Exception ex) {
		    // handle exception here
		} finally {
		    httpClient.getConnectionManager().shutdown(); //Deprecated
		}
		if(status == 200){
		   return 0;
		}else{
		   return 1;
		}
	}

	@Override
	public int sendMail(String from, String to, String subject, String content) {
		final String username = "abdelhaq.elhassnaoui@gmail.com";
		final String password = "SCALaman1980";
		int status = 0;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			status = 1;
			throw new RuntimeException(e);
		}
		return status;
	}

}

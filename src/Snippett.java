import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class Snippett {
	 MessageModel messageModel = null;
	    List<MessageModel> messageModels;

	    String recipient;
	    String myEmail;
	    String password;
	    String subject;
	    String textBody;

	    Session session;

//	    public Snippett(String recipient, String myEmail, String password, String subject, String textBody) {
//	        this.recipient = recipient;
//	        this.myEmail = myEmail;
//	        this.password = password;
//	        this.subject = subject;
//	        this.textBody = textBody;
//	    }
//	
	 public List<MessageModel> ReciveMessages(String email, String password) throws IOException {
	
	        String host = "imap.gmail.com";
	        String provider  = "imaps";
	        try
	        {
	            Properties properties = new Properties();
	
	            //Connect to the server
	            Session session = Session.getDefaultInstance(properties, null);
	            Store store     = session.getStore(provider);
	            store.connect(host, email, password);
	
	            //open the inbox folder
	            Folder inbox = store.getFolder("INBOX");
	            inbox.open(Folder.READ_ONLY);
	
	            // get a list of javamail messages as an array of messages
	            Message[] messages = inbox.getMessages();
	
	            System.out.println("messages.length---" + messages.length);
	
	            messageModels = new ArrayList<>();
	
	            for (int i = 0, n = messages.length; i < n; i++) {
	                Message message = messages[i];
	                messageModel = new MessageModel();
	                messageModel.setFrom(message.getFrom()[0].toString());
	                messageModel.setMessage(message.getContent().toString());
	                messageModel.setSubject(message.getSubject()  +"   "+message.getSentDate());
	
	                
	                
	                messageModels.add(messageModel);
	
	                System.out.println("ccccccccccccccccccccccccccccccccccc ====== " + messageModel.getFrom());
	
	                System.out.println("---------------------------------");
	                System.out.println("Email Number " + (i + 1));
	                System.out.println("Subject: " + message.getSubject());
	                System.out.println("From: " + message.getFrom()[0]);
	                System.out.println("Text: " + message.getContent().toString());
	                System.out.println("---------------------------------");
	            }
	
	            //close the inbox folder but do not
	            //remove the messages from the server
	            inbox.close(false);
	            store.close();
	        }
	        catch (MessagingException me)
	        {
	            System.err.println("messaging exception");
	            me.printStackTrace();
	        }
	
	        return messageModels;
	    }
	 
	 public static void main(String[] args) throws IOException {
		List<MessageModel> reciveMessages = new Snippett().ReciveMessages("vinitsaini357@gmail.com","21nJk6%34{.#~j)`+'");
		
		for (MessageModel messageModel : reciveMessages) {
			System.out.println(messageModel);
		}
		
	}
}
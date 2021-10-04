import java.io.IOException;  
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Folder;  
import javax.mail.Message;  
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.pop3.POP3Store;  
  
public class ReceiveMail{  
  
 public static void receiveEmail(String pop3Host, String storeType,  
  String user, String password,String folder) {  
  try {  
   //1) get the session object  
   Properties properties = new Properties();  
   
   
   properties.setProperty("mail.pop3.ssl.enable", "false");
   properties.setProperty("mail.pop3.starttls.enable", "true"); 
   properties.setProperty("mail.pop3.starttls.required", "true");
   
   properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
   properties.setProperty("mail.pop3.socketFactory.fallback", "false");
   properties.setProperty( "mail.pop3.host", pop3Host );
   properties.setProperty( "mail.pop3.user",user);
   properties.setProperty( "mail.pop3.password",password);
   properties.setProperty( "mail.pop3.ssl.enable", "false");
   properties.setProperty( "mail.pop3.port", "445" );
   properties.setProperty( "mail.pop3.auth", "true" );      
   properties.setProperty("mail.pop3.starttls.enable", "true");
   
   
   
   
   
   
   Session emailSession = Session.getDefaultInstance(properties);  
     
   //2) create the POP3 store object and connect with the pop server  
   POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);  
   emailStore.connect(user, password);  
  
   //3) create the folder object and open it  
   Folder emailFolder = emailStore.getDefaultFolder();  
   
   
   emailFolder.open(Folder.READ_ONLY);  
  
   //4) retrieve the messages from the folder in an array and print it  
   Message[] messages = emailFolder.getMessages();  
   for (int i = 0; i < messages.length; i++) {  
    Message message = messages[i];  
    System.out.println("---------------------------------");  
    System.out.println("Email Number " + (i + 1));  
    System.out.println("Subject: " + message.getSubject());  
    System.out.print("From: " );  
    
    
    for (Address s :  message.getFrom()) {
		System.out.println("\t "+s.toString());
	}
    
    System.out.println("Text: " + message.getContent().toString());  
   }  
  
   //5) close the store and folder objects  
   emailFolder.close(false);  
   emailStore.close();  
  
  } catch (NoSuchProviderException e) {e.printStackTrace();}   
  catch (MessagingException e) {e.printStackTrace();}  
  catch (IOException e) {e.printStackTrace();}  
 }  
  
public static void main(String[] args) {  
	 
	
	
/*	 Scanner scanner = new Scanner(System.in);
	 System.out.println("Enter The Host :");
	 String inputHost = scanner.nextLine();
	 
	 System.out.println("Enter The mailStoreType :");
	 String inputMailStoreType = scanner.nextLine();
	 
	 System.out.println("Enter The Email or username :");
	 String inputUsername = scanner.nextLine();
	 
	 System.out.println("Enter The Password :");
	 String inputPassword = scanner.nextLine();
	 
	 System.out.println("Enter The Folder :");
	 String inputFolder = scanner.nextLine();
	*/
	
	
	 
  String host = "mail.shivit.in";//change accordingly  
  String mailStoreType = "pop3";  
  String username= "vinit.saini@shivit.in";  
  String password= "Mpic5sih@#123";//change accordingly  
  
  receiveEmail(host, mailStoreType, username, password,"INBOX");  //INBOX

	// receiveEmail(inputHost,inputMailStoreType,inputUsername,inputPassword,inputFolder);
	 
	
	//reciveMailByGmail("vinitsaiin357@gmail.com", "21nJk6%34{.#~j)`+'");
	 
 }  
 
 
 
 private boolean textIsHtml = false;

 /**
  * Return the primary text content of the message.
  */
 public String getText(Part p) throws
             MessagingException, IOException {
     if (p.isMimeType("text/*")) {
         String s = (String)p.getContent();
         textIsHtml = p.isMimeType("text/html");
         return s;
     }

     if (p.isMimeType("multipart/alternative")) {
         // prefer html text over plain text
         Multipart mp = (Multipart)p.getContent();
         String text = null;
         for (int i = 0; i < mp.getCount(); i++) {
             Part bp = mp.getBodyPart(i);
             if (bp.isMimeType("text/plain")) {
                 if (text == null)
                     text = getText(bp);
                 continue;
             } else if (bp.isMimeType("text/html")) {
                 String s = getText(bp);
                 if (s != null)
                     return s;
             } else {
                 return getText(bp);
             }
         }
         return text;
     } else if (p.isMimeType("multipart/*")) {
         Multipart mp = (Multipart)p.getContent();
         for (int i = 0; i < mp.getCount(); i++) {
             String s = getText(mp.getBodyPart(i));
             if (s != null)
                 return s;
         }
     }

     return null;
 }
 
 
 
 public static void receiveEmail2(String pop3Host, 
			String storeType, String user, String password){
	 
	 System.out.println("--------------");
		Properties props = new Properties();
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");
		props.put("mail.store.protocol", "pop3");
	 
	Session session = Session.getInstance(props);	
	try {  
		Store mailStore = session.getStore(storeType);
		mailStore.connect(pop3Host, user, password);
	 
		Folder folder = mailStore.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	 
		Message[] emailMessages = folder.getMessages();
		System.out.println("Total Message - " 
				+ emailMessages.length);
	 
	   //Iterate the messages
	  for (int i = 0; i < emailMessages.length; i++) {
	   Message message = emailMessages[i];
	   Address[] toAddress = 
	   message.getRecipients(Message.RecipientType.TO);
	   System.out.println();  
	   System.out.println("Email " + (i+1) + "-");  
	   System.out.println("Subject - " + message.getSubject());  
	   System.out.println("From - " + message.getFrom()[0]); 
	 
	   System.out.println("To - "); 
	   for(int j = 0; j < toAddress.length; j++){
		   System.out.println(toAddress[j].toString());
	   }
	   System.out.println("Text - " + 
			   message.getContent().toString());  
	  }
	 
	   folder.close(false);
	   mailStore.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("Error in receiving email.");
	    }        
	}
 
 
 
 
 public static void reciveMailByGmail(String gmailId,String gmailPassword) {
	 Properties props = System.getProperties();
	 props.setProperty("mail.store.protocol", "imaps");
	 try {
	     Session session = Session.getDefaultInstance(props, null);
	     javax.mail.Store store = session.getStore("imaps");
	     store.connect("imap.gmail.com", gmailId, gmailPassword);
	     javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
	     for (javax.mail.Folder folder : folders) {
	         if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
	             System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
	         }
	     }
	 } catch (MessagingException e) {
	     e.printStackTrace();
	 }
}
}  
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class GoogleAllFolders {

	public static void main(String[] args) {
		name();
	}
	
	public static void name() {
		 Properties props = new Properties();

		    props.setProperty("mail.store.protocol", "imaps");

		    try {
		        Session session = Session.getInstance(props, null);
		        Store store = session.getStore();
		        store.connect("imap.gmail.com", "vinitsaini357@gmail.com", "21nJk6%34{.#~j)`+'");
		        Folder[] folderList = store.getFolder("[Gmail]").list();
		        for (int i = 0; i < folderList.length; i++) {
		            
		        	String fullName = folderList[i].getFullName();
		        	Folder folder = store.getFolder(folderList[i].getFullName());
		        	
			        	System.out.println(fullName+" have "+folder.getMessageCount()+" messages");
		           
		            
		        }
		        Folder inbox = store.getFolder("[Gmail]/Sent Mail");
		        inbox.open(Folder.READ_ONLY);
		        
		        
		        int messageCount = inbox.getMessageCount();
		        int round=0;
		        if(messageCount>=10)
		        	messageCount=10;
		        
		        
		        for (int i = 0; i < messageCount; i++) {
					
		        	 Message msg = inbox.getMessage(inbox.getMessageCount()-i);
				        Address[] in = msg.getFrom();
				        for (Address address : in) {
				            System.out.println("FROM:" + address.toString());
				        }
				        Multipart mp = (Multipart) msg.getContent();
				        BodyPart bp = mp.getBodyPart(0);
				        System.out.println("Bcc User NAme :"+InternetAddress.toString(msg.getRecipients(Message.RecipientType.BCC)));
				        System.out.println("SENT DATE:" + msg.getSentDate());
				        System.out.println("SUBJECT:" + msg.getSubject());
				        System.out.println("CONTENT:" + bp.getContent());
				        
				        System.out.println("-------------------------------------------------------");
				}
		        
		        
		        
		       
		    } catch (Exception mex) {
		        mex.printStackTrace();
		    }
	}
}

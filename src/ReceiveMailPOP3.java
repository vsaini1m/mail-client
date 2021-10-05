import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;
import javax.mail.*;

import javax.mail.internet.MimeBodyPart;

import com.sun.mail.util.MailSSLSocketFactory;

public class ReceiveMailPOP3 {
	
	 public static void main(String args[]) throws Exception {
		    ReceiveMailPOP3.doit();
		  }
	 
	// /", "");
	private static final String HOST = "pop.gmail.com";
	  private static final String USERNAME = "vinitsaini357@gmail.com";
	  private static final String PASSWORD = "21nJk6%34{.#~j)`+'";

  public static void doit() throws MessagingException, IOException {
	  
	  MailSSLSocketFactory sf = null;
	try {
		sf = new MailSSLSocketFactory();
	} catch (GeneralSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  sf.setTrustAllHosts(true); 
	
	 
	  
    Folder folder = null;
    Store store = null;
    try {
      Properties props = new Properties();
      
      props.put("mail.imap.ssl.trust", "*");
      props.put("mail.imap.ssl.socketFactory", sf);
	  
      props.put("mail.store.protocol", "pop3s"); // Google uses POP3S not POP3
      Session session = Session.getDefaultInstance(props);
      // session.setDebug(true);
      store = session.getStore();
      store.connect(HOST, USERNAME, PASSWORD);
      folder = store.getDefaultFolder().getFolder("INBOX");
      folder.open(Folder.READ_ONLY);
      Message[] messages = folder.getMessages();
      System.out.println("No of Messages : " + folder.getMessageCount());
      System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
      for (int i=0; i < messages.length; ++i) {
        System.out.println("MESSAGE #" + (i + 1) + ":");
        Message msg = messages[i];
        String from = "unknown";
        if (msg.getReplyTo().length >= 1) {
          from = msg.getReplyTo()[0].toString();
        }
        else if (msg.getFrom().length >= 1) {
          from = msg.getFrom()[0].toString();
        }
        String subject = msg.getSubject();
        System.out.println("Saving ... " + subject +" " + from);
        // you may want to replace the spaces with "_"
        // the files will be saved into the TEMP directory
        String filename = "c:/temp/" +  subject;
        saveParts(msg.getContent(), filename);
      }
    }
    finally {
      if (folder != null) { folder.close(true); }
      if (store != null) { store.close(); }
    }
  }

  public static void saveParts(Object content, String filename)
  throws IOException, MessagingException
  {
    OutputStream out = null;
    InputStream in = null;
    try {
      if (content instanceof Multipart) {
        Multipart multi = ((Multipart)content);
        int parts = multi.getCount();
        for (int j=0; j < parts; ++j) {
          MimeBodyPart part = (MimeBodyPart)multi.getBodyPart(j);
          if (part.getContent() instanceof Multipart) {
            // part-within-a-part, do some recursion...
            saveParts(part.getContent(), filename);
          }
          else {
            String extension = "";
            if (part.isMimeType("text/html")) {
              extension = "html";
            }
            else {
              if (part.isMimeType("text/plain")) {
                extension = "txt";
              }
              else {
                //  Try to get the name of the attachment
                extension = part.getDataHandler().getName();
              }
              filename = filename + "." + extension;
              System.out.println("... " + filename);
              out = new FileOutputStream(new File(filename));
              in = part.getInputStream();
              int k;
              while ((k = in.read()) != -1) {
                out.write(k);
              }
            }
          }
        }
      }
    }
    finally {
      if (in != null) { in.close(); }
      if (out != null) { out.flush(); out.close(); }
    }
  }

 
}
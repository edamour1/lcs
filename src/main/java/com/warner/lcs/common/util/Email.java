package com.warner.lcs.common.util;



import com.warner.lcs.app.domain.Business;
import com.warner.lcs.app.domain.Client;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {

    //SETUP MAIL SERVER PROPERTIES
    //DRAFT AN EMAIL
    //SEND EMAIL

    private Session newSession = null;
    private MimeMessage mimeMessage = null;
    private String emailSubject, emailBody;
    private String[] emailReceipients;
    private String pdfFilePath; // Specify the path to your PDF file

    public Email(){}
    public Email(String emailSubject, String emailBody, String[] emailReceipients, String pdfFilePath) throws MessagingException, IOException {
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        this.emailReceipients = emailReceipients;
        this.pdfFilePath = pdfFilePath;
        this.setupServerProperties();
        this.draftEmail();
    }

    public void sendEmail() throws MessagingException {
        String fromUser = "edamourjr@gmail.com";  //Enter sender email id
        String fromUserPassword = "**** **** **** ****";  //Enter sender gmail password , this will be authenticated by gmail smtp server
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email successfully sent!!!");
    }

    private MimeMessage draftEmail() throws AddressException, MessagingException, IOException {
//        String[] emailReceipients = {"clownprince1961@gmail.com","cj100517@gmail.com"};  //Enter list of email recepients
//        String emailSubject = "Test Mail lived";
//        String emailBody = "Test Body of my email";
        mimeMessage = new MimeMessage(newSession);

        for (int i =0 ;i<emailReceipients.length;i++)
        {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
        }
        mimeMessage.setSubject(emailSubject);

        // CREATE MIMEMESSAGE
        // CREATE MESSAGE BODY PARTS
        // CREATE MESSAGE MULTIPART
        // ADD MESSAGE BODY PARTS ----> MULTIPART
        // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object


        MimeBodyPart bodyPart = new MimeBodyPart();
        MimeBodyPart pdfAttachmentPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(new File(pdfFilePath));
        pdfAttachmentPart.setDataHandler(new DataHandler(source));
        pdfAttachmentPart.setFileName(new File(pdfFilePath).getName()); // Set filename
        bodyPart.setText(emailBody);

        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        multiPart.addBodyPart(pdfAttachmentPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
    }


    private void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties,null);
    }
}

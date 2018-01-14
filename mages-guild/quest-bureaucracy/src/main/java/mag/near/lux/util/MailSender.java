package mag.near.lux.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MailSender {
    private final String emailSubject;
    private final String emailBody;
    private final String receiverEmailID;
    private final String senderEmailID;
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);
    private final Properties props;

    public MailSender(String receiverEmailID, String emailSubject, String emailBody) {
        this.receiverEmailID = receiverEmailID;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        props = new Properties();

        setUpProperties(props);
        senderEmailID = props.getProperty("mail.message.from");
        sendMessage(receiverEmailID, emailSubject, emailBody, props);
    }

    public MailSender(String receiverEmailID, String emailSubject, String emailBody, String file){
        this.receiverEmailID = receiverEmailID;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        props = new Properties();

        setUpProperties(props);
        senderEmailID = props.getProperty("mail.message.from");
        sendMessageWitghAttachment(receiverEmailID, emailSubject, emailBody, props, file);
    }

    private void sendMessage(String receiverEmailID, String emailSubject, String emailBody, Properties props) {
        try {
            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);
            msg.setText(emailBody);
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(receiverEmailID));
            Transport.send(msg);
            LOGGER.debug("Message with subject {} send to {}", emailSubject, receiverEmailID);
        } catch (Exception mex) {
           LOGGER.error(mex.getMessage());
        }
    }

    private void sendMessageWitghAttachment(String receiverEmailID, String emailSubject, String emailBody, Properties props, String file){
        try {
            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);

            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(receiverEmailID));

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(emailBody);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("quests.hml");

            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
            LOGGER.debug("Message with subject {} send to {}", emailSubject, receiverEmailID);
        } catch (Exception mex) {
            LOGGER.error(mex.getMessage());
        }
    }

    private void setUpProperties(Properties props) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(ResourcesNames.MAIL_PROPERTIES)) {
            props.load(is);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


}
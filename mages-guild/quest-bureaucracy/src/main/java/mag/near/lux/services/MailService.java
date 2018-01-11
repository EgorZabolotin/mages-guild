package mag.near.lux.services;

import mag.near.lux.util.MailSender;

public class MailService {
    public static void sendMail(String to, String subj, String body){
        MailSender mailSender = new MailSender(to, subj,body);
    }
}

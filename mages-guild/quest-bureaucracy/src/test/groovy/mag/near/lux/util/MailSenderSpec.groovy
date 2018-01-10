package mag.near.lux.util

import mag.near.lux.BaseSpec

class MailSenderSpec extends BaseSpec{

    def"send test email"(){
        expect:
        MailSender mailSender=new
                MailSender('ezabolotin@luxoft.com','test','test body')
    }

}

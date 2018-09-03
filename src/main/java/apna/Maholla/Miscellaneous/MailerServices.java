package apna.Maholla.Miscellaneous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailerServices {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailerServices(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void setJavaMailSender(String email) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("vivit1006117@gmail.com");
        mail.setFrom("viveksstrategy@gmail.com");
        mail.setSubject("Confirm Apna Mohalla Email Address");
        mail.setText("This is cool email notification");

        javaMailSender.send(mail);
    }
}

package cn.tgc.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Value("${spring.mail.username}")
    private String mailfrom;

    public void sendSimpleEmail(String mailto, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailfrom);
        message.setTo(mailto);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

}

package com.example.demo;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Utility {

    public static void sendEmail(String message) throws MessagingException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setUsername("bansalkomal93@gmail.com");
        sender.setPassword("jkcb qkdm khut tfsu");
        sender.setPort(587);
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        sender.setJavaMailProperties(props);
        final MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("bansalkomal93@gmail.com");
        helper.setTo("bansalkomal93@gmail.com");
        String body = message;
        helper.setText(body);
        helper.setSubject("Order Placed");
        sender.send(msg);
    }
}

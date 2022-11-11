package com.example.authncandauthhrztnapi.service;

import com.example.authncandauthhrztnapi.appuser.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class EmailService  implements EmailSender {

    private  final JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    @Async
    public void send(String to, String email){
        try{
            MimeMessage message =mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("confirm your email");
            helper.setFrom("korkutdede19@gmail.com");
        }
        catch (MessagingException ex){
            LOGGER.error("occured to exception during send to message",ex);
            throw new IllegalStateException("failde to send emial");
        }
    }
}

package com.bbp.BBPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendFulfillmentEmail(String to, String requestDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Blood Request Fulfilled");
        message.setText("✅ The following request is now fulfilled:\n\n" + requestDetails);
        mailSender.send(message);
    }
    public void sendEmail(String toEmail, String subject, String body) {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo(toEmail);
    	message.setSubject(subject);
    	message.setText(body);
    	message.setFrom("harini.ad22@bitsathy.ac.in");
    	mailSender.send(message);
    	System.out.println("Mail sent successfully...");
    	
    }
}
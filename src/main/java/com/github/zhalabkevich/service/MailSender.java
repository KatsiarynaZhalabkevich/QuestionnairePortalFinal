package com.github.zhalabkevich.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;


@Stateless
public class MailSender implements Serializable {

    @Resource(description = "mail.host")
    private String host;
    @Resource(description = "mail.username")
    private String username;
    @Resource(description = "mail.password")
    private String password;
    @Resource(description = "mail.port")
    private int port;
    @Resource(description = "mail.protocol")
    private String protocol;
    @Resource(description = "mail.debug")
    private String debug;


    public void send(String emailTo, String subject, String message) throws ServiceException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.protocol", protocol);
        properties.setProperty("mail.user", username);
        properties.setProperty("mail.password", password);
        Session session = Session.getDefaultInstance(properties);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ServiceException(e);
        }

    }


}

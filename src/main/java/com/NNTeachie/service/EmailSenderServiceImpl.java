package com.NNTeachie.service;

import com.NNTeachie.Dtos.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendToEmail(EmailTemplate template) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(template.getRecipientEmail());
            helper.setSubject(template.getSubject());

            helper.setText(template.getBody() + "<br><br>" + template.getSignature(), true);

            FileSystemResource file = new FileSystemResource(new File(template.getFilename()));
            if (file.exists()){
                helper.addAttachment(file.getFilename(), file);
            }
            sender.send(mimeMessage);


        } catch (MessagingException e) {

            throw new RuntimeException("Failed to send email", e);
        }
    }
}

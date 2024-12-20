package com.NNTeachie.service;

import com.NNTeachie.model.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    JavaMailSender sender;

    @Override
    public void sendToEmail(EmailTemplate template) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(template.getRecipientEmail().toArray(new String[0]));
            helper.setSubject(template.getSubject());

            helper.setText(template.getBody() + "<br><br>" + template.getSignature(), true);
            if (isNotBlank(template.getBcc())) {
                helper.setBcc(template.getBcc().toArray(new String[0]));
            }
            if (isNotBlank(template.getCc())) {
                helper.setCc(template.getCc().toArray(new String[0]));
            }

            List<FileSystemResource> fileSystemResourceList = template.getFilename().stream()
                    .map(fileName -> new FileSystemResource(new File(fileName)))
                    .toList();

            if (!fileSystemResourceList.isEmpty()) {
                for (FileSystemResource fileResource : fileSystemResourceList) {
                    helper.addAttachment(Objects.requireNonNull(fileResource.getFilename()), fileResource);
                }
            }

            sender.send(mimeMessage);


        } catch (MessagingException e) {

            throw new RuntimeException("Failed to send email", e);
        }
    }

    private static boolean isNotBlank(List<String> fileNames){
        return fileNames != null && !fileNames.isEmpty() && fileNames.stream().allMatch(fileName -> fileName != null && !fileName.trim().isEmpty());
    }
}

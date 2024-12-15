package com.NNTeachie.service;


import com.NNTeachie.Dtos.EmailTemplate;
import org.springframework.web.multipart.MultipartFile;

public interface EmailSenderService {

    public void sendToEmail(EmailTemplate template);
}

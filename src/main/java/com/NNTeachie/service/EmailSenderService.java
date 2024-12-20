package com.NNTeachie.service;


import com.NNTeachie.model.EmailTemplate;

public interface EmailSenderService {

    public void sendToEmail(EmailTemplate template);
}

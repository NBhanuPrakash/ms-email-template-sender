package com.NNTeachie.controller;

import com.NNTeachie.model.EmailTemplate;
import com.NNTeachie.service.EmailSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailSenderController {

    @Autowired
    private EmailSenderServiceImpl service;

    @PostMapping("/sendEmail")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailTemplate emailTemplate) {
        service.sendToEmail(emailTemplate);
        return ResponseEntity.noContent().build();
    }
}

package com.alerthub.sendersmsservice.controller;

import com.alerthub.sendersmsservice.dto.SmsRequest;
import com.alerthub.sendersmsservice.dto.SmsResponse;
import com.alerthub.sendersmsservice.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<SmsResponse> sendSms(@RequestBody SmsRequest request) {
        SmsResponse response = smsService.sendSms(request);
        return ResponseEntity.ok(response);
    }
}
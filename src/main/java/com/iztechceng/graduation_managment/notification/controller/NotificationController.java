package com.iztechceng.graduation_managment.notification.controller;

import com.iztechceng.graduation_managment.notification.model.dto.request.NotificationRequest;
import com.iztechceng.graduation_managment.notification.service.NotificationService;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.exceptions.UserNotFoundException;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        String senderEmail = notificationRequest.getSenderEmail();
        String receiverEmail = notificationRequest.getReceiverEmail();
        String message = notificationRequest.getMessage();

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        notificationService.sendNotification(sender, receiver, message);

        return ResponseEntity.ok("Notification sent successfully!");
    }

}

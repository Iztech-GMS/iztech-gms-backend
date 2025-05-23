package com.iztechceng.graduation_managment.notification.controller;

import com.iztechceng.graduation_managment.notification.model.dto.request.NotificationRequest;
import com.iztechceng.graduation_managment.notification.model.dto.response.NotificationResponse;
import com.iztechceng.graduation_managment.notification.service.NotificationService;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.model.exceptions.UserNotFoundException;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PreAuthorize("hasAnyRole('ADVISOR','STUDENT', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> sendNotification(Principal principal, @RequestBody NotificationRequest notificationRequest) {
        String senderEmail = principal.getName();
        String receiverEmail = notificationRequest.getReceiverEmail();
        String message = notificationRequest.getMessage();
        return ResponseEntity.ok(notificationService.sendNotification(senderEmail, receiverEmail, message));

    }

    @PreAuthorize("hasAnyRole('ADVISOR','STUDENT', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @GetMapping("/my-messages")
    public ResponseEntity<?> getMyMessages(Principal principal) {
        String email = principal.getName();
        List<NotificationResponse> notifications = notificationService.getNotificationsForUser(email);
        return ResponseEntity.ok(notifications);

    }

    @PreAuthorize("hasAnyRole('ADVISOR','STUDENT', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @PreAuthorize("hasAnyRole('ADVISOR','STUDENT', 'STUDENTAFFAIRS', 'DEAN', 'SECRETARY')")
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read successfully");
    }


}

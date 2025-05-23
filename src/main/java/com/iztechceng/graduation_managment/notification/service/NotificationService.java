package com.iztechceng.graduation_managment.notification.service;

import com.iztechceng.graduation_managment.notification.model.dto.response.NotificationResponse;
import com.iztechceng.graduation_managment.notification.model.entity.Notification;
import com.iztechceng.graduation_managment.notification.repository.NotificationRepository;
import com.iztechceng.graduation_managment.user.model.User;
import com.iztechceng.graduation_managment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // Method to send a notification
    public NotificationResponse sendNotification(String senderEmail, String receiverEmail, String message) {
       User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        // Save notification to database
        return toDto(notificationRepository.save(notification));
    }

    public NotificationResponse toDto(Notification notification) {
        return NotificationResponse.builder()
                .notifId(notification.getNotifId())
                .message(notification.getMessage())
                .senderName(notification.getSender() != null ? notification.getSender().getName() : null)
                .senderMail(notification.getSender() != null ? notification.getSender().getEmail() : null)
                .receiverMail(notification.getReceiver() != null ? notification.getReceiver().getEmail() : null)
                .receiverName(notification.getReceiver() != null ? notification.getReceiver().getName() : null)
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .build();
    }

    // Method to get notifications for a user (optional)
    public List<NotificationResponse> getNotificationsForUser(String email) {
        return notificationRepository.findByReceiver(email).stream().map(this::toDto).toList();
    }

    // Method to delete notifications if needed
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

}

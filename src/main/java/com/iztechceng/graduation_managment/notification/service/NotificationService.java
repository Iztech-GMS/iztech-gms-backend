package com.iztechceng.graduation_managment.notification.service;

import com.iztechceng.graduation_managment.notification.model.entity.Notification;
import com.iztechceng.graduation_managment.notification.repository.NotificationRepository;
import com.iztechceng.graduation_managment.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Method to send a notification
    public Notification sendNotification(User sender, User receiver, String message) {
        // Create notification object
        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        // Save notification to database
        return notificationRepository.save(notification);
    }

    // Method to get notifications for a user (optional)
    public List<Notification> getNotificationsForUser(User user) {
        // Fetch notifications for a user
        return notificationRepository.findByReceiver(user);
    }

    // Method to delete notifications if needed
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}

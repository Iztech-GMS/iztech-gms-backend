package com.iztechceng.graduation_managment.notification.repository;

import com.iztechceng.graduation_managment.notification.model.entity.Notification;
import com.iztechceng.graduation_managment.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find notifications by user ID or other criteria

    // Custom query to fetch notifications for a specific user
    List<Notification> findByReceiver(User receiver);

}

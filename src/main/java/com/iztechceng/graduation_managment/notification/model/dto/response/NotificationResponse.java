package com.iztechceng.graduation_managment.notification.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long notifId;
    private String message;


    private String senderName;
    private String senderMail;


    private String receiverName;
    private String receiverMail;

    private LocalDateTime createdAt;

    private boolean isRead = false;
}


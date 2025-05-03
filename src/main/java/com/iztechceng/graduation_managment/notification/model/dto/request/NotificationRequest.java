package com.iztechceng.graduation_managment.notification.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationRequest {
    @NotBlank
    private String message;
    @NotBlank
    private String senderEmail;
    @NotBlank
    private String receiverEmail;
}

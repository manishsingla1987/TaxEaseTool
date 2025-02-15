
package com.taxfiling.itr.controller;

import com.taxfiling.itr.model.Notification;
import com.taxfiling.itr.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmailNotification(@RequestBody EmailNotificationRequest request) {
        notificationService.createNotification(request.getUserId(), request.getMessage(), "EMAIL");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sms")
    public ResponseEntity<?> sendSmsNotification(@RequestBody SmsNotificationRequest request) {
        notificationService.createNotification(request.getUserId(), request.getMessage(), "SMS");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<?> getUnreadNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<?> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    public static class EmailNotificationRequest {
        private Long userId;
        private String message;

        public Long getUserId() { return userId; }
        public String getMessage() { return message; }
    }

    public static class SmsNotificationRequest {
        private Long userId;
        private String message;

        public Long getUserId() { return userId; }
        public String getMessage() { return message; }
    }
}

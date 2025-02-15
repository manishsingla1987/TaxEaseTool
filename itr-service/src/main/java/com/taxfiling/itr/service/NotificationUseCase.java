
package com.taxfiling.itr.service;

import com.taxfiling.itr.model.Notification;
import java.util.List;

public interface NotificationUseCase {
    void createNotification(Long userId, String message, String type);
    List<Notification> getUserNotifications(Long userId);
    List<Notification> getUnreadNotifications(Long userId);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    void sendRealTimeNotification(Long userId, String message);
}


package com.taxfiling.itr.service;

import com.taxfiling.itr.model.AuditLog;
import java.util.List;

public interface AuditLogUseCase {
    void logUserAction(Long userId, String action, String details);
    List<AuditLog> getUserActivityLogs(Long userId);
    void trackSystemEvent(String eventType, String eventDetails);
    List<AuditLog> getSystemLogs(String eventType);
}

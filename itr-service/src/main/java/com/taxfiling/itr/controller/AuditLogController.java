
package com.taxfiling.itr.controller;

import com.taxfiling.itr.model.AuditLog;
import com.taxfiling.itr.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/logs/{userId}")
    public ResponseEntity<?> getUserLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getUserLogs(userId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAuditLog(@RequestBody AuditLogRequest request, HttpServletRequest httpRequest) {
        AuditLog log = auditLogService.createLog(
            request.getUserId(),
            request.getAction(),
            request.getDetails(),
            httpRequest.getRemoteAddr()
        );
        return ResponseEntity.ok(log);
    }

    public static class AuditLogRequest {
        private Long userId;
        private String action;
        private String details;

        public Long getUserId() { return userId; }
        public String getAction() { return action; }
        public String getDetails() { return details; }
    }
}

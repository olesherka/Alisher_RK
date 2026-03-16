package com.example.alisher_rk.audit;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(String action, String entity, String entityId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth == null ? "unknown" : auth.getName();
        auditLogRepository.save(new AuditLog(Instant.now(), username, action, entity, entityId));
    }
}
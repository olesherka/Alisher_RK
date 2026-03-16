package com.example.alisher_rk.audit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "audit_logs")
public class AuditLog {
    @Id
    private String id;

    private Instant createdAt;
    private String username;
    private String action;
    private String entity;
    private String entityId;

    public AuditLog() {
    }

    public AuditLog(Instant createdAt, String username, String action, String entity, String entityId) {
        this.createdAt = createdAt;
        this.username = username;
        this.action = action;
        this.entity = entity;
        this.entityId = entityId;
    }

    public String getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }
    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
}
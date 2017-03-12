package com.app.services;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.app.model.AuditRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs082r on 1/2/2017.
 */
public class AuditService {

    private static AuditService auditService;

    List<AuditRecord> auditRecords;

    private AuditService() {
        auditRecords = new ArrayList<AuditRecord>();
    }

    public static AuditService getInstance() {
        if (auditService == null) {
            auditService = new AuditService();
        }
        return auditService;
    }

    public void AddAuditRecord(Integer userId, String resourceId, String action, String description) {
        AuditRecord record = new AuditRecord();
        record.setUserId(userId);
        record.setResourceId(resourceId);
        record.setAction(action);
        record.setDescription(description);

        auditRecords.add(record);
    }
}

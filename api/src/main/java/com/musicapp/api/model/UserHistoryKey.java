package com.musicapp.api.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@PrimaryKeyClass
public class UserHistoryKey implements Serializable {
    
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant timestamp;

    // Constructores
    public UserHistoryKey() {}
    public UserHistoryKey(UUID userId, Instant timestamp) {
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters y Setters
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
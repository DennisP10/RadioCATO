package com.musicapp.api.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@PrimaryKeyClass
public class UserSearchKey implements Serializable {

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @PrimaryKeyColumn(name = "search_time", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant searchTime;

    // Constructores, Getters y Setters
    public UserSearchKey() {}
    public UserSearchKey(UUID userId, Instant searchTime) {
        this.userId = userId;
        this.searchTime = searchTime;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public Instant getSearchTime() { return searchTime; }
    public void setSearchTime(Instant searchTime) { this.searchTime = searchTime; }
}
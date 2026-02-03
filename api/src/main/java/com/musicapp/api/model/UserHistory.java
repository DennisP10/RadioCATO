package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;

@Table("reproductions_by_user")
public class UserHistory {

    @PrimaryKey
    private UserHistoryKey key;

    @Column("song_id")
    private UUID songId;

    @Column("device_type")
    private String deviceType;

    // Getters y Setters
    public UserHistoryKey getKey() { return key; }
    public void setKey(UserHistoryKey key) { this.key = key; }
    public UUID getSongId() { return songId; }
    public void setSongId(UUID songId) { this.songId = songId; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
}
package com.musicapp.api.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.io.Serializable;
import java.util.UUID;

@PrimaryKeyClass
public class PlaylistsByUserKey implements Serializable {

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @PrimaryKeyColumn(name = "playlist_name", type = PrimaryKeyType.CLUSTERED)
    private String playlistName;

    @PrimaryKeyColumn(name = "playlist_id", type = PrimaryKeyType.CLUSTERED)
    private UUID playlistId;

    // --- CONSTRUCTORES, GETTERS Y SETTERS ---
    public PlaylistsByUserKey() {}

    public PlaylistsByUserKey(UUID userId, String playlistName, UUID playlistId) {
        this.userId = userId;
        this.playlistName = playlistName;
        this.playlistId = playlistId;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }

    public UUID getPlaylistId() { return playlistId; }
    public void setPlaylistId(UUID playlistId) { this.playlistId = playlistId; }
}
package com.musicapp.api.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.io.Serializable;
import java.time.Instant; // Usamos Instant para TIMESTAMP
import java.util.UUID;

@PrimaryKeyClass
public class SongsByPlaylistKey implements Serializable {

    @PrimaryKeyColumn(name = "playlist_id", type = PrimaryKeyType.PARTITIONED)
    private UUID playlistId;

    // Orden Descendente (lo más nuevo arriba)
    @PrimaryKeyColumn(name = "added_at", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant addedAt;

    @PrimaryKeyColumn(name = "song_id", type = PrimaryKeyType.CLUSTERED)
    private UUID songId;

    // --- CONSTRUCTORES ---
    public SongsByPlaylistKey() {}
    
    // Getters y Setters
    public UUID getPlaylistId() { return playlistId; }
    public void setPlaylistId(UUID playlistId) { this.playlistId = playlistId; }

    public Instant getAddedAt() { return addedAt; }
    public void setAddedAt(Instant addedAt) { this.addedAt = addedAt; }

    public UUID getSongId() { return songId; }
    public void setSongId(UUID songId) { this.songId = songId; }
}
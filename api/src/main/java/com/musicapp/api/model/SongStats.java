package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;

@Table("song_stats")
public class SongStats {

    @PrimaryKey("song_id")
    private UUID songId;

    // En Java, 'counter' se mapea como 'long'
    @Column("total_plays")
    private long totalPlays;

    // --- Getters y Setters ---
    public UUID getSongId() { return songId; }
    public void setSongId(UUID songId) { this.songId = songId; }

    public long getTotalPlays() { return totalPlays; }
    public void setTotalPlays(long totalPlays) { this.totalPlays = totalPlays; }
}
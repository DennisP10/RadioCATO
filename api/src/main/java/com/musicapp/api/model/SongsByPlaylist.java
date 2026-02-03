package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.annotation.Transient;

@Table("songs_by_playlist")
public class SongsByPlaylist {

    @PrimaryKey
    private SongsByPlaylistKey key;

    private String title;

    @Column("artist_name")
    private String artistName;

    private int duration;

    // --- ¡NO AGREGUES fileUrl AQUÍ! ---

    public SongsByPlaylistKey getKey() { return key; }
    public void setKey(SongsByPlaylistKey key) { this.key = key; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @Transient // Esto le dice a Cassandra: "Ignora esto, no es una columna real, es solo para el JSON"
    private Long playCount = 0L;

    public Long getPlayCount() { return playCount; }
    public void setPlayCount(Long playCount) { this.playCount = playCount; }
}
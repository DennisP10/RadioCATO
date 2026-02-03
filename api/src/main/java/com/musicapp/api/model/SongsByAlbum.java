package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.Set;

@Table("songs_by_album")
public class SongsByAlbum {
    @PrimaryKey
    private SongsByAlbumKey key;

    private String title;
    private int duration;
    private Set<String> features;

    // Getters y Setters
    public SongsByAlbumKey getKey() { return key; }
    public void setKey(SongsByAlbumKey key) { this.key = key; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}
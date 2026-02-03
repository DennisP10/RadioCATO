package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.List;
import java.util.UUID;

@Table("songs")
public class Song {
    @PrimaryKey("song_id")
    private UUID songId;

    private String title;

    @Column("file_url")
    private String fileUrl;

    private String lyrics;

    // Mapeo de 'embedding list<float>'
    private List<Float> embedding;

    // Getters y Setters
    public UUID getSongId() { return songId; }
    public void setSongId(UUID songId) { this.songId = songId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
    public List<Float> getEmbedding() { return embedding; }
    public void setEmbedding(List<Float> embedding) { this.embedding = embedding; }
}
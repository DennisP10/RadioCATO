package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.LocalDate;
import java.util.UUID;

@Table("albums")
public class Album {
    @PrimaryKey("album_id")
    private UUID albumId;

    private String title;

    @Column("artist_name")
    private String artistName;

    @Column("cover_url")
    private String coverUrl;

    @Column("release_date")
    private LocalDate releaseDate;

    // Getters y Setters
    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
}
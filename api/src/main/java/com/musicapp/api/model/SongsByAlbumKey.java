package com.musicapp.api.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.io.Serializable;
import java.util.UUID;

@PrimaryKeyClass
public class SongsByAlbumKey implements Serializable {
    @PrimaryKeyColumn(name = "album_id", type = PrimaryKeyType.PARTITIONED)
    private UUID albumId;

    @PrimaryKeyColumn(name = "track_number", type = PrimaryKeyType.CLUSTERED)
    private int trackNumber;

    @PrimaryKeyColumn(name = "song_id", type = PrimaryKeyType.CLUSTERED)
    private UUID songId;

    // Getters y Setters
    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }
    public int getTrackNumber() { return trackNumber; }
    public void setTrackNumber(int trackNumber) { this.trackNumber = trackNumber; }
    public UUID getSongId() { return songId; }
    public void setSongId(UUID songId) { this.songId = songId; }
}
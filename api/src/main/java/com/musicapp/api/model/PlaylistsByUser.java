package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("playlists_by_user")
public class PlaylistsByUser {

    @PrimaryKey
    private PlaylistsByUserKey key;

    @Column("total_songs")
    private int totalSongs;

    @Column("is_public")
    private boolean isPublic;

    // --- GETTERS Y SETTERS ---
    public PlaylistsByUserKey getKey() { return key; }
    public void setKey(PlaylistsByUserKey key) { this.key = key; }

    public int getTotalSongs() { return totalSongs; }
    public void setTotalSongs(int totalSongs) { this.totalSongs = totalSongs; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
}
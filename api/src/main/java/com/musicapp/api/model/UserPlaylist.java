package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("playlists_by_user")
public class UserPlaylist {
    @PrimaryKey
    private UserPlaylistKey key;

    @Column("is_public")
    private boolean isPublic;

    @Column("total_songs")
    private int totalSongs;

    public UserPlaylistKey getKey() { return key; }
    public void setKey(UserPlaylistKey key) { this.key = key; }
    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public int getTotalSongs() { return totalSongs; }
    public void setTotalSongs(int totalSongs) { this.totalSongs = totalSongs; }
}
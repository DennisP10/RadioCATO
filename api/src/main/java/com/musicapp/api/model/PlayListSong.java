package com.musicapp.api.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("songs_by_playlist")
public class PlayListSong {

    @PrimaryKey
    private PlayListSongKey key;

    private String title;

    @Column("artist_name")
    private String artistName;

    private Integer duration;
}
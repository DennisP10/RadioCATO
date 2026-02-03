package com.musicapp.api.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("albums_by_artist")
public class AlbumByArtist {

    @PrimaryKey
    private AlbumByArtistKey key;

    @Column("album_name")
    private String albumName;

    @Column("cover_url")
    private String coverUrl;

    @Column("artist_name")
    private String artistName;
}
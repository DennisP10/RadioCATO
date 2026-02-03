package com.musicapp.api.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;
import java.util.UUID;

@Data
@Table("playlists")
public class Playlist {

    @PrimaryKey("playlist_id")
    private UUID playlistId;

    private String name;
    private String description;

    @Column("owner_id")
    private UUID ownerId;

    @Column("is_public")
    private Boolean isPublic;
}
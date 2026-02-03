package com.musicapp.api.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@PrimaryKeyClass
public class PlayListSongKey implements Serializable {

    @PrimaryKeyColumn(name = "playlist_id", type = PrimaryKeyType.PARTITIONED)
    private UUID playlistId;

    @PrimaryKeyColumn(name = "added_at", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant addedAt;

    @PrimaryKeyColumn(name = "song_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID songId;
}
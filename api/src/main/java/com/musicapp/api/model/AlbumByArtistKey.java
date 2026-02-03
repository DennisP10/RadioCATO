package com.musicapp.api.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@PrimaryKeyClass
public class AlbumByArtistKey implements Serializable {

    @PrimaryKeyColumn(name = "artist_id", type = PrimaryKeyType.PARTITIONED)
    private UUID artistId;

    @PrimaryKeyColumn(name = "release_date", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDate releaseDate;

    @PrimaryKeyColumn(name = "album_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID albumId;
}
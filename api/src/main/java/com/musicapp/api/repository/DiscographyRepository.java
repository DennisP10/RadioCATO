package com.musicapp.api.repository;

import com.musicapp.api.model.AlbumByArtist;
import com.musicapp.api.model.AlbumByArtistKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DiscographyRepository extends CassandraRepository<AlbumByArtist, AlbumByArtistKey> {
    List<AlbumByArtist> findByKeyArtistId(UUID artistId);
}
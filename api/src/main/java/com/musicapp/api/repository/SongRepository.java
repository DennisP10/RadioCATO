package com.musicapp.api.repository;

import com.musicapp.api.model.Song;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SongRepository extends CassandraRepository<Song, UUID> {
    // Soporta Q13
}
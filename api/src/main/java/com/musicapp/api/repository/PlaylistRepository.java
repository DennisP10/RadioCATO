package com.musicapp.api.repository;

import com.musicapp.api.model.Playlist;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PlaylistRepository extends CassandraRepository<Playlist, UUID> {
}
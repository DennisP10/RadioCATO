package com.musicapp.api.repository;

import com.musicapp.api.model.Album;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AlbumRepository extends CassandraRepository<Album, UUID> {
    // Métodos automáticos
}
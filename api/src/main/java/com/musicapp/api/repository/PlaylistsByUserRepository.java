package com.musicapp.api.repository;

import com.musicapp.api.model.PlaylistsByUser;
import com.musicapp.api.model.PlaylistsByUserKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlaylistsByUserRepository extends CassandraRepository<PlaylistsByUser, PlaylistsByUserKey> {
    
    // Busca todas las playlists de un usuario específico
    List<PlaylistsByUser> findByKeyUserId(UUID userId);
}
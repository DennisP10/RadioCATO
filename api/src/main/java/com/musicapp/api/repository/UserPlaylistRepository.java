package com.musicapp.api.repository;

import com.musicapp.api.model.UserPlaylist;
import com.musicapp.api.model.UserPlaylistKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserPlaylistRepository extends CassandraRepository<UserPlaylist, UserPlaylistKey> {
    // Q7: Buscar todas las playlists de un usuario
    List<UserPlaylist> findByKeyUserId(UUID userId);
}
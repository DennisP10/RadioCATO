package com.musicapp.api.repository;

import com.musicapp.api.model.SongsByPlaylist;
import com.musicapp.api.model.SongsByPlaylistKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SongsByPlaylistRepository extends CassandraRepository<SongsByPlaylist, SongsByPlaylistKey> {

    // Trae todas las canciones de una playlist
    List<SongsByPlaylist> findByKeyPlaylistId(UUID playlistId);
}
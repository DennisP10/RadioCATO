package com.musicapp.api.repository;

import com.musicapp.api.model.PlayListSong;
import com.musicapp.api.model.PlayListSongKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlaylistSongRepository extends CassandraRepository<PlayListSong, PlayListSongKey> {
    List<PlayListSong> findByKeyPlaylistId(UUID playlistId);
}
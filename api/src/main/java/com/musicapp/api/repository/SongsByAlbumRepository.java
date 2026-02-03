package com.musicapp.api.repository;

import com.musicapp.api.model.SongsByAlbum;
import com.musicapp.api.model.SongsByAlbumKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SongsByAlbumRepository extends CassandraRepository<SongsByAlbum, SongsByAlbumKey> {
    List<SongsByAlbum> findByKeyAlbumId(UUID albumId);
}
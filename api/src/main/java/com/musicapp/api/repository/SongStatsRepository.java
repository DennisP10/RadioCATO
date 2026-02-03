package com.musicapp.api.repository;

import com.musicapp.api.model.SongStats;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List; // Agregado

@Repository
public interface SongStatsRepository extends CassandraRepository<SongStats, UUID> {
    
    @Query("UPDATE song_stats SET total_plays = total_plays + 1 WHERE song_id = ?0")
    void incrementPlays(UUID songId);

    // No necesitas implementar nada, CassandraRepository ya nos da findAll()
}
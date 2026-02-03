package com.musicapp.api.repository;

import com.musicapp.api.model.Artist;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistRepository extends CassandraRepository<Artist, UUID> {
    // Q3 y Q10: El nombre del método DEBE coincidir con la variable 'name' del modelo
    List<Artist> findByName(String name);
}
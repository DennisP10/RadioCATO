package com.musicapp.api.repository;

import com.musicapp.api.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {
    // Spring Data genera automáticamente el método findById(UUID id)
    // No necesitas escribir nada más aquí.
}
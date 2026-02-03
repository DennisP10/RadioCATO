package com.musicapp.api.repository;

import com.musicapp.api.model.UserByEmail;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserByEmailRepository extends CassandraRepository<UserByEmail, String> {
    // Spring Data implementa findById(email) automáticamente
}
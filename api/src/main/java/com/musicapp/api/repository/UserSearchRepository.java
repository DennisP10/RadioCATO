package com.musicapp.api.repository;

import com.musicapp.api.model.UserSearch;
import com.musicapp.api.model.UserSearchKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserSearchRepository extends CassandraRepository<UserSearch, UserSearchKey> {
    // Q12: Buscar historial de búsqueda por ID de usuario
    List<UserSearch> findByKeyUserId(UUID userId);
}
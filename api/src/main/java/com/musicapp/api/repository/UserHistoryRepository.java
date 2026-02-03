package com.musicapp.api.repository;

import com.musicapp.api.model.UserHistory;
import com.musicapp.api.model.UserHistoryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserHistoryRepository extends CassandraRepository<UserHistory, UserHistoryKey> {
    // Q5: Buscar historial por ID de usuario
    List<UserHistory> findByKeyUserId(UUID userId);
}
package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;

@Table("users_by_email") // Nombre exacto de tu tabla
public class UserByEmail {

    @PrimaryKey
    private String email;

    @Column("user_id")
    private UUID userId;

    private String password;

    @Column("session_info")
    private String sessionInfo;

    // --- Getters y Setters ---
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSessionInfo() { return sessionInfo; }
    public void setSessionInfo(String sessionInfo) { this.sessionInfo = sessionInfo; }
}
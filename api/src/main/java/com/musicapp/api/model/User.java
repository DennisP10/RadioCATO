package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Table("users")
public class User {

    @PrimaryKey("user_id")
    private UUID userId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("avatar_url")
    private String avatarUrl;

    @Column("registration_date")
    private Instant registrationDate;

    // Getters y Setters
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
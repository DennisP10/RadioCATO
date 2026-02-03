package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Table("artists")
public class Artist {
    @PrimaryKey("artist_id")
    private UUID artistId;

    private String name;

    @Column("bio_full")
    private String bioFull;

    // Mapeo de 'metrics map<text, int>'
    private Map<String, Integer> metrics; 

    // Mapeo de 'genres set<text>'
    private Set<String> genres;

    // Getters y Setters (Omitidos por brevedad, genéralos o pídemelos)
    public UUID getArtistId() { return artistId; }
    public void setArtistId(UUID artistId) { this.artistId = artistId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBioFull() { return bioFull; }
    public void setBioFull(String bioFull) { this.bioFull = bioFull; }
    public Map<String, Integer> getMetrics() { return metrics; }
    public void setMetrics(Map<String, Integer> metrics) { this.metrics = metrics; }
    public Set<String> getGenres() { return genres; }
    public void setGenres(Set<String> genres) { this.genres = genres; }
}
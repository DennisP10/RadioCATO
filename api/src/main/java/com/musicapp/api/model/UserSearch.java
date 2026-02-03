package com.musicapp.api.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("searches_by_user")
public class UserSearch {

    @PrimaryKey
    private UserSearchKey key;

    @Column("query_text")
    private String queryText;

    @Column("results_count")
    private int resultsCount;

    // Getters y Setters
    public UserSearchKey getKey() { return key; }
    public void setKey(UserSearchKey key) { this.key = key; }
    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }
    public int getResultsCount() { return resultsCount; }
    public void setResultsCount(int resultsCount) { this.resultsCount = resultsCount; }
}
package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "parentGenre"})
public class GenreResource {
    @JsonProperty("name")
    private String name;
    @JsonProperty("parentGenre")
    private String parentGenre;

    public GenreResource() { /*needed for Jackson*/ }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentGenre() {
        return this.parentGenre;
    }

    public void setParentGenre(String parentGenre) {
        this.parentGenre = parentGenre;
    }
}

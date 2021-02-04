package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"artistName", "artistInfo"})
public class ArtistResource {
    @JsonProperty("artistName")
    private String artistName;
    @JsonProperty("artistInfo")
    private String artistInfo;


    public ArtistResource() { /*needed for Jackson*/ }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistInfo() {
        return artistInfo;
    }

    public void setArtistInfo(String artistInfo) {
        this.artistInfo = artistInfo;
    }
}

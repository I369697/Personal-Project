package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"albumName", "artistName"})
public class AlbumResource {

    @JsonProperty("albumName")
    private String albumName;
    @JsonProperty("artistName")
    private String artistName;


    public AlbumResource() { /*needed for Jackson*/ }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}

package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"trackName", "genre", "albumNames", "artistNames", "trackInfo"})
public class TrackResource {

    @JsonProperty("trackName")
    private String trackName;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("albumNames")
    private String albumName;
    @JsonProperty("artistName")
    private String artistName;
    @JsonProperty("trackInfo")
    private String trackInfo;


    public TrackResource() { /*needed for Jackson*/ }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

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

    public String getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(String trackInfo) {
        this.trackInfo = trackInfo;
    }
}

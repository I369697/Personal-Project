package com.songr.services.contract.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"albums", "artists", "genres", "tracks"})
public class PreferenceResource {
    @JsonProperty("albums")
    private List<AlbumResource> albums;
    @JsonProperty("artists")
    private List<ArtistResource> artists;
    @JsonProperty("genres")
    private List<GenreResource> genres;
    @JsonProperty("tracks")
    private List<TrackResource> tracks;

    public PreferenceResource() { /*needed for Jackson*/ }

    public List<AlbumResource> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumResource> albums) {
        this.albums = albums;
    }

    public List<ArtistResource> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistResource> artists) {
        this.artists = artists;
    }

    public List<GenreResource> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResource> genres) {
        this.genres = genres;
    }

    public List<TrackResource> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackResource> tracks) {
        this.tracks = tracks;
    }
}

package com.songr.services.contract.assembler;

import com.songr.services.contract.resource.PreferenceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PreferenceResourceAssembler {
    @Autowired
    private AlbumResourceAssembler albumResourceAssembler;
    @Autowired
    private ArtistResourceAssembler artistResourceAssembler;
    @Autowired
    private GenreResourceAssembler genreResourceAssembler;
    @Autowired
    private TrackResourceAssembler trackResourceAssembler;

    public PreferenceResource toResource(Map<String, Object> data) {
        PreferenceResource preferenceResource = new PreferenceResource();

        preferenceResource.setAlbums(albumResourceAssembler.toResources((List<Map<String, Object>>) data.get("albums")));
        preferenceResource.setArtists(artistResourceAssembler.toResources((List<Map<String, Object>>) data.get("artists")));
        preferenceResource.setGenres(genreResourceAssembler.toResources((List<Map<String, Object>>) data.get("genres")));
        preferenceResource.setTracks(trackResourceAssembler.toResources((List<Map<String, Object>>) data.get("tracks")));

        return preferenceResource;
    }
}

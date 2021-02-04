package com.songr.services.contract.assembler;

import com.songr.services.contract.resource.TrackResource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class TrackResourceAssembler {

    public TrackResource toResource(Map<String, Object> data) {
        TrackResource trackResource = new TrackResource();

        trackResource.setTrackName((String) data.get("trackName"));
        trackResource.setGenre((String) data.get("genre"));
        trackResource.setAlbumName((String) data.get("albumName"));
        trackResource.setArtistName((String) data.get("artistName"));
        trackResource.setTrackInfo((String) data.get("trackInfo"));

        return trackResource;
    }

    public List<TrackResource> toResources(List<Map<String, Object>> data) {
        List<TrackResource> tracks = new LinkedList<>();

        for (Map<String, Object> d : data) {
            TrackResource trackResource = new TrackResource();

            trackResource.setTrackName((String) d.get("trackName"));
            trackResource.setGenre((String) d.get("genre"));
            trackResource.setAlbumName((String) d.get("albumName"));
            trackResource.setArtistName((String) d.get("artistName"));
            trackResource.setTrackInfo((String) d.get("trackInfo"));
        }
        return tracks;
    }
}

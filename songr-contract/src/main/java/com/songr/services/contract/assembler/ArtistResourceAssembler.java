package com.songr.services.contract.assembler;

import com.songr.services.contract.resource.ArtistResource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ArtistResourceAssembler {

    public ArtistResource toResource(Map<String, Object> data) {
        ArtistResource artistResource = new ArtistResource();

        artistResource.setArtistName((String) data.get("artistName"));
        artistResource.setArtistInfo((String) data.get("artistInfo"));

        return artistResource;
    }

    public List<ArtistResource> toResources(List<Map<String, Object>> data) {
        List<ArtistResource> artists = new LinkedList<>();

        for (Map<String, Object> d : data) {
            ArtistResource artistResource = new ArtistResource();

            artistResource.setArtistName((String) d.get("artistName"));
            artistResource.setArtistInfo((String) d.get("artistInfo"));
            artists.add(artistResource);
        }

        return artists;
    }
}

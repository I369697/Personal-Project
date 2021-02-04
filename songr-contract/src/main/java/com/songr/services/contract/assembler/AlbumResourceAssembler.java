package com.songr.services.contract.assembler;

import com.songr.services.contract.resource.AlbumResource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class AlbumResourceAssembler {

    public AlbumResource toResource(Map<String, Object> data) {
        AlbumResource albumResource = new AlbumResource();

        albumResource.setAlbumName((String) data.get("albumName"));
        albumResource.setArtistName((String) data.get("artistName"));

        return albumResource;
    }

    public List<AlbumResource> toResources(List<Map<String, Object>> data) {
        List<AlbumResource> albums = new LinkedList<>();

        for (Map<String, Object> d : data) {
            AlbumResource albumResource = new AlbumResource();

            albumResource.setAlbumName((String) d.get("albumName"));
            albumResource.setArtistName((String) d.get("artistName"));
            albums.add(albumResource);
        }

        return albums;
    }
}

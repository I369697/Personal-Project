package com.songr.services.contract.assembler;

import com.songr.services.contract.resource.GenreResource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class GenreResourceAssembler {

    public GenreResource toResource(Map<String, Object> data) {
        GenreResource genreResource = new GenreResource();

        genreResource.setName((String) data.get("name"));
        genreResource.setParentGenre((String) data.get("parentGenre"));

        return genreResource;
    }

    public List<GenreResource> toResources(List<Map<String, Object>> data) {
        List<GenreResource> genres = new LinkedList<>();

        for (Map<String, Object> d : data) {
            GenreResource genreResource = new GenreResource();

            genreResource.setName((String) d.get("name"));
            genreResource.setParentGenre((String) d.get("parentGenre"));

            genres.add(genreResource);
        }

        return genres;
    }
}

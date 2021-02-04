package com.songr.services.contract.controller;

import com.songr.services.contract.resource.GenreResource;
import com.songr.services.contract.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable(name = "id", value = "id", required = true) String id,
                       @RequestParam(name = "password", value = "password", required = false) String password) {
        return genreService.test(id, password);
    }

    @RequestMapping(value = "/{token}/get-genre", method = RequestMethod.GET)
    @ResponseBody
    public GenreResource getGenre(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "genre", value = "genre", required = true) String genre) {
        return genreService.getGenre(token, genre);
    }

    @RequestMapping(value = "/{token}/set-genre", method = RequestMethod.GET)
    @ResponseBody
    public GenreResource setGenre(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "genre", value = "genre", required = true) String genre) {
        return genreService.setGenre(token, genre);
    }
}

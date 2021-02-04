package com.songr.services.contract.controller;

import com.songr.services.contract.resource.ArtistResource;
import com.songr.services.contract.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable(name = "id", value = "id", required = true) String id,
                       @RequestParam(name = "password", value = "password", required = false) String password) {
        return artistService.test(id, password);
    }

    @RequestMapping(value = "/{token}/get-artist", method = RequestMethod.GET)
    @ResponseBody
    public ArtistResource getArtist(@PathVariable(name = "token", value = "token", required = true) String token,
                                    @RequestParam(name = "artistName", value = "artistName", required = true) String artistName) {
        return artistService.getArtist(token, artistName);
    }

    @RequestMapping(value = "/{token}/set-artist", method = RequestMethod.GET)
    @ResponseBody
    public ArtistResource setArtist(@PathVariable(name = "token", value = "token", required = true) String token,
                                    @RequestParam(name = "artistName", value = "artistName", required = true) String artistName,
                                    @RequestParam(name = "artistInfo", value = "artistInfo", required = false, defaultValue = "") String artistInfo) {
        return artistService.setArtist(token, artistName, artistInfo);
    }
}

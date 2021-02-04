package com.songr.services.contract.controller;

import com.songr.services.contract.resource.TrackResource;
import com.songr.services.contract.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable(name = "id", value = "id", required = true) String id,
                       @RequestParam(name = "password", value = "password", required = false) String password) {
        return trackService.test(id, password);
    }

    @RequestMapping(value = "/{token}/get-track", method = RequestMethod.GET)
    @ResponseBody
    public TrackResource getTrack(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "trackName", value = "trackName", required = true) String trackName) {
        return trackService.getTrack(token, trackName);
    }

    @RequestMapping(value = "{token}/set-track", method = RequestMethod.GET)
    @ResponseBody
    public TrackResource setTrack(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "trackName", value = "trackName", required = true) String trackName,
                                  @RequestParam(name = "genre", value = "genre", required = true) String genre,
                                  @RequestParam(name = "albumName", value = "albumName", required = true) String albumName,
                                  @RequestParam(name = "artistName", value = "artistName", required = true) String artistName,
                                  @RequestParam(name = "trackInfo", value = "trackInfo", required = false, defaultValue = "") String trackInfo) {
        return trackService.setTrack(token, trackName, genre, albumName, artistName, trackInfo);
    }
}

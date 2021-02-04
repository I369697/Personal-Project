package com.songr.services.contract.controller;

import com.songr.services.contract.resource.AlbumResource;
import com.songr.services.contract.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable(name = "id", value = "id", required = true) String id,
                       @RequestParam(name = "password", value = "password", required = false) String password) {
        return albumService.test(id, password);
    }

    @RequestMapping(value = "/{token}/get-album", method = RequestMethod.GET)
    @ResponseBody
    public AlbumResource getAlbum(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "albumName", value = "albumName", required = true) String albumName) {
        return albumService.getAlbum(token, albumName);
    }

    @RequestMapping(value = "/{token}/set-album", method = RequestMethod.GET)
    @ResponseBody
    public AlbumResource setAlbum(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "artistName", value = "artistName", required = true) String artistName,
                                  @RequestParam(name = "albumName", value = "albumName", required = true) String albumName) {
        return albumService.setAlbum(token, artistName, albumName);
    }
}

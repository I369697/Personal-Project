package com.songr.services.contract.controller;

import com.songr.services.contract.resource.*;
import com.songr.services.contract.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test/{userName}", method = RequestMethod.GET)
    public String test(@PathVariable(name = "userName", value = "userName", required = true) String userName,
                       @RequestParam(name = "password", value = "password", required = false) String password) {
        return userService.test(userName, password);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam(name = "userName", value = "userName", required = true) String userName,
                                   @RequestParam(name = "password", value = "password", required = true) String password) {
        UserResource ur = userService.login(userName, password);
        if (!ur.getUserName().isEmpty())
            return new ResponseEntity<>(ur, HttpStatus.OK);
        else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/{token}/logout", method = RequestMethod.GET)
    public UserResource logout(@PathVariable(name = "token", value = "token", required = true) String token,
                               @RequestParam(name = "userName", value = "userName", required = true) String userName) {
        return userService.logout(token, userName);
    }

    @RequestMapping(value = "/{token}/get-user", method = RequestMethod.GET)
    public UserResource getUser(@PathVariable(name = "token", value = "token", required = true) String token,
                                @RequestParam(name = "userName", value = "userName", required = true) String userName) {
        return userService.getUser(token, userName);
    }

    @RequestMapping(value = "/set-user", method = RequestMethod.POST)
    public ResponseEntity<?> setUser(@RequestParam(name = "userName", value = "userName", required = true) String userName,
                                     @RequestParam(name = "email", value = "email", required = true) String email,
                                     @RequestParam(name = "password", value = "password", required = true) String password) {
        UserResource ur = userService.setUser(userName, email, password);
        if (!ur.getUserName().isEmpty())
            return new ResponseEntity<>(ur, HttpStatus.OK);
        else return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/{token}/edit-user", method = RequestMethod.POST)
    public UserResource editUser(@PathVariable(name = "token", value = "token", required = true) String token,
                                 @RequestParam(name = "userName", value = "userName", required = true) String userName,
                                 @RequestParam(name = "firstName", value = "firstName", required = false) String firstName,
                                 @RequestParam(name = "middleName", value = "middleName", required = false) String middleName,
                                 @RequestParam(name = "lastName", value = "lastName", required = false) String lastName,
                                 @RequestParam(name = "email", value = "email", required = false) String email,
                                 @RequestParam(name = "dateOfBirth", value = "dateOfBirth", required = false) String dateOfBirth,
                                 @RequestParam(name = "gender", value = "gender", required = false) String gender) {
        return userService.editUser(token, userName, firstName, middleName, lastName, email, dateOfBirth, gender);
    }

    @RequestMapping(value = "/{token}/change-password", method = RequestMethod.POST)
    public UserResource changePassword(@PathVariable(name = "token", value = "token", required = true) String token,
                                       @RequestParam(name = "userName", value = "userName", required = true) String userName,
                                       @RequestParam(name = "password", value = "password", required = true) String password) {
        return userService.changePassword(token, userName, password);
    }

    @RequestMapping(value = "/{token}/connect-spotify", method = RequestMethod.POST)
    public void connectSpotify(@PathVariable(name = "token", value = "token", required = true) String token,
                               @RequestParam(name = "spotify_refresh_token", value = "spotify_refresh_token", required = true) String spotifyRefreshToken) {
        userService.connectSpotify(token, spotifyRefreshToken);
    }


//    @RequestMapping(value = "/{token}/upload-thumbnail", method = RequestMethod.POST, consumes = "multipart/form-data")
//    public ResponseEntity<UserResource> uploadThumbnail(@PathVariable(name = "token", value = "token", required = true) String token,
//                                                        @RequestParam(name = "userName", value = "userName", required = true) String userName,
//                                                        @RequestBody ImageForm imageForm) {
//        //TODO (the frontend sends it with 'content-type': 'multipart/form-data' header to be able to send image as base64 string, and as Form Data)
//        //store image
//
//        userService.uploadThumbnail(token, userName, imageForm.getThumbnail());
//        UserResource ur = userService.getUser(token, userName);
//        return ResponseEntity.ok().body(ur);
//    }

    @RequestMapping(value = "/{token}/upload-thumbnail", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<UserResource> uploadThumbnail(@PathVariable(name = "token", value = "token", required = true) String token,
                                                        @RequestParam(name = "userName", value = "userName", required = true) String userName,
                                                        WebRequest thumbNailForm) {
        System.out.println(thumbNailForm);
        //userService.uploadThumbnail(token, userName, imageBlob);
        //UserResource ur = userService.getUser(token, userName);
        // ResponseEntity.ok().body(ur);
        return ResponseEntity.ok().body(null);
    }

    @RequestMapping(value = "/{token}/get-preferences", method = RequestMethod.GET)
    @ResponseBody
    public PreferenceResource getPreferences(@PathVariable(name = "token", value = "token", required = true) String token,
                                             @RequestParam(name = "userId", value = "userId", required = true) int userId) {
        return userService.getPreferences(token, userId);
    }

    @RequestMapping(value = "/{token}/set-album", method = RequestMethod.GET)
    @ResponseBody
    public AlbumResource setAlbum(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "userId", value = "userId", required = true) int userId,
                                  @RequestParam(name = "albumName", value = "albumName", required = true) String albumName) {
        return userService.setAlbum(token, userId, albumName);
    }

    @RequestMapping(value = "/{token}/set-artist", method = RequestMethod.GET)
    @ResponseBody
    public ArtistResource setArtist(@PathVariable(name = "token", value = "token", required = true) String token,
                                    @RequestParam(name = "userId", value = "userId", required = true) int userId,
                                    @RequestParam(name = "artistName", value = "artistName", required = true) String artistName) {
        return userService.setArtist(token, userId, artistName);
    }

    @RequestMapping(value = "/{token}/set-genre", method = RequestMethod.GET)
    @ResponseBody
    public GenreResource setGenre(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "userId", value = "userId", required = true) int userId,
                                  @RequestParam(name = "genre", value = "genre", required = true) String genre) {
        return userService.setGenre(token, userId, genre);
    }

    @RequestMapping(value = "{token}/set-track", method = RequestMethod.GET)
    @ResponseBody
    public TrackResource setTrack(@PathVariable(name = "token", value = "token", required = true) String token,
                                  @RequestParam(name = "userId", value = "userId", required = true) int userId,
                                  @RequestParam(name = "trackName", value = "trackName", required = true) String trackName,
                                  @RequestParam(name = "artistName", value = "artistName", required = true) String artistName) {
        return userService.setTrack(token, userId, trackName, artistName);
    }
}

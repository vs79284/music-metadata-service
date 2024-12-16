package com.assignmnt.ice.controller;

import com.assignmnt.ice.dto.CreateAlbumResponse;
import com.assignmnt.ice.dto.CreateTrackResponse;
import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import com.assignmnt.ice.service.IAlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the controller class for price service REST API path are defined in
 * this class.
 */
@RestController
@RequestMapping(path = "album")
public class AlbumController {

    Logger log = LoggerFactory.getLogger(AlbumController.class);
    @Autowired
    IAlbumService albumService;

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAlbumResponse> addAlbum(
            @RequestBody AlbumRequestBody albumRequestBody) {
        var track = albumService.addAlbum(albumRequestBody);
        return ResponseEntity.ok(CreateAlbumResponse.from(track));
    }

    @PatchMapping(path = "/{albumId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAlbumResponse> updateReleaseDate(
            @RequestBody AlbumRequestBody createTrackRequest) {
        var track = albumService.addAlbum(createTrackRequest);
        return ResponseEntity.ok(CreateAlbumResponse.from(track));
    }


    @PostMapping(path = "{albumId}/tracks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateTrackResponse> addTrackToAlbum(@PathVariable String albumId,
                                                               @RequestBody List<TrackRequestBody> trackRequests) {
        var track = albumService.addTrack(trackRequests);
        return ResponseEntity.ok(CreateTrackResponse.from(track));
    }


    @PostMapping(path = "{albumId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Album>> getAlbums(@PathVariable String albumName) {

        albumService.getAlbums(albumName);
        return ResponseEntity.ok(albumService.getAlbums(albumName));
    }


}

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * This is the controller class for price service REST API path are defined in
 * this class.
 */
@RestController
@RequestMapping(path = "/album")
public class AlbumController {

    Logger log = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    IAlbumService albumService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Album> addAlbum(
            @RequestBody AlbumRequestBody albumRequestBody) {
        Album album = albumService.addAlbum(albumRequestBody);
        URI location = URI.create("/album/"+album.getId());
        return ResponseEntity.created(location).body(album);
    }

    @GetMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Album>> getAlbum(
            @RequestParam String query) {
        var track = albumService.searchAlbumsByTitle(query);
        return ResponseEntity.ok(track);
    }


    @PatchMapping(path = "/{albumId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Album> updateReleaseDate(@PathVariable String albumId,
            @RequestBody AlbumRequestBody createTrackRequest) {
        var track = albumService.setReleaseDate(albumId, createTrackRequest);
        return ResponseEntity.ok(track);
    }


    @PostMapping(path = "/{albumId}/tracks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTrackToAlbum(@PathVariable String albumId,
                                                               @RequestBody List<TrackRequestBody> trackRequests) {
        var album = albumService.addTracksToAlbum(albumId, trackRequests);
        return ResponseEntity.created(null).build();
    }

}

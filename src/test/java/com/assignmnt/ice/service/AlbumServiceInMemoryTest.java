package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.exceptions.AlbumConflictException;
import com.assignmnt.ice.model.request.AlbumRequest;
import com.assignmnt.ice.model.request.TrackRequest;
import com.assignmnt.ice.utils.Helper;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumServiceInMemoryTest {

    Helper helper = new Helper();
    AlbumServiceInMemory albumService = new AlbumServiceInMemory(helper);

    @Test
    void addAlbum() {
        AlbumRequest requestBody = new AlbumRequest("Test Album", "Test Artist", LocalDate.now());

        Album album = albumService.addAlbum(requestBody);
        assertNotNull(album.id());
        assertEquals("Test Album", album.title());
    }

    @Test
    void shouldThrowConflictExceptionWhenAlbumExists() {
        AlbumRequest requestBody = new AlbumRequest("Test Album", "Test Artist", LocalDate.now());

        Album album = albumService.addAlbum(requestBody);
        assertNotNull(album.id());
        assertEquals("Test Album", album.title());
        AlbumConflictException exception = assertThrows(

                AlbumConflictException.class,
                () -> albumService.addAlbum(requestBody)
        );
    }

    @Test
    void addTracksToAlbum() {
        AlbumRequest requestBody = new AlbumRequest("Test Album", "Test Artist", LocalDate.now());
        Album album = albumService.addAlbum(requestBody);
        TrackRequest trackRequest = new TrackRequest("Test Track", Duration.ofMinutes(5), LocalDate.now());
        List<TrackRequest> tracks = new LinkedList<>();
        tracks.add(trackRequest);
        Album updatedAlbum = albumService.addTracksToAlbum(album.id(), tracks);
        assertEquals(1, updatedAlbum.tracks().size());

    }

    @Test
    void setReleaseDate() {
        AlbumRequest requestBody = new AlbumRequest("Test Album", "Test Artist", LocalDate.now());
        Album album = albumService.addAlbum(requestBody);
    }

    @Test
    void isAlbumReleased() {
        AlbumRequest requestBody = new AlbumRequest("Test Album", "Test Artist", LocalDate.now().minusDays(2));
        Album album = albumService.addAlbum(requestBody);
        assertTrue(albumService.isAlbumReleased(album.id()));
    }

    @Test
    void searchAlbumsByTitle() {
        AlbumRequest requestBody = new AlbumRequest("Test Album", "Test Artist", LocalDate.now());
        Album album = albumService.addAlbum(requestBody);
        TrackRequest trackRequest = new TrackRequest("Test Track", Duration.ofMinutes(5), LocalDate.now());
        List<TrackRequest> tracks = new LinkedList<>();
        tracks.add(trackRequest);
        Album updatedAlbum = albumService.addTracksToAlbum(album.id(), tracks);

        List<Album> result = albumService.searchAlbumsByTitle("Tets Album");
        assertEquals(1,result.size());
    }
}
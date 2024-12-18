package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.entity.Track;
import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
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
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());

        Album album = albumService.addAlbum(requestBody);
        assertNotNull(album.getId());
        assertEquals("Test Album", album.getTitle());
    }

    @Test
    void addExistingAlbum() {
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());

        Album album = albumService.addAlbum(requestBody);
        assertNotNull(album.getId());
        assertEquals("Test Album", album.getTitle());
        albumService.addAlbum(requestBody);
    }

    @Test
    void addTracksToAlbum() {
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());
        Album album = albumService.addAlbum(requestBody);
        TrackRequestBody trackRequestBody = new TrackRequestBody("Test Track", Duration.ofMinutes(5), LocalDate.now());
        List<TrackRequestBody> tracks = new LinkedList<>();
        tracks.add(trackRequestBody);
        Album updatedAlbum = albumService.addTracksToAlbum(album.getId(), tracks);
        assertEquals(1, updatedAlbum.getTracks().size());

    }

    @Test
    void setReleaseDate() {
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());
        Album album = albumService.addAlbum(requestBody);
    }

    @Test
    void isAlbumReleased() {
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now().minusDays(2));
        Album album = albumService.addAlbum(requestBody);
        assertTrue(albumService.isAlbumReleased(album.getId()));
    }

    @Test
    void searchAlbumsByTitle() {
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());
        Album album = albumService.addAlbum(requestBody);
        TrackRequestBody trackRequestBody = new TrackRequestBody("Test Track", Duration.ofMinutes(5), LocalDate.now());
        List<TrackRequestBody> tracks = new LinkedList<>();
        tracks.add(trackRequestBody);
        Album updatedAlbum = albumService.addTracksToAlbum(album.getId(), tracks);

        List<Album> result = albumService.searchAlbumsByTitle("Tets Album");
        assertEquals(1,result.size());
    }
}
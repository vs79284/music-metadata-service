package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.entity.Track;
import com.assignmnt.ice.exceptions.AlbumConflictException;
import com.assignmnt.ice.exceptions.AlbumNotFoundException;
import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.assignmnt.ice.utils.Helper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Service
public class AlbumServiceInMemory implements IAlbumService {

    Map<String, Album> albumRepo = new HashMap<>();

    @Autowired
    Helper helper;

    @Override
    public Album addAlbum(AlbumRequestBody albumRequestBody) {

        //String id = UUID.randomUUID().toString();
        String id = helper.generateId(albumRequestBody.title());
        if( albumRepo.get(id)!=null){
            throw new AlbumConflictException("Album already exist with title "+ albumRequestBody.title());
        }
        Album album = new Album();
        album.setArtist(albumRequestBody.artist());
        album.setTitle(albumRequestBody.title());
        album.setReleaseDate(albumRequestBody.releaseDate());
        album.setId(id);
        album.setTracks(new LinkedList<>());
        albumRepo.put(
                id, album);
        return album;
    }

    @Override
    public Album addTracksToAlbum(String albumId, List<TrackRequestBody> trackRequests) {
        Album album = albumRepo.get(albumId);
        if( albumRepo.get(albumId)==null){
            throw new AlbumNotFoundException("Album not found with id "+ albumId);
        }
        trackRequests.forEach(t -> {
            Track track = new Track();
            track.setDuration(t.duration());
            track.setTitle(t.title());
            album.getTracks().add(track);
        });
        return album;
    }

    @Override
    public Album setReleaseDate(String albumId, AlbumRequestBody albumRequestBody) {
        Album album = albumRepo.get(albumId);
        if( albumRepo.get(albumId)==null){
            throw new AlbumNotFoundException("Album not found with id "+ albumId);
        }
        album.setReleaseDate(albumRequestBody.releaseDate());
        return album;
    }

    @Override
    public Boolean isAlbumReleased(String albumId) {
        Album album = albumRepo.get(albumId);
        return album.getReleaseDate() != null && album.getReleaseDate().isBefore(LocalDate.now());
    }

    @Override
    public List<Album> searchAlbumsByTitle(String title) {
        return albumRepo.values().stream().filter(album -> StringUtils.getLevenshteinDistance(album.getTitle().toLowerCase(), title.toLowerCase()) <= 3).collect(Collectors.toList());
    }
}

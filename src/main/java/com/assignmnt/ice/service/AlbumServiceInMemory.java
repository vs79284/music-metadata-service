package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.entity.Track;
import com.assignmnt.ice.exceptions.AlbumConflictException;
import com.assignmnt.ice.exceptions.AlbumNotFoundException;
import com.assignmnt.ice.model.request.AlbumRequest;
import com.assignmnt.ice.model.request.TrackRequest;
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
    private final Helper helper;

    public AlbumServiceInMemory(Helper helper) {
        this.helper = helper;
    }

    @Override
    public Album addAlbum(AlbumRequest albumRequest) {

        String id = helper.generateId(albumRequest.title());
        Optional<Album> albumOptional = Optional.ofNullable(albumRepo.get(id));
        if(albumOptional.isPresent()){
            throw new AlbumConflictException("Album already exist with title "+ albumRequest.title());
        }

        Album album = new Album(id,albumRequest.title(),albumRequest.artist(),albumRequest.releaseDate(),new LinkedList<>());
        albumRepo.put(id, album);
        return album;
    }

    @Override
    public Album addTracksToAlbum(String albumId, List<TrackRequest> trackRequests) {

        Optional<Album> albumOptional = Optional.ofNullable(albumRepo.get(albumId));
        if(albumOptional.isEmpty()){
            throw new AlbumNotFoundException("Album not found with id "+ albumId);
        }

        trackRequests.forEach(t -> {
            Track track = new Track("ID",t.title(), t.duration());
            albumOptional.get().tracks().add(track);
        });
        return albumOptional.get();
    }

    @Override
    public Album setReleaseDate(String albumId, AlbumRequest albumRequest) {
        Optional<Album> albumOptional = Optional.ofNullable(albumRepo.get(albumId));
        if(albumOptional.isEmpty()){
            throw new AlbumNotFoundException("Album not found with id "+ albumId);
        }
        Album newAlbum = new Album(albumId,albumOptional.get().title(),albumOptional.get().artist(),albumRequest.releaseDate(),albumOptional.get().tracks());
        albumRepo.put(albumId,newAlbum);
        return newAlbum;
    }

    @Override
    public Boolean isAlbumReleased(String albumId) {
        Optional<Album> albumOptional = Optional.ofNullable(albumRepo.get(albumId));
        if(albumOptional.isEmpty()){
            throw new AlbumNotFoundException("Album not found with id "+ albumId);
        }
        return albumOptional.get().releaseDate() != null && albumOptional.get().releaseDate().isBefore(LocalDate.now());
    }

    @Override
    public List<Album> searchAlbumsByTitle(String title) {
        return albumRepo.values().stream().filter(album -> StringUtils.getLevenshteinDistance(album.title().toLowerCase(), title.toLowerCase()) <= 3).collect(Collectors.toList());
    }
}

package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import com.assignmnt.ice.repository.AlbumRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumService implements IAlbumService {

    @Autowired
    AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
    }


    @Override
    public Album addAlbum(AlbumRequestBody albumRequestBody) {
        Album album = new Album();
        album.setArtist(albumRequestBody.artist());
        album.setTitle(albumRequestBody.title());
        album.setReleaseDate(albumRequestBody.releaseDate());
        return albumRepository.save(album);
    }

    @Override
    public Album setReleaseDate(String albumId, AlbumRequestBody albumRequestBody) {
        return null;
    }

    @Override
    public Boolean isAlbumReleased(String albumId) {
        return true;
        //Album album = albumRepository.findById(albumId);
    }

    @Override
    public Album addTracksToAlbum(String albumId, List<TrackRequestBody> trackRequests) {
        return null;
    }

    @Override
    public List<Album> searchAlbumsByTitle(String title) {

        List<Album> allAlbums = albumRepository.findAll();

        List<Album> results = allAlbums.stream()
                .filter(album -> StringUtils.getLevenshteinDistance(album.getTitle().toLowerCase(), title.toLowerCase()) <= 3)
                .collect(Collectors.toList());
        return results;
    }
}

package com.assignmnt.ice.service;

import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import com.assignmnt.ice.model.Track;
import com.assignmnt.ice.entity.Album;
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


    @Override
    public Album addAlbum(AlbumRequestBody albumRequestBody) {
        Album album = new Album();
        albumRepository.save(album);
        return new Album();
    }

    @Override
    public Album setReleaseDate(AlbumRequestBody albumRequestBody) {
        return null;
    }

    @Override
    public Boolean isAlbumReleased(String albumId) {
        return null;
    }

    @Override
    public List<Track> addTrack(List<TrackRequestBody> trackRequests) {
        return null;
    }

    @Override
    public List<Album> getAlbums(String query) {

        List<Album> allAlbums = albumRepository.findAll();
        return allAlbums.stream()
                .filter(album -> StringUtils.getLevenshteinDistance(album.getTitle().toLowerCase(), query.toLowerCase()) <= 3)
                .collect(Collectors.toList());
    }
}

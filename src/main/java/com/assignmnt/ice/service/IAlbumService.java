package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.model.request.AlbumRequest;
import com.assignmnt.ice.model.request.TrackRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IAlbumService {

    Album addAlbum(AlbumRequest albumRequest);

    Album addTracksToAlbum(String albumId, List<TrackRequest> trackRequests);

    Album setReleaseDate(String albumId, AlbumRequest albumRequest);

    Boolean isAlbumReleased(String albumId);

    List<Album> searchAlbumsByTitle(String title);
}

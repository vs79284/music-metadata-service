package com.assignmnt.ice.service;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IAlbumService {

    Album addAlbum(AlbumRequestBody albumRequestBody);

    Album addTracksToAlbum(String albumId, List<TrackRequestBody> trackRequests);

    Album setReleaseDate(String albumId, AlbumRequestBody albumRequestBody);

    Boolean isAlbumReleased(String albumId);

    List<Album> searchAlbumsByTitle(String title);
}

package com.assignmnt.ice.service;

import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import com.assignmnt.ice.model.Track;
import org.springframework.stereotype.Component;
import com.assignmnt.ice.entity.Album;
import java.util.List;

@Component
public interface IAlbumService {
    Album addAlbum(AlbumRequestBody albumRequestBody);

    Album setReleaseDate(AlbumRequestBody albumRequestBody);

    Boolean isAlbumReleased(String albumId);

    List<Track> addTrack(List<TrackRequestBody> trackRequests);

    List<Album> getAlbums(String albumName);
}

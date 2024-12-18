package com.assignmnt.ice.repository;

import com.assignmnt.ice.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}

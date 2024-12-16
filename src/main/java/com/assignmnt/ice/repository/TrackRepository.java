package com.assignmnt.ice.repository;

import com.assignmnt.ice.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track,Long> {
}

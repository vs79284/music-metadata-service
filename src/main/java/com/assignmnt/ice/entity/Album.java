package com.assignmnt.ice.entity;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public record Album ( String id, String title,String artist,LocalDate releaseDate, List<Track> tracks){}

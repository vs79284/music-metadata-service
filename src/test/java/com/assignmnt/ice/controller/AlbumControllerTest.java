package com.assignmnt.ice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignmnt.ice.entity.Album;
import com.assignmnt.ice.model.request.AlbumRequestBody;
import com.assignmnt.ice.model.request.TrackRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AlbumControllerTest {

    AlbumRequestBody requestBody ;

    List<TrackRequestBody> tracks;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;

    String albumId;
    @BeforeEach
    void setUp() {
        requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());
        AlbumRequestBody requestBody = new AlbumRequestBody("Test Album", "Test Artist", LocalDate.now());
        TrackRequestBody trackRequestBody = new TrackRequestBody("Test Track", Duration.ofMinutes(5), LocalDate.now());
        tracks = new LinkedList<>();
        tracks.add(trackRequestBody);
    }

    @AfterEach
    void tearDown() {
    }

    public String asJsonString(final Object obj) {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void shouldAddNewAlbum() throws Exception {
        requestBody = new AlbumRequestBody("Test New Album", "Test New Artist", LocalDate.now());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldThrowResourceConflictExceptionWhileAddingAlbum() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Album already exist with title Test Album"));
    }

    @Test
    void addTrackToAlbum() throws Exception {

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").exists()) // Verify the albumId in the response
                .andReturn()
                .getResponse()
                .getContentAsString(); // Extract the full JSON response
        String albumId = JsonPath.parse(response).read("$.id");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/album/{albumId}/tracks",albumId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tracks)))
                .andExpect(status().isCreated());

    }


    @Test
    void getAlbum() throws Exception {
        requestBody = new AlbumRequestBody("Test Album2", "Test Artist 1 ", LocalDate.now());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isCreated());
        requestBody = new AlbumRequestBody("Test Album3", "Test Artist 2", LocalDate.now());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isCreated());

        String responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.get("/album")
                                .param("query","Test Album2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Extract the full JSON response
        List<Album> resultAlbums = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Album.class));
        assert resultAlbums.size() == 3;


        responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.get("/album")
                                .param("query","abc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Extract the full JSON response
        resultAlbums = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Album.class));
        assert resultAlbums.size() == 0;

        responseJson = mockMvc.perform(
                        MockMvcRequestBuilders.get("/album")
                                .param("query","album1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Extract the full JSON response
        resultAlbums = objectMapper.readValue(responseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Album.class));
        assert resultAlbums.size() == 0;

    }

    @Test
    void updateReleaseDate() throws Exception {
        requestBody = new AlbumRequestBody("Test update relesase Album", "Test New Artist", LocalDate.now());

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").exists()) // Verify the albumId in the response
                .andReturn()
                .getResponse()
                .getContentAsString(); // Extract the full JSON response
        String albumId = JsonPath.parse(response).read("$.id");
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/album/{albumId}",albumId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestBody)))
                .andExpect(status().isOk());
    }

}
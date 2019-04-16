package com.example.kalah.controller;

import com.example.kalah.domain.dto.CreateGameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KalayControllerIT {

    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private String methodGames = "games";

    private String id = "dummyId";
    private String moveUrlFormat = "/%s/%s/pits/%d";

    private String jsonPathOfId = "$.id";
    private String jsonPathOfUri = "$.uri";
    private String jsonPathOfStatus = "$.status";

    @Test
    public void testCreateGame() throws Exception {
        mvc
                .perform(post(String.format("/%s", methodGames))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(jsonPathOfId).exists())
                .andExpect(jsonPath(jsonPathOfUri).exists());
    }

    @Test
    public void testMove() throws Exception {
        int pitId = 1;

        MvcResult createGameResult = mvc
                .perform(post(String.format("/%s", methodGames))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(jsonPathOfId).exists())
                .andExpect(jsonPath(jsonPathOfUri).exists())
                .andReturn();

        CreateGameDto dto = MAPPER.readValue(createGameResult.getResponse().getContentAsString(), CreateGameDto.class);

        mvc
                .perform(put(String.format(moveUrlFormat, methodGames, dto.getId(), pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(jsonPathOfId).exists())
                .andExpect(jsonPath(jsonPathOfUri).exists())
                .andExpect(jsonPath(jsonPathOfStatus).exists());
    }

    @Test
    public void testMoveBadRequest() throws Exception {
        int pitId = 1;
        mvc
                .perform(put(String.format(moveUrlFormat, methodGames, id, pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testMoveInvalidMovement() throws Exception {
        int pitId = 8;

        MvcResult createGameResult = mvc
                .perform(post(String.format("/%s", methodGames))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(jsonPathOfId).exists())
                .andExpect(jsonPath(jsonPathOfUri).exists())
                .andReturn();

        CreateGameDto dto = MAPPER.readValue(createGameResult.getResponse().getContentAsString(), CreateGameDto.class);

        mvc
                .perform(put(String.format(moveUrlFormat, methodGames, dto.getId(), pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}

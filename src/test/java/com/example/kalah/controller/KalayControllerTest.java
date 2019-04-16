package com.example.kalah.controller;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.exception.InvalidArgumentException;
import com.example.kalah.exception.InvalidMovementException;
import com.example.kalah.exception.KalahException;
import com.example.kalah.service.KalahService;
import com.example.kalah.util.KalahBoardUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class KalayControllerTest {

    @Mock
    private KalahService kalahService;

    @InjectMocks
    private KalahController kalahController;

    private MockMvc mockMvc;
    private String methodGames = "games";

    private String id = "dummyId";
    private String moveUrlFormat = "/%s/%s/pits/%d";
    private String gamegUrlFormat = "/%s/%s";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(kalahController)
                .setControllerAdvice(KalahControllerAdvise.class).build();
    }

    @Test
    public void testCreateGame() throws Exception {

        Mockito.when(kalahService.createGame(KalahType.SIX_TO_SIX)).thenReturn(id);

        mockMvc
                .perform(post(String.format("/%s", methodGames))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.uri").exists());
    }

    @Test
    public void testCreateInvalidParameter() throws Exception {

        mockMvc
                .perform(post(String.format(gamegUrlFormat, methodGames, "dummy"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateGameException() throws Exception {

        Mockito.when(kalahService.createGame(KalahType.SIX_TO_SIX)).thenThrow(new NullPointerException("Dummy NullPointerException"));

        mockMvc
                .perform(post(String.format("/%s", methodGames))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCreateGameKalahException() throws Exception {

        Mockito.when(kalahService.createGame(KalahType.SIX_TO_SIX)).thenThrow(new KalahException("Dummy KalahException"));

        mockMvc
                .perform(post(String.format("/%s", methodGames))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testGetGame() throws Exception {

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(id);

        Mockito.when(kalahService.getGame(id)).thenReturn(kalahBoard);

        mockMvc
                .perform(get(String.format(gamegUrlFormat, methodGames, id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testGetGameInvalidParam() throws Exception {

        Mockito.when(kalahService.getGame(id))
                .thenThrow(InvalidArgumentException.to("Dummy InvalidArgumentException"));

        mockMvc
                .perform(get(String.format(gamegUrlFormat, methodGames, id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testMove() throws Exception {

        int pitId = 1;
        int pitIndex = 0;


        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(id);

        Mockito.when(kalahService.move(id, pitIndex)).thenReturn(kalahBoard);

        mockMvc
                .perform(put(String.format(moveUrlFormat, methodGames, id, pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testMoveInvalidParam() throws Exception {

        int pitId = 1;
        int pitIndex = 0;

        Mockito.when(kalahService.move(id, pitIndex))
                .thenThrow(InvalidArgumentException.to("Dummy InvalidArgumentException"));

        mockMvc
                .perform(put(String.format(moveUrlFormat, methodGames, id, pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMoveInvalidMovement() throws Exception {

        int pitId = 1;
        int pitIndex = 0;

        Mockito.when(kalahService.move(id, pitIndex))
                .thenThrow(InvalidMovementException.to("Dummy InvalidMovementException"));

        mockMvc
                .perform(put(String.format(moveUrlFormat, methodGames, id, pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void testMoveKalahException() throws Exception {

        int pitId = 1;
        int pitIndex = 0;

        Mockito.when(kalahService.move(id, pitIndex))
                .thenThrow(KalahException.to("Dummy KalahException"));

        mockMvc
                .perform(put(String.format(moveUrlFormat, methodGames, id, pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testMoveRuntimeException() throws Exception {

        int pitId = 1;
        int pitIndex = 0;

        Mockito.when(kalahService.move(id, pitIndex))
                .thenThrow(new RuntimeException("Dummy RuntimeException"));

        mockMvc
                .perform(put(String.format(moveUrlFormat, methodGames, id, pitId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}

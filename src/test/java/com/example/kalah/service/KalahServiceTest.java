package com.example.kalah.service;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.exception.InvalidArgumentException;
import com.example.kalah.exception.InvalidMovementException;
import com.example.kalah.exception.KalahException;
import com.example.kalah.repository.KalahMockRepository;
import com.example.kalah.util.KalahBoardUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class KalahServiceTest {

    @Mock
    private KalahBoardService kalahBoardService;

    @Mock
    private KalahMockRepository kalahMockRepository;

    @InjectMocks
    private KalahService kalahService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateGame() {
        String id = "testId";

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(id);

        Mockito.when(kalahBoardService.createKalahBoard(Mockito.eq(KalahType.SIX_TO_SIX))).thenReturn(kalahBoard);
        Mockito.when(kalahMockRepository.add(Mockito.eq(kalahBoard))).thenReturn(kalahBoard);

        String actualId = kalahService.createGame(KalahType.SIX_TO_SIX);

        Assert.assertEquals("Id mismatched", id, actualId);
    }

    @Test(expected = KalahException.class)
    public void testCreateGameRepositoryServiceException() {
        String id = "testId";

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(id);

        Mockito.when(kalahBoardService.createKalahBoard(Mockito.eq(KalahType.SIX_TO_SIX)))
                .thenReturn(kalahBoard);
        Mockito.when(kalahMockRepository.add(Mockito.eq(kalahBoard)))
                .thenThrow(KalahException.to("Dummy KalahException"));

        kalahService.createGame(KalahType.SIX_TO_SIX);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateGameBoardServiceException() {
        Mockito.when(kalahBoardService.createKalahBoard(Mockito.eq(KalahType.SIX_TO_SIX)))
                .thenThrow(new RuntimeException("Dummy RuntimeException"));

        kalahService.createGame(KalahType.SIX_TO_SIX);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testCreateGameNUllKalahType() {
        kalahService.createGame(null);
    }

    @Test
    public void testGetGame() {
        String id = "testId";

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(id);

        Mockito.when(kalahMockRepository.get(id)).thenReturn(kalahBoard);

        KalahBoard actual = kalahService.getGame(id);

        Assert.assertNotNull("KalahBoard is null", actual);
    }

    @Test(expected = KalahException.class)
    public void testGetGameRepositoryServiceException() {
        String id = "testId";

        Mockito.when(kalahMockRepository.get(id)).thenThrow(KalahException.to("Dummy KalahException"));

        kalahService.getGame(id);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testGetGameNullId() {
        kalahService.getGame(null);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testGetGameEmptyId() {
        kalahService.getGame("");
    }

    @Test
    public void testMove() {
        String kalahBoardId = "testId";

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(kalahBoardId);

        Mockito.when(kalahMockRepository.get(kalahBoardId)).thenReturn(kalahBoard);

        KalahBoard actual = kalahService.move(kalahBoardId, 0);

        Assert.assertNotNull("KalahBoard is null", actual);
    }

    @Test(expected = KalahException.class)
    public void testMoveRepositoryServiceException() {
        String kalahBoardId = "testId";

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(kalahBoardId);

        Mockito.when(kalahMockRepository.get(kalahBoardId)).thenThrow(KalahException.to("Dummy KalahException"));

        kalahService.move(kalahBoardId, 0);
    }

    @Test(expected = InvalidMovementException.class)
    public void testMoveKalahBoardException() {
        String kalahBoardId = "testId";

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);
        kalahBoard.setId(kalahBoardId);

        Mockito.when(kalahMockRepository.get(kalahBoardId)).thenReturn(kalahBoard);

        Mockito.doThrow(new InvalidMovementException("Dummy InvalidMovementException")).when(kalahBoardService).move(kalahBoard, 0);

        kalahService.move(kalahBoardId, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testMoveNullKalahBoardId() {
        kalahService.move(null, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testMoveEmptyKalahBoardId() {
        kalahService.move("", 0);
    }
}

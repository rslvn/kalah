package com.example.kalah.service;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.exception.InvalidMovementException;
import com.example.kalah.util.KalahBoardUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class KalahBoardServiceTest {

    private String messageWinnerNotNull = "Winner is not null";
    private String messagePlayTurnMismatched = "PlayTurn mismatched";
    private String messageStoneCountMismatched = "StoneCount mismatched";

    @InjectMocks
    private KalahBoardService kalahBoardService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateKalahBoard() {
        KalahBoard kalahBoard = kalahBoardService.createKalahBoard(KalahType.SIX_TO_SIX);

        Assert.assertNotNull("kalahBoard is null", kalahBoard);
    }

    @Test
    public void testMoveAndChangePlayTurn() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        kalahBoardService.move(kalahBoard, 3);

        // first movement pit
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(3).getStoneCount());
        // player pits whose stone count increase
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(4).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(5).getStoneCount());
        // player home
        Assert.assertEquals(messageStoneCountMismatched, 1, kalahBoard.getPitList().get(6).getStoneCount());
        // rival player pits whose stone count increase
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(7).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(8).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(9).getStoneCount());

        // rival player pit whose stone count is not changed
        Assert.assertEquals(messageStoneCountMismatched, 6, kalahBoard.getPitList().get(10).getStoneCount());

        Assert.assertEquals(messagePlayTurnMismatched, kalahBoard.getPlayerNorth(), kalahBoard.getPlayTurn());
        Assert.assertNull(messageWinnerNotNull, kalahBoard.getWinner());

    }

    @Test
    public void testMoveAndDontPutStoneToRivalHome() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        // pick 2 stone from the 5th pit and add them to the 6th pit to create the case
        kalahBoard.getPitList().get(4).setStoneCount(4);
        kalahBoard.getPitList().get(5).increase(2);

        kalahBoardService.move(kalahBoard, 5);
        // first movement pit
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(5).getStoneCount());
        // player home
        Assert.assertEquals(messageStoneCountMismatched, 1, kalahBoard.getPitList().get(6).getStoneCount());
        // rival player pits whose stone count increased
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(7).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(8).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(9).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(10).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(11).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(12).getStoneCount());

        // rival player home
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(13).getStoneCount());

        // player pits whose stone count increased
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(0).getStoneCount());

        // player pits whose stone count not increased
        Assert.assertEquals(messageStoneCountMismatched, 6, kalahBoard.getPitList().get(1).getStoneCount());

        Assert.assertEquals(messagePlayTurnMismatched, kalahBoard.getPlayerNorth(), kalahBoard.getPlayTurn());
        Assert.assertNull(messageWinnerNotNull, kalahBoard.getWinner());
    }

    @Test
    public void testMoveAndPutLastStoneToOwnHome() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        // pick 4 stone from the 5th pit and add them to the 6th pit to create the case
        kalahBoard.getPitList().get(4).setStoneCount(2);
        kalahBoard.getPitList().get(5).increase(4);

        kalahBoardService.move(kalahBoard, 4);
        // first movement pit
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(4).getStoneCount());
        // player pit
        Assert.assertEquals(messageStoneCountMismatched, 11, kalahBoard.getPitList().get(5).getStoneCount());

        // player home
        Assert.assertEquals(messageStoneCountMismatched, 1, kalahBoard.getPitList().get(6).getStoneCount());

        // rival player pits whose stone count increased
        Assert.assertEquals(messageStoneCountMismatched, 6, kalahBoard.getPitList().get(7).getStoneCount());

        Assert.assertEquals(messagePlayTurnMismatched, kalahBoard.getPlayerSouth(), kalahBoard.getPlayTurn());
        Assert.assertNull(messageWinnerNotNull, kalahBoard.getWinner());

    }


    @Test
    public void testMoveAndStoleOppositeStones() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        // pick 2 stone from the 1st pit and add them to the 6th and the 2nd pit to create the case
        kalahBoard.getPitList().get(0).setStoneCount(0);
        kalahBoard.getPitList().get(5).increase(2);
        kalahBoard.getPitList().get(1).increase(4);

        kalahBoardService.move(kalahBoard, 5);
        // first movement pit
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(5).getStoneCount());
        // player home
        Assert.assertEquals(messageStoneCountMismatched, 9, kalahBoard.getPitList().get(6).getStoneCount());
        // rival player pits whose stone count increased
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(7).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(8).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(9).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(10).getStoneCount());
        Assert.assertEquals(messageStoneCountMismatched, 7, kalahBoard.getPitList().get(11).getStoneCount());
        // opposite pit
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(12).getStoneCount());

        // rival player home
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(13).getStoneCount());

        // final pit to move
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(0).getStoneCount());

        // player pits whose stone count not increased
        Assert.assertEquals(messageStoneCountMismatched, 10, kalahBoard.getPitList().get(1).getStoneCount());

        Assert.assertEquals(messagePlayTurnMismatched, kalahBoard.getPlayerSouth(), kalahBoard.getPlayTurn());
        Assert.assertNull(messageWinnerNotNull, kalahBoard.getWinner());
    }

    @Test
    public void testMoveAndDecideWinner() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        // create case
        kalahBoard.getPitList().get(7).setStoneCount(0);
        kalahBoard.getPitList().get(8).setStoneCount(0);
        kalahBoard.getPitList().get(9).setStoneCount(0);
        kalahBoard.getPitList().get(10).setStoneCount(0);
        kalahBoard.getPitList().get(11).setStoneCount(0);
        kalahBoard.getPitList().get(12).setStoneCount(0);
        kalahBoard.getPitList().get(6).increase(36);


        kalahBoardService.move(kalahBoard, 5);
        // first movement pit
        Assert.assertEquals(messageStoneCountMismatched, 0, kalahBoard.getPitList().get(5).getStoneCount());
        // player home
        Assert.assertEquals(messageStoneCountMismatched, 37, kalahBoard.getPitList().get(6).getStoneCount());
        // rival player pits whose stone count increased
        Assert.assertEquals(messageStoneCountMismatched, 1, kalahBoard.getPitList().get(7).getStoneCount());

        Assert.assertEquals(messagePlayTurnMismatched, kalahBoard.getPlayerSouth(), kalahBoard.getPlayTurn());
        Assert.assertNotNull("Winner is null", kalahBoard.getWinner());
    }

    @Test(expected = InvalidMovementException.class)
    public void testMoveGameAlreadyOver() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        kalahBoard.setWinner(kalahBoard.getPlayTurn());

        kalahBoardService.move(kalahBoard, 5);
    }

    @Test(expected = InvalidMovementException.class)
    public void testPlayerAttempsToMoveOwnHome() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        kalahBoardService.move(kalahBoard, 6);
    }

    @Test(expected = InvalidMovementException.class)
    public void testPlayerAttempsToMoveFromEmptyPit() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        // create case
        kalahBoard.getPitList().get(2).setStoneCount(0);
        kalahBoardService.move(kalahBoard, 2);
    }

    @Test(expected = InvalidMovementException.class)
    public void testPlayerAttempsToMoveRivalsPit() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        kalahBoardService.move(kalahBoard, 8);
    }

    @Test(expected = InvalidMovementException.class)
    public void testPlayerAttempsToMoveRivalsHome() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        kalahBoardService.move(kalahBoard, 13);
    }


}

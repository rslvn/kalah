package com.example.kalah.util;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

public class KalahBoardUtilityTest {

    @Test
    public void name() {

        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        Assert.assertNotNull("kalahBoard is null", kalahBoard);
        Assert.assertEquals("KalahType mismatched", KalahType.SIX_TO_SIX, kalahBoard.getKalahType());
        Assert.assertNotNull("PlayerSouth is null", kalahBoard.getPlayerSouth());
        Assert.assertNotNull("PlayerNorth is null", kalahBoard.getPlayerNorth());
        Assert.assertEquals("PlayTurn mismatched", kalahBoard.getPlayerSouth(), kalahBoard.getPlayTurn());
        Assert.assertNull("Winner is null", kalahBoard.getWinner());
        Assert.assertTrue("PitList is empty", CollectionUtils.isNotEmpty(kalahBoard.getPitList()));
    }
}

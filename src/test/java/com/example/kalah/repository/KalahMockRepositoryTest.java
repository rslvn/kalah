package com.example.kalah.repository;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.exception.InvalidArgumentException;
import com.example.kalah.util.KalahBoardUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class KalahMockRepositoryTest {

    @InjectMocks
    private KalahMockRepository kalahMockRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * This method covers the happy cases of the add, the update and the get methods
     */
    @Test
    public void testUpdate() {
        KalahBoard kalahBoard = KalahBoardUtility.initialKalahBoard(KalahType.SIX_TO_SIX);

        KalahBoard addedKalahBoard = kalahMockRepository.add(kalahBoard);

        Assert.assertNotNull("KalahBoard is null", addedKalahBoard);
        Assert.assertNotNull("KalahBoard.id is null", addedKalahBoard.getId());

        addedKalahBoard.setPlayTurn(kalahBoard.getPlayerNorth());

        KalahBoard updatedKalahBoard = kalahMockRepository.update(kalahBoard);
        Assert.assertNotNull("KalahBoard is null", addedKalahBoard);
        Assert.assertNotNull("KalahBoard.id is null", addedKalahBoard.getId());

        Assert.assertEquals("", updatedKalahBoard.getPlayerNorth(), updatedKalahBoard.getPlayTurn());
    }

    @Test(expected = InvalidArgumentException.class)
    public void testGet() {
        kalahMockRepository.get("someId");
    }
}

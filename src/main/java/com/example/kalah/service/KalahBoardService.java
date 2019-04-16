package com.example.kalah.service;

import com.example.kalah.domain.model.*;
import com.example.kalah.util.KalahBoardUtility;
import com.example.kalah.util.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is created to manage Kalah board creation and movements on the board
 */
@Service
public class KalahBoardService {

    /**
     * creates a new KalahBoard with players and pits by kalahType. The method does not set the id. It is the responsibility of the repository
     *
     * @param kalahType the type of KalahBoard
     * @return a new created KalahBoard
     */
    public KalahBoard createKalahBoard(KalahType kalahType) {
        return KalahBoardUtility.initialKalahBoard(kalahType);
    }


    /**
     * Make a movement if possible. If a movement occurs the stone counts are changed.
     * It decides to change the play turn between the players and also decides the game is over or no.
     *
     * @param kalahBoard the kalah board to make a movement
     * @param pitIndex   the pitIndex to start the movement
     */
    public void move(KalahBoard kalahBoard, int pitIndex) {
        Preconditions.checkMovement(kalahBoard.getWinner() == null, "The game is over");

        KalahPlayer kalahPlayer = kalahBoard.getPlayTurn();
        Preconditions.checkNotNull(kalahPlayer, "kalahPlayer can not be null");

        Preconditions.checkMovement(kalahPlayer.isOwnPit(pitIndex),
                "The player %s can not play with this pit (pitIndex: %d)",
                kalahPlayer.getPlayerType(),
                pitIndex);

        // not necessary to check that the index does exist or not
        Pit pit = kalahBoard.getPitList().get(pitIndex);
        Preconditions.checkMovement(!pit.isEmpty(),
                "The player %s can not play with empty pit. (pitIndex: %d)",
                kalahPlayer.getPlayerType(),
                pitIndex);

        // reset the stone count of the pit
        int stoneCount = pit.getStoneCount();
        pit.resetStoneCount();

        while (stoneCount > 0) {
            pit = getNextPit(kalahBoard.getPitList(), pit.getIndex());

            // dot put the stone to the rival's home
            if (PitType.HOME.equals(pit.getPitType()) && !kalahPlayer.isOwnHome(pit.getIndex())) {
                continue;
            }

            pit.increase(1);
            stoneCount--;
        }

        // execute the rule if the last stone is put the player's own home
        if (kalahPlayer.isOwnHome(pit.getIndex())) {
            // do nothing. keep play turn as current player
            return;
        }

        // execute the rule if the last stone is put the player's own empty pit
        if ((kalahPlayer.isOwnPit(pit.getIndex()) && pit.getStoneCount() == 1)) {
            Pit oppositePit = kalahBoard.getPitList().get(pit.getOppositePitIndex());

            // move the stones from the player's own pit and the opposite pit the pit to the player's home
            kalahPlayer.getHome().increase(oppositePit.getStoneCount() + pit.getStoneCount());
            pit.resetStoneCount();
            oppositePit.resetStoneCount();

            // keep play turn as current player
            return;
        }

        // the decision of winner
        kalahBoard.makeWinnerDecision();

        // change the play turn if no winner
        if (kalahBoard.getWinner() == null) {
            kalahBoard.changePlayTurn();
        }
    }

    /**
     * get next pit by current pit index. Reset index if the current index isthe last index of the pitList
     *
     * @param pitList         the PitList
     * @param currentPitIndex the currentPitIndex
     * @return the next Pit
     */
    private Pit getNextPit(List<Pit> pitList, int currentPitIndex) {
        int nextIndex = currentPitIndex + 1;

        return nextIndex >= pitList.size() ?
                pitList.get(0) :
                pitList.get(nextIndex);
    }
}

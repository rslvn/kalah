package com.example.kalah.domain.model;

import lombok.*;

import java.util.List;

/**
 * This class is created to keep board status and manage to moves
 */
@Getter
@Setter
@ToString
@Builder
public class KalahBoard {

    private String id;

    @NonNull
    private final KalahType kalahType;

    private final KalahPlayer playerSouth;
    private final KalahPlayer playerNorth;

    private KalahPlayer playTurn;
    private KalahPlayer winner;

    private final List<Pit> pitList;

    /**
     * change play turn to rival
     */
    public void changePlayTurn() {
        playTurn = playTurn.equals(playerSouth) ? playerNorth : playerSouth;
    }

    /**
     * decide the playTurn win or no
     */
    public void makeWinnerDecision() {
        if (playTurn.getHome().getStoneCount() >= kalahType.getStoneVictoryCount()) {
            winner = playTurn;
        }
    }

}

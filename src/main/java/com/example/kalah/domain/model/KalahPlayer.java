package com.example.kalah.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

/**
 * This class created to describe a player
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class KalahPlayer {

    private KalahPlayerType playerType;
    private Pit home;
    private Set<Integer> pitSetToPlay;

    /**
     * gives the pit is the player's pit ot no
     * @param pitIndex the pitIndex
     * @return true or false
     */
    public boolean isOwnPit(int pitIndex) {
        return pitSetToPlay.contains(pitIndex);
    }

    /**
     * gives the home is the player's home or no
     * @param pitIndex the pitIndex
     * @return true or false
     */
    public boolean isOwnHome(int pitIndex) {
        return home.getIndex() == pitIndex;
    }
}

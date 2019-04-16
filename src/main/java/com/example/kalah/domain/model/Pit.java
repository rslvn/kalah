package com.example.kalah.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is created to describe a pit
 */
@Setter
@Getter
@Builder
public class Pit {

    private PitType pitType;
    private int index;
    private int oppositePitIndex;
    private int stoneCount;


    /**
     * gives pit stone count status.
     *
     * @return true if empty; otherwise false
     */
    public boolean isEmpty() {
        return stoneCount == 0;
    }

    /**
     * index known as id or number by clients. This method gives index as Id
     *
     * @return the id
     */
    public int getIndexAsId() {
        return index + 1;
    }

    /**
     * reset the stone count
     */
    public void resetStoneCount() {
        stoneCount = 0;
    }

    /**
     * increase the sone count
     *
     * @param increaseValue the increment value
     */
    public void increase(int increaseValue) {
        stoneCount += increaseValue;
    }
}

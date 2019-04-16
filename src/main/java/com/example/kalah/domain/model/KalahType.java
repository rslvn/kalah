package com.example.kalah.domain.model;

import lombok.Getter;

import java.util.Optional;

/**
 * this enum is created to kalah types. A board is created by this enum
 */
@Getter
public enum KalahType {

    SIX_TO_SIX(6, 6, 37);

    private final int pitCount;
    private final int stoneCount;
    private final int stoneVictoryCount;
    private final int kalahaIndexOfSouthPlayer;
    private final int kalahaIndexOfNorthPlayer;
    private final int totalPitCount;


    KalahType(int pitCount, int stoneCount, int stoneVictoryCount) {
        this.pitCount = pitCount;
        this.stoneCount = stoneCount;
        this.stoneVictoryCount = stoneVictoryCount;
        this.totalPitCount = 2 * pitCount + 2;
        this.kalahaIndexOfSouthPlayer = pitCount;
        this.kalahaIndexOfNorthPlayer = totalPitCount - 1;
    }

    /**
     * retrieve the KalahType by name
     *
     * @param name the name
     * @return the matched KalahType
     */
    public static Optional<KalahType> toKalahType(String name) {
        for (KalahType kalahType : KalahType.values()) {
            if (kalahType.name().equalsIgnoreCase(name)) {
                return Optional.of(kalahType);
            }
        }

        return Optional.empty();
    }

}

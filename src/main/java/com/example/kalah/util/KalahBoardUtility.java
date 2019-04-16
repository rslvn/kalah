package com.example.kalah.util;

import com.example.kalah.domain.model.*;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is created to keep KalahBoard utility processes
 */
public class KalahBoardUtility {

    private KalahBoardUtility() {
        // for sonarqube
    }

    /**
     * creates and returns a kalahboard instance by type
     *
     * @param kalahType the kalahType
     * @return the kalahBoard instance
     */
    public static KalahBoard initialKalahBoard(KalahType kalahType) {
        List<Pit> pitList = Lists.newArrayList();

        // create the playable pitlist of the south player
        List<Pit> playerPits = createPlayablePitList(pitList.size(),
                kalahType.getPitCount(),
                kalahType.getStoneCount(),
                kalahType.getTotalPitCount());

        // create the south player
        KalahPlayer playerSouth = createKalahPlayer(KalahPlayerType.SOUTH,
                kalahType.getKalahaIndexOfSouthPlayer(),
                playerPits);

        // put the playable pitList and the south player home to total pitList in order
        pitList.addAll(playerPits);
        pitList.add(playerSouth.getHome());


        // create the playable pitlist of the north player
        playerPits = createPlayablePitList(pitList.size(),
                kalahType.getPitCount(),
                kalahType.getStoneCount(),
                kalahType.getTotalPitCount());

        // create the north player
        KalahPlayer playerNorth = createKalahPlayer(KalahPlayerType.NORTH,
                kalahType.getKalahaIndexOfNorthPlayer(),
                playerPits);

        // put the playable pitList and the north player home to total pitList in order
        pitList.addAll(playerPits);
        pitList.add(playerNorth.getHome());

        return KalahBoard.builder()
                .kalahType(kalahType)
                .playerSouth(playerSouth)
                .playerNorth(playerNorth)
                .playTurn(playerSouth)
                .pitList(pitList)
                .build();
    }

    /**
     * creates playable pits by given range
     *
     * @param firstIndex    the firstIndex in total PitList
     * @param pitCount      the playable pit count on board
     * @param stoneCount    the initial stone count in a pit
     * @param totalPitCount the total pit count to decide the opposite pit
     * @return A playable pitList
     */
    private static List<Pit> createPlayablePitList(int firstIndex, int pitCount, int stoneCount, int totalPitCount) {

        return IntStream
                .range(firstIndex, firstIndex + pitCount)
                .mapToObj(index -> createPit(PitType.PIT, index, stoneCount, totalPitCount))
                .collect(Collectors.toList());
    }

    /**
     * Creates a KalahPlay with home and playable pits by type
     *
     * @param playerType      the KalayPlayerType
     * @param homeIndex       the home index of the KalayPlayer
     * @param playablePitList the playable pit list by yhe kalay player
     * @return a KalayPlayer
     */
    private static KalahPlayer createKalahPlayer(KalahPlayerType playerType, int homeIndex, List<Pit> playablePitList) {

        Pit home = Pit.builder()
                .pitType(PitType.HOME)
                .index(homeIndex)
                .oppositePitIndex(-1)
                .build();

        return KalahPlayer.builder()
                .playerType(playerType)
                .home(home)
                .pitSetToPlay(playablePitList
                        .stream()
                        .map(Pit::getIndex)
                        .collect(Collectors.toSet()))
                .build();
    }

    /**
     * Creates a pit by type and calculate the opposite pit
     *
     * @param pitType       the type of the Pit
     * @param index         the index of the Pit in total Pit List
     * @param stoneCount    the initial stone count
     * @param totalPitCount the total stone count to decide the opposite pit
     * @return a Pit
     */
    private static Pit createPit(PitType pitType, int index, int stoneCount, int totalPitCount) {

        return Pit.builder()
                .index(index)
                .pitType(pitType)
                .stoneCount(stoneCount)
                .oppositePitIndex(totalPitCount - 2 - index)
                .build();
    }

}

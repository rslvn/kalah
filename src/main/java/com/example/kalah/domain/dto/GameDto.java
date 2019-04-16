package com.example.kalah.domain.dto;

import com.example.kalah.domain.model.KalahPlayer;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.domain.model.Pit;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * This class craeted to give a response to get game requests
 */
@Data
@Builder
public class GameDto {


    private String id;

    private KalahType kalahType;

    private KalahPlayer playerSouth;
    private KalahPlayer playerNorth;

    private KalahPlayer playTurn;
    private KalahPlayer winner;

    private List<Pit> pitList;
}

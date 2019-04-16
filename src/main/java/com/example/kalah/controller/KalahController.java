package com.example.kalah.controller;

import com.example.kalah.domain.dto.CreateGameDto;
import com.example.kalah.domain.dto.GameDto;
import com.example.kalah.domain.dto.MoveDto;
import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.domain.model.Pit;
import com.example.kalah.exception.InvalidArgumentException;
import com.example.kalah.service.KalahService;
import com.example.kalah.util.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * This class is created as an endpoint to play Kalah Game
 */
@Slf4j
@RestController
public class KalahController {


    private KalahService kalahService;

    /**
     * Constructor
     *
     * @param kalahService the kalahService
     */
    public KalahController(KalahService kalahService) {
        this.kalahService = kalahService;
    }

    /**
     * accept request to create a new game
     *
     * @param type the optional parameter to create different board by type
     * @return a CreateGameDto
     */
    @PostMapping(value = "/games")
    public ResponseEntity<CreateGameDto> createGame(@RequestParam(name = "type", defaultValue = "SIX_TO_SIX", required = false) String type) {
        // Validate parameter
        KalahType kalahType = KalahType.toKalahType(type)
                .orElseThrow(() -> InvalidArgumentException.to("Unkown KalahType: %s", type));

        // call service
        String gameId = kalahService.createGame(kalahType);

        // prepare return object
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateGameDto.builder()
                .id(gameId)
                .uri(ControllerLinkBuilder
                        .linkTo(ControllerLinkBuilder
                                .methodOn(KalahController.class)
                                .getGame(gameId))
                        .withSelfRel()
                        .getHref())
                .build());
    }

    /**
     * @param gameId the gameId to play
     * @param pitId  the initial pitId to play
     * @return a MoveDto as game latest status
     */
    @PutMapping(value = "/games/{gameId}/pits/{pitId}")
    public MoveDto move(@PathVariable(name = "gameId") String gameId, @PathVariable(name = "pitId") Integer pitId) {
        // Validate parameter
        Preconditions.checkNotNull(pitId, "pitId is null");

        // call service
        KalahBoard kalahBoard = kalahService.move(gameId, pitId - 1);

        // prepare return object
        return MoveDto.builder()
                .id(kalahBoard.getId())
                .uri(ControllerLinkBuilder
                        .linkTo(ControllerLinkBuilder
                                .methodOn(KalahController.class)
                                .getGame(gameId))
                        .withSelfRel()
                        .getHref())
                .status(kalahBoard
                        .getPitList()
                        .stream()
                        .collect(Collectors
                                .toMap(Pit::getIndexAsId, Pit::getStoneCount)))
                .build();
    }

    /**
     * accept request to get game status. This is a dummy service to create consistent game URI
     *
     * @param gameId the gameId
     * @return a GameDto
     */
    @GetMapping(value = "/games/{gameId}")
    public GameDto getGame(@PathVariable(name = "gameId") String gameId) {
        // call service. parameter is validated by service
        KalahBoard kalahBoard = kalahService.getGame(gameId);

        // prepare return object
        return GameDto.builder()
                .id(kalahBoard.getId())
                .kalahType(kalahBoard.getKalahType())
                .playerSouth(kalahBoard.getPlayerSouth())
                .playerNorth(kalahBoard.getPlayerNorth())
                .playTurn(kalahBoard.getPlayTurn())
                .winner(kalahBoard.getWinner())
                .pitList(kalahBoard.getPitList())
                .build();
    }
}

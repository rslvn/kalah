package com.example.kalah.service;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.domain.model.KalahType;
import com.example.kalah.repository.KalahMockRepository;
import com.example.kalah.util.Preconditions;
import org.springframework.stereotype.Service;

/**
 * This class is created as back service of the controller.
 * Its responsibilities are validating parameters and orchestration between repository to game board
 */
@Service
public class KalahService {

    private KalahBoardService kalahBoardService;
    private KalahMockRepository kalahMockRepository;

    /**
     * The Constructor
     *
     * @param kalahBoardService the kalahBoardService
     * @param kalahMockRepository the kalahMockRepository
     */
    public KalahService(KalahBoardService kalahBoardService, KalahMockRepository kalahMockRepository) {
        this.kalahBoardService = kalahBoardService;
        this.kalahMockRepository = kalahMockRepository;
    }

    /**
     * Validate parameters and create a board
     *
     * @param kalahType the kalahType to create the board
     * @return a KalahBoard
     */
    public String createGame(KalahType kalahType) {
        Preconditions.checkNotNull(kalahType, "kalahType can not be null");

        KalahBoard kalahBoard = kalahBoardService.createKalahBoard(kalahType);

        return kalahMockRepository.add(kalahBoard).getId();
    }

    /**
     * returns the Kalah board current status
     *
     * @param kalahBoardId the kalahBoardId
     * @return the latest kalah board status
     */
    public KalahBoard getGame(String kalahBoardId) {
        Preconditions.checkNotEmpty(kalahBoardId, "kalahBoardId can not be empty");

        return kalahMockRepository.get(kalahBoardId);
    }


    /**
     * makes the movements if it is possible
     *
     * @param kalahBoardId the kalahBoardId for movements
     * @param pitIndex     the starting pit index
     * @return the latest kalah board status after movements
     */
    public KalahBoard move(String kalahBoardId, int pitIndex) {
        Preconditions.checkNotEmpty(kalahBoardId, "kalahBoardId can not be empty");

        KalahBoard kalahBoard = kalahMockRepository.get(kalahBoardId);

        kalahBoardService.move(kalahBoard, pitIndex);

        return kalahBoard;
    }
}

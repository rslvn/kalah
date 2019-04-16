package com.example.kalah.repository;

import com.example.kalah.domain.model.KalahBoard;
import com.example.kalah.exception.KalahException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * this class created to simulate a repository
 */
@Service
public class KalahMockRepository {

    private static final Map<String, KalahBoard> kalahBoardMap = new ConcurrentHashMap<>();

    /**
     * Adds the kalahBoard to cache
     *
     * @param kalahBoard the kalahBoard
     * @return the cached kalah board
     */
    public KalahBoard add(KalahBoard kalahBoard) {
        kalahBoard.setId(UUID.randomUUID().toString());
        kalahBoardMap.put(kalahBoard.getId(), kalahBoard);

        return kalahBoard;
    }

    /**
     * updates the cached kalah board
     *
     * @param kalahBoard the kalahBoard
     * @return the cached kalah board
     */
    public KalahBoard update(KalahBoard kalahBoard) {

        get(kalahBoard.getId());

        return kalahBoardMap.put(kalahBoard.getId(), kalahBoard);
    }

    /**
     * retrieves the kalah board by id
     *
     * @param kalahBoardId the kalahBoardId
     * @return the cached kalah board
     */
    public KalahBoard get(String kalahBoardId) {
        return Optional.ofNullable(kalahBoardMap.get(kalahBoardId))
                .orElseThrow(() -> KalahException.to("No kalayBoard with Id: %s", kalahBoardId));
    }

}

package com.example.kalah.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * this class craeted to give a response to move game requests
 */
@Data
@Builder
public class MoveDto {
    private String id;
    private String uri;
    private Map<Integer, Integer> status;
}

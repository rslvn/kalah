package com.example.kalah.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * this class craeted to give a response to move game requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveDto {
    private String id;
    private String uri;
    private Map<Integer, Integer> status;
}

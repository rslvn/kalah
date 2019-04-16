package com.example.kalah.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * this class craeted to give a response to create game requests
 */
@Data
@Builder
public class CreateGameDto {
    private String id;
    private String uri;
}

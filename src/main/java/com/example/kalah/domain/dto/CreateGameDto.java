package com.example.kalah.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this class craeted to give a response to create game requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameDto {
    private String id;
    private String uri;
}

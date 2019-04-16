package com.example.kalah.domain.dto;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class CreateGameDtoTest {

    @Test
    public void testCreateGameDtoAsPojo() {
        Assertions.assertPojoMethodsFor(CreateGameDto.class).testing(Method.values()).areWellImplemented();
    }

}

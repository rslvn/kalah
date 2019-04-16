package com.example.kalah.domain.dto;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class GameDtoTest {

    @Test
    public void testGameDtoAsPojo() {
        Assertions.assertPojoMethodsFor(GameDto.class).testing(Method.values()).areWellImplemented();
    }

}

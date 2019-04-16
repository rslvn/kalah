package com.example.kalah.domain.dto;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class MoveDtoTest {

    @Test
    public void testMoveDtoAsPojo() {
        Assertions.assertPojoMethodsFor(MoveDto.class).testing(Method.values()).areWellImplemented();
    }

}

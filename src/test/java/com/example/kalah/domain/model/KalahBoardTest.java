package com.example.kalah.domain.model;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class KalahBoardTest {

    @Test
    public void testKalahBoardAsPojo() {
        Assertions.assertPojoMethodsFor(KalahBoard.class).testing(Method.GETTER, Method.TO_STRING).areWellImplemented();
    }
}

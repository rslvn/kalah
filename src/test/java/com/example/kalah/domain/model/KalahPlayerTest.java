package com.example.kalah.domain.model;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class KalahPlayerTest {
    @Test
    public void testKalahPlayerAsPojo() {
        Assertions.assertPojoMethodsFor(KalahPlayer.class).testing(Method.GETTER).areWellImplemented();
    }
}

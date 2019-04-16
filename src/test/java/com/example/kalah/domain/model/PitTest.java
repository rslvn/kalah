package com.example.kalah.domain.model;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class PitTest {
    @Test
    public void testPitAsPojo() {
        Assertions.assertPojoMethodsFor(Pit.class).testing(Method.GETTER).areWellImplemented();
    }
}

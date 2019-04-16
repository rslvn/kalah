package com.example.kalah.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class KalahTypeTest {

    @Test
    public void testToKalahType() {
        Optional<KalahType> kalahTypeOptional = KalahType.toKalahType(KalahType.SIX_TO_SIX.name());
        Assert.assertTrue("No KalahType", kalahTypeOptional.isPresent());
    }

    @Test
    public void testToKalahTypeNotPresent() {
        Optional<KalahType> kalahTypeOptional = KalahType.toKalahType("Test");
        Assert.assertFalse("KalahType found", kalahTypeOptional.isPresent());
    }

}

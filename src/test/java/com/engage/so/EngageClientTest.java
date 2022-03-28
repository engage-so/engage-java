package com.engage.so;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for simple App.
 */
public class EngageClientTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * This test should return exception if resource does not exist
     */
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Test
    public void ItShouldThrowExceptionIfNoParams(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Pass a valid key");
        EngageClient engage = new EngageClient("","secret");
    }
}

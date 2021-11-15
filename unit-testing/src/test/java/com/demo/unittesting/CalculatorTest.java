package com.demo.unittesting;

import com.demo.app.Calculator;
import org.junit.jupiter.api.*;

import static org.junit.Assert.*;


public class CalculatorTest {

    static Calculator cal = null;

    @BeforeAll
    public static void setUp() {
        cal = new Calculator();
        System.out.println("Setup called....");
    }

    @Test
    @Disabled
    public void testHello() {
        System.out.println("Hello from Unit Testing");
    }

    //@Test
    @RepeatedTest(value = 4)
    @DisplayName("Add method Test")
    public void testAdd() {
        System.out.println("Add tested ...");
        assertEquals(20, cal.add(10, 10));
    }

    @Test
    public void testSubtract() {

        assertEquals(9, cal.subtract(20, 11));
    }

    @Test
    public void testMultiply() {

        assertEquals(110, cal.multiply(10, 11));
    }

    @Test
    public void testDevide() {

        assertEquals(1, cal.divide(10, 10));
    }

    @Test
    public void testDevideByZero() {
        Exception ex=assertThrows(ArithmeticException.class,()->cal.divide(10,0));
       // assertEquals("/ by zero",ex.getMessage());
        System.out.println(ex.getClass().getSimpleName());
        assertTrue(ex.getMessage().contains("zero"));
        assertTrue(ex.getClass().getSimpleName().equals("ArithmeticException"));
    }


    @AfterAll
    public static void cleanUp() {
        cal = null;
        System.out.println("Clean up called");
    }
}

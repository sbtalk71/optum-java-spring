package com.demo.unittesting;

import com.demo.app.Calculator;

import com.demo.app.EmpNotFoundException;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MockTests {
   Calculator cal= mock(Calculator.class);

   DemoInterface idemo=mock(DemoInterface.class);

    @Test
    public void testCalculatorMock(){
       when(cal.add(2,3)).thenReturn(5);

        assertEquals(5,cal.add(2,3));
    }

    @Test
    public void testGetMessage(){
        when(idemo.getName()).thenReturn("Shantanu");
        MyNameService service=new MyNameService(idemo);
        assertEquals("Shantanu"+" welcome to Mockito",service.getMessage());

    }

    @Test
    public void testFindNameException() throws EmpNotFoundException {
        when(idemo.getName()).thenReturn("Shantanu");
        MyNameService service=new MyNameService(idemo);

        Exception ex=assertThrows(EmpNotFoundException.class,()->service.findName("shantanu"));
        assertTrue(ex.getClass().getSimpleName().equals("EmpNotFoundException"));

    }
}

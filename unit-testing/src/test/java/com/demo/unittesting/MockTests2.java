package com.demo.unittesting;

import com.demo.app.Calculator;
import com.demo.app.EmpNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockTests2 {
    @Mock
    Calculator cal;//= mock(Calculator.class);

    @Mock
    DemoInterface idemo;//=mock(DemoInterface.class);

    @InjectMocks
    MyNameService service = new MyNameService();

    @Test
    public void testCalculatorMock() {
        when(cal.add(2, 3)).thenReturn(5);

        assertEquals(5, cal.add(2, 3));
    }

    @Test
    public void testGetMessage() {
        when(idemo.getName()).thenReturn("Shantanu");
        //MyNameService service = new MyNameService(idemo);
        assertEquals("Shantanu" + " welcome to Mockito", service.getMessage());

    }

    @Test
    public void testFindNameException() throws EmpNotFoundException {
        when(idemo.getName()).thenReturn("Shantanu");
        MyNameService service = new MyNameService(idemo);

        Exception ex = assertThrows(EmpNotFoundException.class, () -> service.findName("shantanu"));
        assertTrue(ex.getClass().getSimpleName().equals("EmpNotFoundException"));

    }
}

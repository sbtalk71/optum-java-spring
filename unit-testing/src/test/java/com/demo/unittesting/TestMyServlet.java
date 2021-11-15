package com.demo.unittesting;

import com.demo.app.MyServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestMyServlet {
    @Test
    public void testServlet() throws Exception{
        ServletRequest req= mock(ServletRequest.class);
        ServletResponse resp=mock(ServletResponse.class);
        when(req.getParameter("name")).thenReturn("Shantanu");

        StringWriter sw=new StringWriter();
        PrintWriter writer=new PrintWriter(sw);

        when(resp.getWriter()).thenReturn(writer);

        MyServlet ms= new MyServlet();
        ms.service(req,resp);
        verify(req,atLeast(1)).getParameter("name");
        writer.flush();
        Assertions.assertTrue(sw.toString().contains("hello Shantanu"));
    }
}

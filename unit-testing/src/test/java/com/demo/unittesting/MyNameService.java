package com.demo.unittesting;

import com.demo.app.EmpNotFoundException;

public class MyNameService {
    DemoInterface idemo;

    public MyNameService(DemoInterface idemo) {
        this.idemo = idemo;
    }

    public MyNameService() {

    }

    public String getMessage() {
        return idemo.getName() + " welcome to Mockito";
    }


    public String findName(String name) throws EmpNotFoundException {
        throw new EmpNotFoundException();
    }


}

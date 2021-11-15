package com.demo.unittesting;

public class MyNameService {
    DemoInterface idemo;

    public MyNameService(DemoInterface idemo) {
        this.idemo = idemo;
    }

    public String getMessage(){
        return idemo.getName()+" welcome to Mockito";
    }
}

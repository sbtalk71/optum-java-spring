package com.demo.app;

import java.util.Arrays;
import java.util.List;

public class Util {
    private List<String> namesList= Arrays.asList("cat","tiger","lion","monkey");

    public List<String> getNamesList() {
        return namesList;
    }

    public void setNamesList(List<String> namesList) {
        this.namesList = namesList;
    }

    //write Unit Test to
    //1. if monkey is there
    //2. total number od animals is 4
}

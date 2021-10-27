package com.example.hiddengems.dataModels;

import java.io.Serializable;

public class operatinghours implements Serializable {
    String dayOfWeek;
    int startTime;
    int endTime;

    public operatinghours(String DOW, int start, int end) {
        this.dayOfWeek = DOW;
        this.startTime = start;
        this.endTime = end;
    }

}

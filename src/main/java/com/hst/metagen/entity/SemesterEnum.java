package com.hst.metagen.entity;

public enum SemesterEnum {

    SPRING("Spring"),
    FALL("Fall");

    String semester;


    SemesterEnum(String semester) {
    }

    public String getSemester() {
        return semester;
    }
}

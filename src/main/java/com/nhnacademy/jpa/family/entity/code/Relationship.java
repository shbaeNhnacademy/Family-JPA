package com.nhnacademy.jpa.family.entity.code;

public enum Relationship {
    부("father"),
    모("mother"),
    배우자("spouse"),
    본인(""),
    자녀("child"),
    동거인(""),
    호주(""),
    동거친족(""),
    비동거친족(""),
    동거자(""),
    기타(""),
    ;


    private final String enName;

    Relationship(String enName) {
        this.enName = enName;
    }

    public String getEnName() {
        return enName;
    }
}

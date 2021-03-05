package com.godeltech.gorodetskaya.task.entity.enums;

import java.util.Arrays;

public enum Gender {
    MALE("male"), FEMALE("female"), UNDEFINED("undefined");

    private final String string;

    Gender(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static Gender lookUp(String string) {
        return Arrays.stream(values()).filter(gender -> gender.getString().equalsIgnoreCase(string)).findFirst().get();
    }
}

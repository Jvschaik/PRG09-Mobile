package com.example.moody;

public class Diary {
    private String first;
    private String second;

    public Diary() {
        //public geen constructor nodig anders error.
    }

    public Diary(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }
}

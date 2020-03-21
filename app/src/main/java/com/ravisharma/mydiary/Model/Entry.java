package com.ravisharma.mydiary.Model;

public class Entry {
    private int id;
    private String name;
    private String date;
    private String entry;

    public Entry(int id, String name, String date, String entry) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.entry = entry;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getEntry() {
        return entry;
    }
}

package com.spenkana.wordearch.nucleus;

public class Field {
    public final Integer width;
    public final Integer length;

    public Field(String... rows) {
        width = rows[0].length();
        length = rows.length;
    }
}

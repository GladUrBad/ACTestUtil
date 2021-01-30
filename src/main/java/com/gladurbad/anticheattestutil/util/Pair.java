package com.gladurbad.anticheattestutil.util;

public class Pair<Key, Value> {
    private final Key key;
    private Value value;

    public Pair(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(final Value value) {
        this.value = value;
    }
}

package org.netmelody.citric.value;

public final class Time implements Comparable<Time> {

    private final int value;
    
    private Time(int value) {
        this.value = value;
    }
    
    public static Time of(int value) {
        return new Time(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Time) && ((Time)obj).value == value;
    }
    
    @Override
    public int hashCode() {
        return value;
    }
    
    @Override
    public int compareTo(Time o) {
        return (value < o.value ? -1 : (value == o.value ? 0 : 1));
    }

    public int value() {
        return value;
    }

    public Time minus(Duration delta) {
        return Time.of(Math.max(value - delta.value(), 0));
    }

    public Time plusOne() {
        return Time.of(value + 1);
    }

    public Time minusOne() {
        return Time.of(value - 1);
    }

    @Override
    public String toString() {
        return String.format("Time(t=%d)", value);
    }
}

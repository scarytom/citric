package org.netmelody.citric.value;

public final class Duration implements Comparable<Duration> {

    private final int value;
    
    private Duration(int value) {
        this.value = value;
    }
    
    public static Duration of(int value) {
        return new Duration(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Duration) && ((Duration)obj).value == value;
    }
    
    @Override
    public int hashCode() {
        return value;
    }
    
    @Override
    public int compareTo(Duration o) {
        return (value < o.value ? -1 : (value == o.value ? 0 : 1));
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Time(t=%d)", value);
    }
}

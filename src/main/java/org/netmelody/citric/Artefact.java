package org.netmelody.citric;

public final class Artefact implements Comparable<Artefact> {
    private final int number;
    
    private Artefact(int value) {
        this.number = value;
    }
    
    public static Artefact number(int number) {
        return new Artefact(number);
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Artefact) && ((Artefact)obj).number == number;
    }
    
    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public int compareTo(Artefact o) {
        return (number < o.number ? -1 : (number == o.number ? 0 : 1));
    }
}
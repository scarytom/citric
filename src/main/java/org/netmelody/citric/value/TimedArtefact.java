package org.netmelody.citric.value;

public final class TimedArtefact {
    
    private final Time time;
    private final Artefact artefact;
    
    public TimedArtefact(Time time, Artefact artefact) {
        this.time = time;
        this.artefact = artefact;
    }

    public Time time() {
        return time;
    }

    public Artefact artefact() {
        return artefact;
    }
}

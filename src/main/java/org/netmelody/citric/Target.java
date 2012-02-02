package org.netmelody.citric;

import java.util.SortedSet;

public final class Target implements ArtefactStream {
    
    private final ArtefactStream parent;
    private final Time duration;

    public Target(ArtefactStream parent) {
        this(parent, Time.of(1));
    }

    public Target(ArtefactStream parent, Time duration) {
        this.parent = parent;
        this.duration = duration;
    }

    public SortedSet<Artefact> availableAt(Time t) {
        return parent.availableAt(t.minus(duration));
    }
}
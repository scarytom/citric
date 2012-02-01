package org.netmelody.citric;

import java.util.SortedSet;

public final class Target implements ArtefactStream {
    
    private final ArtefactStream parent;

    public Target(ArtefactStream parent) {
        this.parent = parent;
    }

    public SortedSet<Artefact> availableAt(Time t) {
        return parent.availableAt(t.minus(Time.of(1)));
    }
}
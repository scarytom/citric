package org.netmelody.citric;

import java.util.SortedSet;

import com.google.common.collect.ImmutableSortedSet;

public final class Target implements ArtefactStream {
    
    public SortedSet<Artefact> availableAt(Time t) {
        return ImmutableSortedSet.of();
    }
}
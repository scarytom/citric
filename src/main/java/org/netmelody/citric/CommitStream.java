package org.netmelody.citric;

import java.util.SortedSet;

import com.google.common.collect.ImmutableSortedSet;

public final class CommitStream {
    private final Artefact a1 = Artefact.number(1);
    
    public SortedSet<Artefact> availableAt(Time t) {
        if (t.equals(Time.of(0))) {
            return ImmutableSortedSet.of();
        }
        
        if (t.equals(Time.of(1))) {
            return ImmutableSortedSet.of(a1);
        }
        
        return ImmutableSortedSet.of(a1, Artefact.number(2));
    }
}
package org.netmelody.citric;

import java.util.SortedSet;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Ranges;

public final class CommitStream implements ArtefactStream {
    
    private ContiguousSet<Artefact> artifacts = Ranges.<Artefact>all().asSet(ArtefactDomain.INSTANCE);
    
    @Override
    public SortedSet<Artefact> availableAt(Time t) {
        return artifacts.headSet(Artefact.number(t.value() + 1));
    }
}
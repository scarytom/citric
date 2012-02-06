package org.netmelody.citric;

import java.util.SortedSet;

import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Time;

import com.google.common.base.Optional;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Ranges;

public final class CommitStream implements ArtefactStream {
    
    private ContiguousSet<Artefact> artifacts = Ranges.<Artefact>all().asSet(ArtefactDomain.INSTANCE);
    
    @Override
    public SortedSet<Artefact> availableAt(Time t) {
        return artifacts.headSet(Artefact.number(t.value() + 1));
    }
    
    @Override
    public Optional<Artefact> imminentAt(Time t) {
        return Optional.of(availableAt(t.plusOne()).last());
    }
}
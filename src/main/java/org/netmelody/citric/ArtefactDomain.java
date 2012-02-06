package org.netmelody.citric;

import org.netmelody.citric.value.Artefact;

import com.google.common.collect.DiscreteDomain;

public final class ArtefactDomain extends DiscreteDomain<Artefact> {
    public static final ArtefactDomain INSTANCE = new ArtefactDomain();

    private ArtefactDomain() { }

    @Override
    public Artefact next(Artefact artefact) {
        return artefact.next(); 
    }

    @Override
    public Artefact previous(Artefact artefact) {
        return artefact.previous(); 
    }

    @Override
    public long distance(Artefact start, Artefact end) {
        return start.distanceTo(end);
    }

    @Override
    public Artefact minValue() {
        return Artefact.number(1);
    }

    @Override
    public Artefact maxValue() {
        return Artefact.number(Integer.MAX_VALUE);
    }
}
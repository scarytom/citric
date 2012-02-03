package org.netmelody.citric;

import java.util.SortedSet;

import com.google.common.base.Optional;

public interface ArtefactStream {

    SortedSet<Artefact> availableAt(Time t);

    Optional<Artefact> imminentAt(Time t);
}
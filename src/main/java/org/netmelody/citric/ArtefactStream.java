package org.netmelody.citric;

import java.util.SortedSet;

import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Time;

import com.google.common.base.Optional;

public interface ArtefactStream {

    SortedSet<Artefact> availableAt(Time t);

    Optional<Artefact> imminentAt(Time t);
}
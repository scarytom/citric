package org.netmelody.citric;

import java.util.SortedSet;

public interface ArtifactStream {

    SortedSet<Artefact> availableAt(Time t);

}
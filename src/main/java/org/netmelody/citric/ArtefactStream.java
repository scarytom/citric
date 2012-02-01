package org.netmelody.citric;

import java.util.SortedSet;

public interface ArtefactStream {

    SortedSet<Artefact> availableAt(Time t);

}
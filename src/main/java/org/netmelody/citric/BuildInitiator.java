package org.netmelody.citric;

import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Time;
import org.netmelody.citric.value.TimedArtefact;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public interface BuildInitiator {

    Optional<Artefact> determineNextBuild(Time t, Optional<TimedArtefact> previousBuild,
                                          ArtefactStream parentTarget,
                                          ImmutableList<ArtefactStream> siblingTargets);
}
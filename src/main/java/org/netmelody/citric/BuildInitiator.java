package org.netmelody.citric;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public interface BuildInitiator {

	Optional<TimedArtefact> determineNextBuild(Time t,
			Optional<TimedArtefact> previousBuild, ArtefactStream parentTarget,
			ImmutableList<ArtefactStream> siblingTargets);

}
package org.netmelody.citric;

import java.util.SortedSet;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class SimpleBuildInitiator implements BuildInitiator {
	
    @Override
	public Optional<TimedArtefact> determineNextBuild(Time t, Optional<TimedArtefact> previousBuild,
			                                          ArtefactStream parentTarget, ImmutableList<ArtefactStream> siblingTargets) {
  
    	final SortedSet<Artefact> available = parentTarget.availableAt(t);
        
        if (available.isEmpty()) {
            return Optional.absent();
        }
        
        final Artefact candidate = available.last();
        if (previousBuild.isPresent() && (candidate.compareTo(previousBuild.orNull().artefact()) <= 0)) {
            return Optional.absent();
        }
        
        return Optional.of(new TimedArtefact(t, candidate)); 
    }
}
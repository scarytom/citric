package org.netmelody.citric;

import java.util.SortedSet;

import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Time;
import org.netmelody.citric.value.TimedArtefact;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class SimpleBuildInitiator implements BuildInitiator {
	
    @Override
	public Optional<Artefact> determineNextBuild(Time t, Optional<TimedArtefact> previousBuild,
			                                          ArtefactStream parentTarget, ImmutableList<ArtefactStream> siblingTargets) {
  
    	final SortedSet<Artefact> available = parentTarget.availableAt(t);
        
        if (available.isEmpty()) {
            return Optional.absent();
        }
        
        final Artefact candidate = available.last();
        if (previousBuild.isPresent() && (candidate.compareTo(previousBuild.orNull().artefact()) <= 0)) {
            return Optional.absent();
        }
        
        return Optional.of(candidate); 
    }
}
package org.netmelody.citric;


import java.util.SortedSet;

import org.netmelody.citric.utils.HeadedSortedSet;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSortedSet;

public final class Target implements ArtefactStream {
    
    private final ArtefactStream parent;
    private final Time duration;
    
    public Target(ArtefactStream parent) {
        this(parent, Time.of(1));
    }

    public Target(ArtefactStream parent, Time duration) {
        this.parent = parent;
        this.duration = duration;
    }
    
    private Optional<TimedArtefact> currentBuild(Time t) {
        if (t.compareTo(Time.of(0)) <= 0) {
            return Optional.absent();
        }
        
        final Optional<TimedArtefact> previousBuild = currentBuild(t.minus(Time.of(1)));
        if (previousBuild.isPresent()) {
            if (previousBuild.orNull().time().compareTo(t.minus(duration)) > 0) {
                return previousBuild;
            }
        }
        
        return determineNextBuild(t, previousBuild);
    }

    private Optional<TimedArtefact> determineNextBuild(Time t, Optional<TimedArtefact> previousBuild) {
        final SortedSet<Artefact> available = parent.availableAt(t);
        
        if (available.isEmpty()) {
            return Optional.absent();
        }
        
        final Artefact candidate = available.first();
        if (previousBuild.isPresent() && (candidate.compareTo(previousBuild.orNull().artefact()) <= 0)) {
            return Optional.absent();
        }
        
        return Optional.of(new TimedArtefact(t, candidate)); 
    }

    public SortedSet<Artefact> availableAt(Time t) {
        if (t.compareTo(Time.of(0)) <= 0) {
            return ImmutableSortedSet.of();
        }
        
        final SortedSet<Artefact> previouslyAvailable = availableAt(t.minus(Time.of(1)));
        final Optional<TimedArtefact> currentBuild = currentBuild(t.minus(Time.of(1)));
        
        if (currentBuild.isPresent() && (currentBuild.orNull().time().compareTo(t.minus(duration)) <= 0)) {
            return new HeadedSortedSet<Artefact>(previouslyAvailable, currentBuild.orNull().artefact());
        }
        return previouslyAvailable;
    }
}
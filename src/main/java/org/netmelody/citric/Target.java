package org.netmelody.citric;


import java.util.SortedSet;

import org.netmelody.citric.utils.HeadedSortedSet;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

public final class Target implements ArtefactStream {
    
    private final ArtefactStream parent;
    private final Time duration;
    private final ImmutableList<ArtefactStream> siblings;
    private final BuildInitiator initiator = new SimpleBuildInitiator();
    
    private final LoadingCache<Time, Optional<TimedArtefact>> buildCache = CacheBuilder.newBuilder().build(CacheLoader.from(builds()));
    private final LoadingCache<Time, SortedSet<Artefact>> artefactCache = CacheBuilder.newBuilder().build(CacheLoader.from(artefacts()));
    
    public Target(ArtefactStream parent) {
        this(parent, Time.of(1));
    }

    public Target(ArtefactStream parent, Time duration) {
    	this(parent, ImmutableList.<ArtefactStream>of(), duration);
    }

    public Target(ArtefactStream parent, ImmutableList<ArtefactStream> siblings, Time duration) {
        this.parent = parent;
		this.siblings = siblings;
        this.duration = duration;
    }

	private Function<Time, Optional<TimedArtefact>> builds() {
        return new Function<Time, Optional<TimedArtefact>>() {
            @Override
            public Optional<TimedArtefact> apply(Time t) {
                if (t.compareTo(Time.of(0)) <= 0) {
                    return Optional.absent();
                }
                
                final Optional<TimedArtefact> previousBuild = buildCache.getUnchecked(t.minus(Time.of(1)));
                if (previousBuild.isPresent()) {
                    if (previousBuild.orNull().time().compareTo(t.minus(duration)) > 0) {
                        return previousBuild;
                    }
                }
                
                return initiator.determineNextBuild(t, previousBuild, parent, siblings);
            }
        };
    }

    private Function<Time, SortedSet<Artefact>> artefacts() {
        return new Function<Time, SortedSet<Artefact>>() {
            @Override
            public SortedSet<Artefact> apply(Time t) {
                if (t.compareTo(Time.of(0)) <= 0) {
                    return ImmutableSortedSet.of();
                }
                
                final SortedSet<Artefact> previouslyAvailable = artefactCache.getUnchecked(t.minus(Time.of(1)));
                final Optional<TimedArtefact> currentBuild = buildCache.getUnchecked(t.minus(Time.of(1)));
                
                if (currentBuild.isPresent() && (currentBuild.orNull().time().compareTo(t.minus(duration)) <= 0)) {
                    return new HeadedSortedSet<Artefact>(previouslyAvailable, currentBuild.orNull().artefact());
                }
                return previouslyAvailable;
            }
        };
    }
    
    public SortedSet<Artefact> availableAt(Time t) {
        return artefactCache.getUnchecked(t);
    }
    
    public Optional<Artefact> imminentAt(Time t) {
    	final Optional<TimedArtefact> imminent = this.buildCache.getUnchecked(t);
		return imminent.isPresent() ? Optional.of(imminent.orNull().artefact()) : Optional.<Artefact>absent();
    }
}
package org.netmelody.citric;


import java.util.SortedSet;

import org.netmelody.citric.utils.HeadedSortedSet;
import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Duration;
import org.netmelody.citric.value.Time;
import org.netmelody.citric.value.TimedArtefact;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

public final class Target implements ArtefactStream {
    
    private final BuildInitiator initiator;
    private final ArtefactStream parent;
    private final ImmutableList<ArtefactStream> siblings;
    private final Duration duration;
    
    private final LoadingCache<Time, Optional<TimedArtefact>> buildCache = CacheBuilder.newBuilder().concurrencyLevel(1).build(CacheLoader.from(builds()));
    private final LoadingCache<Time, SortedSet<Artefact>> artefactCache = CacheBuilder.newBuilder().concurrencyLevel(1).build(CacheLoader.from(artefacts()));
    
    public Target(ArtefactStream parent) {
        this(parent, Duration.of(1));
    }

    public Target(ArtefactStream parent, Duration duration) {
        this(new SimpleBuildInitiator(), parent, ImmutableList.<ArtefactStream>of(), duration);
    }

    public Target(BuildInitiator initiator, ArtefactStream parent, ImmutableList<ArtefactStream> siblings, Duration duration) {
        this.initiator = initiator;
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
                
                final Optional<TimedArtefact> previousBuild = buildCache.getUnchecked(t.minusOne());
                if (previousBuild.isPresent()) {
                    if (previousBuild.orNull().time().compareTo(t.minus(duration)) > 0) {
                        return previousBuild;
                    }
                }
                
                final Optional<Artefact> nextArtefact = initiator.determineNextBuild(t, previousBuild, parent, siblings);
                
                return nextArtefact.isPresent() ? Optional.of(new TimedArtefact(t, nextArtefact.orNull()))
                                                : Optional.<TimedArtefact>absent();
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
                
                final SortedSet<Artefact> previouslyAvailable = artefactCache.getUnchecked(t.minusOne());
                final Optional<TimedArtefact> currentBuild = buildCache.getUnchecked(t.minusOne());
                
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
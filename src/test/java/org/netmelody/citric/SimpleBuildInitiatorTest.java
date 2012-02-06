package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Time;
import org.netmelody.citric.value.TimedArtefact;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

public final class SimpleBuildInitiatorTest {

    private final Mockery context = new Mockery();
    
    private final SimpleBuildInitiator initiator = new SimpleBuildInitiator();
    
    @Test public void
    returnsNothingWhenNoUpstreamArtefactAvailable() {
        final ArtefactStream parentStream = context.mock(ArtefactStream.class);
        
        context.checking(new Expectations() {{
            allowing(parentStream).availableAt(Time.of(1)); will(returnValue(ImmutableSortedSet.of()));
        }});

        final Optional<Artefact> build = initiator.determineNextBuild(Time.of(1), Optional.<TimedArtefact>absent(),
                                                                      parentStream, ImmutableList.<ArtefactStream>of());
        assertThat(build.orNull(), is(nullValue()));
    }
    
    @Test public void
    returnsLatestAvailableUpstreamArtefact() {
        final ArtefactStream parentStream = context.mock(ArtefactStream.class);
        
        context.checking(new Expectations() {{
            allowing(parentStream).availableAt(Time.of(1));
                will(returnValue(ImmutableSortedSet.of(Artefact.number(1), Artefact.number(2))));
        }});

        final Optional<Artefact> build = initiator.determineNextBuild(Time.of(1), Optional.<TimedArtefact>absent(),
                                                                      parentStream, ImmutableList.<ArtefactStream>of());
        assertThat(build.orNull(), is(Artefact.number(2)));
    }

}
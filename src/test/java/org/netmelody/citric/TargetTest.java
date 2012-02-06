package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Duration;
import org.netmelody.citric.value.Time;

import com.google.common.collect.ImmutableSortedSet;

public final class TargetTest {

    private final Mockery context = new Mockery();
    
    @Test public void
    buildsNothingAtFirst() {
        final ArtefactStream stream = context.mock(ArtefactStream.class);
        final Target target = new Target(stream);
        
        context.checking(new Expectations() {{
            allowing(stream).availableAt(Time.of(0)); will(returnValue(ImmutableSortedSet.of()));
        }});
        
        assertThat(target.availableAt(Time.of(0)), is(Matchers.<Artefact>emptyIterable()));
    }
    
    @Test public void
    buildsParentArtefactWithGivenBuildDuration() {
        final ArtefactStream stream = context.mock(ArtefactStream.class);
        final Target target = new Target(stream);
        
        context.checking(new Expectations() {{
            allowing(stream).availableAt(Time.of(1)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1))));
            allowing(stream).availableAt(Time.of(2)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1))));
        }});
        
        assertThat(target.availableAt(Time.of(1)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(2)), is(Matchers.contains(Artefact.number(1))));
    }
    
    @Test public void
    delaysArtefactRateWhenBuildDurationIsLong() {
        final ArtefactStream stream = context.mock(ArtefactStream.class);
        final Target target = new Target(stream, Duration.of(5));
        
        context.checking(new Expectations() {{
            allowing(stream).availableAt(Time.of(1)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1))));
            allowing(stream).availableAt(Time.of(6)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1), Artefact.number(2))));
        }});
        
        assertThat(target.availableAt(Time.of(5)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(6)), is(Matchers.contains(Artefact.number(1))));
    }
    
    @Test public void
    imminentlySuppliesNextArtifact() {
        final ArtefactStream stream = context.mock(ArtefactStream.class);
        final Target target = new Target(stream, Duration.of(5));
        
        context.checking(new Expectations() {{
            allowing(stream).availableAt(Time.of(1)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1))));
            allowing(stream).availableAt(Time.of(6)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1), Artefact.number(2))));
        }});
        
        assertThat(target.imminentAt(Time.of(0)).orNull(), is(nullValue()));
        assertThat(target.imminentAt(Time.of(1)).orNull(), is(Artefact.number(1)));
        assertThat(target.imminentAt(Time.of(2)).orNull(), is(Artefact.number(1)));
        assertThat(target.imminentAt(Time.of(3)).orNull(), is(Artefact.number(1)));
        assertThat(target.imminentAt(Time.of(4)).orNull(), is(Artefact.number(1)));
        assertThat(target.imminentAt(Time.of(5)).orNull(), is(Artefact.number(1)));
        assertThat(target.imminentAt(Time.of(6)).orNull(), is(Artefact.number(2)));
    }
    
    @Test public void
    realisticallySimulatesCommitBuilds() {
        final Target target = new Target(new CommitStream(), Duration.of(2));
        assertThat(target.availableAt(Time.of(0)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(1)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(2)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(3)), is(Matchers.contains(Artefact.number(1))));
        assertThat(target.availableAt(Time.of(4)), is(Matchers.contains(Artefact.number(1))));
        assertThat(target.availableAt(Time.of(5)), is(Matchers.contains(Artefact.number(1), Artefact.number(3))));
        assertThat(target.availableAt(Time.of(6)), is(Matchers.contains(Artefact.number(1), Artefact.number(3))));
    }
}

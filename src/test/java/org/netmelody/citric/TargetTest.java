package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableListIterator;

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
        final Target target = new Target(stream, Time.of(5));
        
        context.checking(new Expectations() {{
            allowing(stream).availableAt(Time.of(1)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1))));
            allowing(stream).availableAt(Time.of(6)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1), Artefact.number(2))));
        }});
        
        assertThat(target.availableAt(Time.of(5)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(6)), is(Matchers.contains(Artefact.number(1))));
    }
    
    @Test public void
    realisticallySimulatesCommitBuilds() {
        final Target target = new Target(new CommitStream(), Time.of(2));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(1)), Artefact.class)));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(2)), Artefact.class)));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(3)), Artefact.class)));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(4)), Artefact.class)));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(5)), Artefact.class)));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(6)), Artefact.class)));
        System.out.println(Arrays.deepToString(Iterables.toArray(target.availableAt(Time.of(7)), Artefact.class)));
        assertThat(target.availableAt(Time.of(0)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(1)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(2)), is(Matchers.<Artefact>emptyIterable()));
        assertThat(target.availableAt(Time.of(3)), is(Matchers.contains(Artefact.number(1))));
        assertThat(target.availableAt(Time.of(4)), is(Matchers.contains(Artefact.number(1))));
        assertThat(target.availableAt(Time.of(5)), is(Matchers.contains(Artefact.number(1), Artefact.number(3))));
        assertThat(target.availableAt(Time.of(6)), is(Matchers.contains(Artefact.number(1), Artefact.number(3))));
    }
}

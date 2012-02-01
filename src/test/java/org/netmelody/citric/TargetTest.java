package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

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
    buildsParentArtefactWithGivenBuildTime() {
        final ArtefactStream stream = context.mock(ArtefactStream.class);
        final Target target = new Target(stream);
        
        context.checking(new Expectations() {{
            allowing(stream).availableAt(Time.of(1)); will(returnValue(ImmutableSortedSet.of(Artefact.number(1))));
        }});
        
        assertThat(target.availableAt(Time.of(2)), is(Matchers.contains(Artefact.number(1))));
    }
}

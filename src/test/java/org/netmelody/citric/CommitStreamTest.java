package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.Test;

public final class CommitStreamTest {
    
    @Test public void
    providesNoArtefactsAtTimeZero() {
        final CommitStream stream = new CommitStream();
        
        assertThat(stream.availableAt(Time.of(0)), is(Matchers.<Artefact>emptyIterable()));
    }
    
    @Test public void
    providesOneArtefactAtTimeOne() {
        final CommitStream stream = new CommitStream();
        
        assertThat(stream.availableAt(Time.of(1)), contains(Artefact.number(1)));
    }

}

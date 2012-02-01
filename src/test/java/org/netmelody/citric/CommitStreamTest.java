package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public final class CommitStreamTest {
    @Test public void
    providesARegularStreamOfArtefacts() {
        final CommitStream stream = new CommitStream();
        
        assertThat(stream.availableAt(Time.of(0)), is(emptyIterable()));
    }
}

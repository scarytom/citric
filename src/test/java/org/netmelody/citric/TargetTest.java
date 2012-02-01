package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.Test;

public final class TargetTest {

    @Test public void
    reportsWhatItHasBuiltToDate() {
        final Target target = new Target();
        assertThat(target.availableAt(Time.of(0)), is(Matchers.<Artefact>emptyIterable()));
    }
}

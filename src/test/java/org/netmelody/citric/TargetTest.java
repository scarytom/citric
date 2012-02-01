package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public final class TargetTest {

    @Test public void
    reportsWhatItHasBuiltToDate() {
        final Target target = new Target();
        assertThat(target.available(), is(emptyIterable()));
    }
}

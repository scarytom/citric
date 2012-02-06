package org.netmelody.citric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Time;

import com.google.common.collect.Iterables;

public final class CommitStreamTest {
    
    @Test public void
    providesNoArtefactsAtTimeZero() {
        final CommitStream commits = new CommitStream();
        
        assertThat(commits.availableAt(Time.of(0)), is(Matchers.<Artefact>emptyIterable()));
    }

    @Test public void
    providesOneArtefactAtTimeOne() {
        final CommitStream commits = new CommitStream();
        
        assertThat(commits.availableAt(Time.of(1)), contains(Artefact.number(1)));
    }

    @Test public void
    providesTwoArtefactsAtTimeTwo() {
        final CommitStream commits = new CommitStream();
        
        assertThat(commits.availableAt(Time.of(2)), contains(Artefact.number(1), Artefact.number(2)));
    }

    @Test public void
    includesTheIdenticalArtifactFromTimeOneInThosePresentAtTimeTwo() {
        final CommitStream commits = new CommitStream();
        
        final Artefact artefact1 = Iterables.getOnlyElement(commits.availableAt(Time.of(1)));
        assertThat(commits.availableAt(Time.of(2)), hasItem(sameInstance(artefact1)));
    }
    
    @Test public void
    imminentlySuppliesNextArtifact() {
        final CommitStream commits = new CommitStream();
        
        assertThat(commits.imminentAt(Time.of(0)).orNull(), is(Artefact.number(1)));
        assertThat(commits.imminentAt(Time.of(1)).orNull(), is(Artefact.number(2)));
    }
}
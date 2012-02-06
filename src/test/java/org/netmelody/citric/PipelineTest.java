package org.netmelody.citric;

import java.util.Arrays;

import org.junit.Test;
import org.netmelody.citric.value.Artefact;
import org.netmelody.citric.value.Duration;
import org.netmelody.citric.value.Time;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class PipelineTest {

    @Test public void
    canConstructASimpleLinearPipeline() {
        Pipeline.startingWithACommitStream()
                .followedByTargetTaking(Time.of(1));
    }
    
    @Test public void
    crazyPipeline() {
        final CommitStream commitStream = new CommitStream();
        
        final Target compile = new Target(commitStream, Duration.of(1));
        final Target unitTest = new Target(compile, Duration.of(3));
        final Target integrationTest = new Target(unitTest, Duration.of(5));
        final Target browserChrome = new Target(integrationTest, Duration.of(10));
        final Target browserFirefox = new Target(integrationTest, Duration.of(11));
        final Target browserIe = new Target(integrationTest, Duration.of(18));
        
        final Time n = Time.of(500);
        final SetView<Artefact> result = Sets.intersection(Sets.intersection(browserChrome.availableAt(n), browserFirefox.availableAt(n)), browserIe.availableAt(n));
        
        System.out.println(Arrays.deepToString(Iterables.toArray(result, Artefact.class)));
    }
}
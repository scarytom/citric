package org.netmelody.citric;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class MultiBuildPipelineTest {

    @Test public void
    crazyPipeline() {
        final CommitStream commitStream = new CommitStream();
        
        final Target compile = new Target(commitStream, Time.of(1));
        final Target unitTest = new Target(compile, Time.of(3));
        final Target integrationTest = new Target(unitTest, Time.of(5));
        final Target browserChrome = new Target(integrationTest, Time.of(10));
        final Target browserFirefox = new Target(integrationTest, Time.of(11));
        final Target browserIe = new Target(integrationTest, Time.of(18));
        
        final Time n = Time.of(500);
        final SetView<Artefact> result = Sets.intersection(Sets.intersection(browserChrome.availableAt(n), browserFirefox.availableAt(n)), browserIe.availableAt(n));
        
        System.out.println(Arrays.deepToString(Iterables.toArray(result, Artefact.class)));
    }
}
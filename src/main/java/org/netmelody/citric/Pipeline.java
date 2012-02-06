package org.netmelody.citric;

import org.netmelody.citric.value.Duration;

public final class Pipeline {

    public static PipelineBuilder startingWithACommitStream() {
        return new PipelineBuilder();
    }
    
    public static final class PipelineBuilder {
        public PipelineBuilder followedByTargetTaking(Duration duration) {
            return this;
        }
    }
}

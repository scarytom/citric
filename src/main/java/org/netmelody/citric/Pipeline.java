package org.netmelody.citric;

import org.netmelody.citric.value.Time;

public final class Pipeline {

	public static PipelineBuilder startingWithACommitStream() {
		return new PipelineBuilder();
	}
	
	public static final class PipelineBuilder {
		public PipelineBuilder followedByTargetTaking(Time duration) {			
		    return this;
		}
	}
}

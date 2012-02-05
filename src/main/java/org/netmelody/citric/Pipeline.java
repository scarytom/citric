package org.netmelody.citric;

public final class Pipeline {

	public static PipelineBuilder startingWithACommitStream() {
		return null;
	}
	
	public static final class PipelineBuilder {
		public PipelineBuilder followedByTargetTaking(Time duration) {			
		    return this;
		}
	}
}

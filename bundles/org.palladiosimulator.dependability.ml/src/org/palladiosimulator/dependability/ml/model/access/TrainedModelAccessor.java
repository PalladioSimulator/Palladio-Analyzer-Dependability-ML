package org.palladiosimulator.dependability.ml.model.access;

import java.net.URI;

public interface TrainedModelAccessor<In, Out> {
	
	public boolean canAccess(URI modelURI);
	
	public void load(URI modelURI);
	
	public Out query(In inputData);
}

package org.palladiosimulator.dependability.ml.model.access;

import java.net.URI;
import java.util.List;

public interface TrainedModelAccessor<In, Out> {
	
	public boolean canAccess(URI modelURI);
	
	public void load(URI modelURI);
	
	public List<Out> query(In inputData);
}

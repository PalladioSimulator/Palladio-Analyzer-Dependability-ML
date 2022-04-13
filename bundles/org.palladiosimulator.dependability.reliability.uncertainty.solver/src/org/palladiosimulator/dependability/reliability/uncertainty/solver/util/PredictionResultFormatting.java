package org.palladiosimulator.dependability.reliability.uncertainty.solver.util;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.palladiosimulator.dependability.reliability.uncertainty.solver.markov.ReliabilityPredictionResultPerScenario;
import org.palladiosimulator.dependability.reliability.uncertainty.solver.model.DiscreteUncertaintyStateSpace.UncertaintyState;

import com.google.common.collect.Lists;

public class PredictionResultFormatting {

	public static String asString(List<UncertaintyState> uncertainties) {
		return String.join(",", uncertainties.stream()
				.map(each -> each.getValue().get())
				.collect(toList()));
	}
	
	public static Iterator<ReliabilityPredictionResultPerScenario> sortByUncertainties(Set<ReliabilityPredictionResultPerScenario> unsorted) {
		var sorted = new TreeSet<ReliabilityPredictionResultPerScenario>(new Comparator<ReliabilityPredictionResultPerScenario>() {

			@Override
			public int compare(ReliabilityPredictionResultPerScenario r1, ReliabilityPredictionResultPerScenario r2) {
				var strUncertainties1 = asString(r1.getUncertainties());
				var strUncertainties2 = asString(r2.getUncertainties());
				var uncertainties = Lists.newArrayList(strUncertainties1, strUncertainties2);
				
				Collections.sort(uncertainties);
				
				return uncertainties.get(0).equals(strUncertainties1) ? -1 : 1;
			}
		
		});
		
		sorted.addAll(unsorted);
		
		return sorted.iterator();
	}
}

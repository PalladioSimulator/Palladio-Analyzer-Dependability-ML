package org.palladiosimulator.dependability.reliability.uncertainty.solver.util;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.function.Predicate;

import org.palladiosimulator.envdyn.environment.staticmodel.GroundProbabilisticNetwork;
import org.palladiosimulator.envdyn.environment.staticmodel.GroundRandomVariable;

import com.google.common.collect.Sets;

public class UncertaintyModelUtil {

	public static Set<GroundRandomVariable> filterRandomVariablesOnlyWithParent(GroundProbabilisticNetwork network) {
		var withParents = filterRandomVariablesWithParent(network);
		var withoutChilds = filterRandomVariablesWithoutChilds(network);
		
		return Sets.intersection(withParents, withoutChilds);
	}
	
	public static Set<GroundRandomVariable> filterRandomVariablesWithParent(GroundProbabilisticNetwork network) {
		return network.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream())
				.filter(variablesWithParents())
				.collect(toSet());
	}
	
	public static Set<GroundRandomVariable> filterRandomVariablesWithoutChilds(GroundProbabilisticNetwork network) {
		var allVariables = network.getLocalProbabilisticModels().stream()
				.flatMap(each -> each.getGroundRandomVariables().stream())
				.collect(toSet());
		
		var dependencies = allVariables.stream()
				.flatMap(each -> each.getDependenceStructure().stream())
				.collect(toSet());
		
		Set<GroundRandomVariable> result = Sets.newHashSet();
		for (GroundRandomVariable each : allVariables) {
			if (dependencies.stream().allMatch(d -> d.getSource().getId().equals(each.getInstantiatedTemplate().getId()) == false)) {
				result.add(each);
			}
		}
		return result;
	}
	
	private static Predicate<GroundRandomVariable> variablesWithParents() {
		return v -> v.getDependenceStructure().isEmpty() == false;
	}
	
}

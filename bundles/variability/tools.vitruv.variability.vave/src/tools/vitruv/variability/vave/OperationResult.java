package tools.vitruv.variability.vave;

import java.util.HashMap;
import java.util.Map;

import tools.vitruv.variability.vave.consistency.ConsistencyResult;
import tools.vitruv.variability.vave.consistency.ConsistencyRule;

/**
 * Stores the results of the consistency rules that were triggered during the execution of a vave operation in addition to the actual result of the operation itself.
 */
public abstract class OperationResult<T> {
	// key is consistency rule and value is the result that was provided by the rule
	private Map<Class<? extends ConsistencyRule>, ConsistencyResult> consistencyResults = new HashMap<>();

	public ConsistencyResult getConsistencyResult(Class<? extends ConsistencyRule> consistencyRuleType) {
		return this.consistencyResults.get(consistencyRuleType);
	}
	
	public void addConsistencyResult(Class<? extends ConsistencyRule> consistencyRuleType, ConsistencyResult consistencyResult) {
		this.consistencyResults.put(consistencyRuleType, consistencyResult);
	}

	public T getResult() {
		throw new RuntimeException("This operation does not provide a result.");
	}
	
	public void setResult(T result) {
		throw new RuntimeException("This operation does not provide a result.");
	}
}

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

	/**
	 * Returns the result produced be the consistency rule with the given type during the execution of a vave operation.
	 * 
	 * @param consistencyRuleType The type of the consistency rule whose result shall be returned.
	 * @return The consistency result produced by the given consistency rule.
	 */
	public ConsistencyResult getConsistencyResult(Class<? extends ConsistencyRule> consistencyRuleType) {
		return this.consistencyResults.get(consistencyRuleType);
	}
	
	/**
	 * Assigns the result produced by a consistency rule to its type and stores it in this operation result.
	 * 
	 * @param consistencyRuleType The type of the consistency rule.
	 * @param consistencyResult The consistency result produced by the given consistency rule.
	 */
	public void addConsistencyResult(Class<? extends ConsistencyRule> consistencyRuleType, ConsistencyResult consistencyResult) {
		this.consistencyResults.put(consistencyRuleType, consistencyResult);
	}

	/**
	 * Returns the result of a vave operation.
	 * 
	 * @return The result of an operation.
	 */
	public T getResult() {
		throw new RuntimeException("This operation does not provide a result.");
	}
	
	/**
	 * Sets the result of a vave operation.
	 * 
	 * @param result The provided operation result.
	 */
	public void setResult(T result) {
		throw new RuntimeException("This operation does not provide a result.");
	}
}

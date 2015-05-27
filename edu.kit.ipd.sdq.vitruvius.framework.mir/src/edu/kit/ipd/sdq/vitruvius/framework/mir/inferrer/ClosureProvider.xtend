package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.xbase.XExpression
import com.google.inject.Singleton

@Singleton
class ClosureProvider {
	private Map<XExpression, Map<EPackage, TreeAppendableClosure>> assignmentClosures
	private Map<XExpression, Map<EPackage, TreeAppendableClosure>> equalityClosures
	
	@Inject Provider<TreeAppendableClosure> closureProvider;
	@Inject Provider<TreeAppendableConjunctionClosure> conjunctionClosureProvider;
	
	new() {
		reset
	}
	
	public def void reset() {
		assignmentClosures = newHashMap
		equalityClosures = newHashMap		
	}
	
	/**
	 * Returns a {@link TreeAppendableClosure} that corresponds to the given expression (block)
	 * and the package as argument.
	 * <p>
	 * The closure is created if it not already exists for this helper.
	 */
	public def TreeAppendableClosure getAssignmentClosure(XExpression expression, EPackage pkg) {
		if (!assignmentClosures.containsKey(expression))
			assignmentClosures.put(expression, new HashMap<EPackage, TreeAppendableClosure>());
		
		val closureMap = assignmentClosures.get(expression);
		
		if (!closureMap.containsKey(pkg))
			closureMap.put(pkg, closureProvider.get());

//		println(hashCode + ".getAssignmentClosure: " + expression.hashCode + " => " + closureMap.get(pkg).hashCode)

		return closureMap.get(pkg);
	}
	
	/**
	 * Returns a {@link TreeAppendableClosure} that corresponds to the given expression (block)
	 * and the package as argument.
	 * <p>
	 * The closure is created if it not already exists for this helper.
	 * <p>
	 * For equality closures, a {@link TreeAppendableConjunctionClosure} is used.
	 */
	public def TreeAppendableClosure getEqualityClosure(XExpression expression, EPackage pkg) {
		if (!equalityClosures.containsKey(expression))
			equalityClosures.put(expression, new HashMap<EPackage, TreeAppendableClosure>());
		
		val closureMap = equalityClosures.get(expression);
		
		if (!closureMap.containsKey(pkg))
			closureMap.put(pkg, conjunctionClosureProvider.get());
			
//		println(hashCode + ".getEqualityClosure: " + expression.hashCode + " => " + closureMap.get(pkg).hashCode)
		
		return closureMap.get(pkg);
	}
	
	public def Map<EPackage, TreeAppendableClosure> getAssignmentClosureMap(XExpression expression) {
		return assignmentClosures.get(expression)?.immutableCopy ?: newHashMap()
	}
	
	public def Map<EPackage, TreeAppendableClosure> getEqualityClosureMap(XExpression expression) {
		return equalityClosures.get(expression)?.immutableCopy ?: newHashMap()
	}
}

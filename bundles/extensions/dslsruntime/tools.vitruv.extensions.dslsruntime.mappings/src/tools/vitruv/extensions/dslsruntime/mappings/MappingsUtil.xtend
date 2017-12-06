package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.emf.ecore.EObject
import java.util.List
import java.util.Set
import com.google.common.collect.Sets
import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.commons.util.java.Pair
import edu.kit.ipd.sdq.commons.util.java.Triple
import edu.kit.ipd.sdq.commons.util.java.Quadruple
import edu.kit.ipd.sdq.commons.util.java.Monuple
import edu.kit.ipd.sdq.commons.util.java.Quintuple

/**
 * Utility class providing static methods that are used by code that is generated for mappings 
 * that were defined using the mappings language.
 */
@Utility
class MappingsUtil {

	def static boolean allElementsStillExisting(EObject... elements) {
		for (element : elements) {
			if (element.eResource === null) {
				return false
			}
		}
		return true
	}
	
	/**
	 * Returns the cartesian product of the given sets. 
	 * (Used by generated mapping code for computing all mapping candidate combinations.)
	 */
	private def static <T> Iterable<List<T>> untypedCartesianProduct(Set<? extends T>... sets) {
		return Sets.cartesianProduct(sets)
	}
	
	/**
	 * Returns the correctly typed cartesian product of the given set. 
	 */
	def static <A> Iterable<Monuple<A>> typedCartesianProduct(Set<A> aSet) {
		return untypedCartesianProduct(aSet).map[new Monuple<A>(
			it.get(0)
		)]
	}
	
	/**
	 * Returns the correctly typed cartesian product of the given sets. 
	 */
	def static <A,B> Iterable<Pair<A,B>> typedCartesianProduct(Set<A> aSet, Set<B> bSet) {
		return untypedCartesianProduct(aSet, bSet).map[new Pair<A,B>(
			it.get(0) as A,
			it.get(1) as B
		)]
	}
	
	/**
	 * Returns the correctly typed cartesian product of the given sets. 
	 */
	def static <A,B,C> Iterable<Triple<A,B,C>> typedCartesianProduct(Set<A> aSet, Set<B> bSet, Set<C> cSet) {
		return untypedCartesianProduct(aSet, bSet, cSet).map[new Triple<A,B,C>(
			it.get(0) as A,
			it.get(1) as B,
			it.get(2) as C
		)]
	}
	
	/**
	 * Returns the correctly typed cartesian product of the given sets. 
	 */
	def static <A,B,C,D> Iterable<Quadruple<A,B,C,D>> typedCartesianProduct(Set<A> aSet, Set<B> bSet, Set<C> cSet, Set<D> dSet) {
		return untypedCartesianProduct(aSet, bSet, cSet, dSet).map[new Quadruple<A,B,C,D>(
			it.get(0) as A,
			it.get(1) as B,
			it.get(2) as C,
			it.get(3) as D
		)]
	}
	
	/**
	 * Returns the correctly typed cartesian product of the given sets. 
	 */
	def static <A,B,C,D,E> Iterable<Quintuple<A,B,C,D,E>> typedCartesianProduct(Set<A> aSet, Set<B> bSet, Set<C> cSet, Set<D> dSet, Set<E> eSet) {
		return untypedCartesianProduct(aSet, bSet, cSet, dSet).map[new Quintuple<A,B,C,D,E>(
			it.get(0) as A,
			it.get(1) as B,
			it.get(2) as C,
			it.get(3) as D,
			it.get(4) as E
		)]
	}
}
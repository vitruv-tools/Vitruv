package tools.vitruv.extensions.dslsruntime.mappings

import org.eclipse.emf.ecore.EObject
import java.util.List
import java.util.Set
import com.google.common.collect.Sets
import edu.kit.ipd.sdq.activextendannotations.Utility

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
	 * Used by generated mapping code for computing all mapping candidate combinations.
	 */
	def static <T> Iterable<List<T>> cartesianProduct(Set<? extends T>... sets) {
		return Sets.cartesianProduct(sets)
	}
}
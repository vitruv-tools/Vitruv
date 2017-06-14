package tools.vitruv.framework.versioning

import java.util.Collection
import org.eclipse.xtext.xbase.lib.Functions.Function2
import tools.vitruv.framework.versioning.impl.DistanceCalculatorImpl

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-13
 */
interface DistanceCalculator {
	DistanceCalculator instance = DistanceCalculatorImpl::init

	def <T> int levenshteinDistance(
		Collection<T> sequence1,
		Collection<T> sequence2,
		Function2<T, T, Boolean> comparison
	)

	def <T> int levenshteinDistance(
		Collection<T> sequence1,
		Collection<T> sequence2
	)
}

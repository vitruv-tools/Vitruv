package tools.vitruv.framework.versioning

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function2
import tools.vitruv.framework.versioning.impl.DistanceCalculatorImpl

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface DistanceCalculator {
	DistanceCalculator instance = DistanceCalculatorImpl::init

	def <T> int levenshteinDistance(
		List<T> baseEchanges,
		List<T> compareEchanges,
		Function2<T, T, Boolean> comparison
	)
}

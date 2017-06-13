package tools.vitruv.framework.versioning.impl

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.xtext.xbase.lib.Functions.Function2
import tools.vitruv.framework.versioning.DistanceCalculator

class DistanceCalculatorImpl implements DistanceCalculator {
	static val logger = Logger::getLogger(ConflictDetectorImpl)

	static def DistanceCalculator init() {
		new DistanceCalculatorImpl
	}

	private new() {
	}

	override <T> int levenshteinDistance(
		List<T> baseEchanges,
		List<T> compareEchanges,
		Function2<T, T, Boolean> comparison
	) {
		logger.debug("LevenshteinDistance")
		val len0 = baseEchanges.length + 1
		val len1 = compareEchanges.length + 1

		// the array of distances                                                       
		var int[] cost = newIntArrayOfSize(len0)
		var int[] newcost = newIntArrayOfSize(len0)

		// initial cost of skipping prefix in String s0         
		for (i : 0 ..< len0)
			cost.set(i, i)

		// dynamically computing the array of distances                                  
		// transformation cost for each letter in s1   
		for (j : 1 ..< len1) {
			// initial cost of skipping prefix in String s1                             
			newcost.set(0, j)

			// transformation cost for each letter in s0                                
			for (i : 1 ..< len0) {
				// matching current letters in both strings                             
				var int match
				if (comparison.apply(baseEchanges.get(i - 1), compareEchanges.get(j - 1))) {
					match = 0
				} else {
					match = 1
				}

				// computing cost for each transformation                               
				val cost_replace = cost.get(i - 1) + match
				val cost_insert = cost.get(i) + 1
				val cost_delete = newcost.get(i - 1) + 1

				// keep minimum cost                                                    
				newcost.set(i, Math::min(Math::min(cost_insert, cost_delete), cost_replace))
			}

			// swap cost/newcost arrays                                                 
			val swap = cost
			cost = newcost
			newcost = swap
		}

		// the distance is the cost for transforming all letters in both strings        
		return cost.get(len0 - 1)
	}
}

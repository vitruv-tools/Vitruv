package tools.vitruv.framework.versioning.impl

import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.xtext.xbase.lib.Functions.Function2
import tools.vitruv.framework.versioning.DistanceCalculator

class DistanceCalculatorImpl implements DistanceCalculator {
	static extension Logger = Logger::getLogger(DistanceCalculatorImpl)

	static def DistanceCalculator init() {
		new DistanceCalculatorImpl
	}

	private new() {
	}

	override <T> int levenshteinDistance(
		Collection<T> sequence1,
		Collection<T> sequence2,
		Function2<T, T, Boolean> compareFunction
	) {
		debug("Start calculating levenshtein distance")
		val len0 = sequence1.length + 1
		val len1 = sequence2.length + 1

		// the array of distances            
		// initial cost of skipping prefix in String s0                              
		var int[] cost = 0 .. len0
		var int[] newcost = newIntArrayOfSize(len0)

		// dynamically computing the array of distances                                  
		// transformation cost for each letter in s1   
		for (j : 1 ..< len1) {
			// initial cost of skipping prefix in String s1                             
			newcost.set(0, j)

			// transformation cost for each letter in s0                                
			for (i : 1 ..< len0) {
				// matching current letters in both strings                             
				var int match
				if (compareFunction.apply(sequence1.get(i - 1), sequence2.get(j - 1))) {
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
		debug('''Levenshtein distance is «cost.get(len0 - 1)»''')
		return cost.get(len0 - 1)
	}

	override <T> levenshteinDistance(Collection<T> sequence1, Collection<T> sequence2) {
		levenshteinDistance(sequence1, sequence2, [T x, T y|x == y])
	}
}

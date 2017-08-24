package tools.vitruv.framework.change.echange.copy

import java.util.Set

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.copy.impl.EChangeCopierImpl

/**
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @since 2017-06-14
 * @version 0.2.0 
 */
interface EChangeCopier {
	def EChange copy(EChange e)

	static def EChangeCopier createEChangeCopier(
		Set<Pair<String, String>> replacePairs
	) {
		return new EChangeCopierImpl(replacePairs)
	}
}

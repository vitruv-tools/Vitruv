package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.EChangeCompareUtilImpl
import tools.vitruv.framework.change.echange.EChange

interface EChangeCompareUtil {
	static EChangeCompareUtil instance = EChangeCompareUtilImpl::init

	def boolean isEChangeEqual(EChange e1, EChange e2)

	def boolean isConflictingEachOther(EChange e1, EChange e2)

	def void addPair(Pair<String, String> pair)
}

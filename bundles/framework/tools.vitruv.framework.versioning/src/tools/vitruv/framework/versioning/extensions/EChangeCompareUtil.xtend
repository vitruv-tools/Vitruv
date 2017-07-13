package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.EChangeCompareUtilImpl
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.common.util.URI

interface EChangeCompareUtil {
	static EChangeCompareUtil instance = EChangeCompareUtilImpl::init

	def boolean isEChangeEqual(EChange e1, EChange e2)

	def void addPair(Pair<String, String> pair)

	def boolean containerIsRootAndMapped(String containerString, InternalEObject affectedContainer2)

	def String getComparableString(URI uri)
}

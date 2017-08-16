package tools.vitruv.framework.versioning.extensions

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.extensions.impl.EChangeCompareUtilImpl

interface EChangeCompareUtil {
	static EChangeCompareUtil instance = EChangeCompareUtilImpl::init

	def boolean isEChangeEqual(EChange e1, EChange e2)

	def void addPair(Pair<String, String> pair)

	def void addIdPair(Pair<String, String> idair)

	def boolean containerIsRootAndMapped(String containerString, InternalEObject affectedContainer2)

	def String getComparableString(URI uri)

	def boolean isEObjectEqual(EObject eObject1, EObject eObject2)
}

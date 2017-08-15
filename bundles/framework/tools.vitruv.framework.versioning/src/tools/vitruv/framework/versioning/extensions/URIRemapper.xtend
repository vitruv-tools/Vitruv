package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.URIRemapperImpl
import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.echange.EChange

interface URIRemapper {
	URIRemapper instance = URIRemapperImpl::init

	def Consumer<EChange> createEChangeRemapFunction(String from, String to)

	def Consumer<EChange> createEChangeRemapFunction(VURI from, VURI to)

	def Consumer<EObject> createRemapFunction(String from, String to)

	def Consumer<EObject> createRemapFunction(VURI from, VURI to)

	def VURI createNewVURI(VURI oldVURI, Pair<String, String> stringWithNewString)

	def VURI getCorrespondentURI(VURI vuri)
}

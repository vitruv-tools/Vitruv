package tools.vitruv.framework.versioning.extensions.impl

import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.echange.EChange
import java.util.function.Consumer
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot

class URIRemapperImpl implements URIRemapper {
	static def URIRemapper init() {
		new URIRemapperImpl
	}

	private new() {
	}

	val remapURIString = [ String from, String to, EObject e |
		val internalEObject = e as InternalEObject
		val proxyString = internalEObject.eProxyURI.toString
		if (proxyString.contains(from)) {
			val newProxyString = proxyString.replace(from, to)
			val newUri = URI::createURI(newProxyString)
			internalEObject.eSetProxyURI(newUri)
		}
	]

	override createRemapFunction(VURI from, VURI to) {
		if (null === from || null === to)
			return []
		val fromVURIString = from.EMFUri.toString
		val toVURIString = to.EMFUri.toString
		return remapURIString.curry(fromVURIString).curry(toVURIString)
	}

	override createRemapFunction(String from, String to) {
		return remapURIString.curry(from).curry(to)
	}

	override createEChangeRemapFunction(String from, String to) {
		val remapMyUriFunction = createRemapFunction(from, to)
		return [EChange e|processEchange(e, remapMyUriFunction)]
	}

	override createEChangeRemapFunction(VURI from, VURI to) {
		val remapTheirUriFunction = createRemapFunction(from, to)
		return [EChange e|processEchange(e, remapTheirUriFunction)]
	}

	private static dispatch def processEchange(EChange e, Consumer<EObject> cb) {
	}

	private static dispatch def processEchange(ReplaceSingleValuedEAttribute<?, ?> e, Consumer<EObject> cb) {
		cb.accept(e.affectedEObject)
	}

	private static dispatch def processEchange(CreateAndInsertNonRoot<?, ?> e, Consumer<EObject> cb) {
		cb.accept(e.insertChange.affectedEObject)
	}
}

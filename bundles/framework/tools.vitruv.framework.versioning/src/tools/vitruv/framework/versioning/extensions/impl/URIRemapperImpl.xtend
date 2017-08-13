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
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import org.apache.log4j.Logger

class URIRemapperImpl implements URIRemapper {
	static extension Logger = Logger::getLogger(URIRemapperImpl)

	static def URIRemapper init() {
		new URIRemapperImpl
	}

	private static dispatch def processEchange(
		EChange e,
		Consumer<EObject> cb
	) {
		throw new UnsupportedOperationException('''EChange «e» with class «e.class.simpleName» was not handled yet''')
	}

	private static dispatch def processEchange(
		ReplaceSingleValuedEAttribute<?, ?> e,
		Consumer<EObject> cb
	) {
		cb.accept(e.affectedEObject)
	}

	private static dispatch def processEchange(
		CreateAndInsertRoot<?> e,
		Consumer<EObject> cb
	) {
		// PS Do nothing, but prevent UnsupportedOperationException to be thrown 
	}

	private static dispatch def processEchange(
		CreateAndInsertNonRoot<?, ?> e,
		Consumer<EObject> cb
	) {
		cb.accept(e.insertChange.affectedEObject)
	}

	private static def remapCreateAndInsertRootUri(
		CreateAndInsertRoot<?> e,
		String from,
		String to
	) {
		val oldUri = e.insertChange.uri
		if (oldUri.contains(from)) {
			val newUri = oldUri.replace(from, to)
			e.insertChange.uri = newUri
		}
	}

	private static def remapCreateAndInsertRootUri(
		CreateAndInsertRoot<?> e,
		VURI from,
		VURI to
	) {
		remapCreateAndInsertRootUri(e, from.EMFUri.toString, to.EMFUri.toString)
	}

	private new() {
	}

	public static val REMAP_URI_String = [ String from, String to, EObject e |
		val internalEObject = e as InternalEObject
		if (null === internalEObject) {
			error('''Null as parameter''')
			return;
		}
		val proxyString = internalEObject.eProxyURI?.toString
		if (null === proxyString) {
			error('''EObject «e» has no proxyURI''')
			return;
		}
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
		return createRemapFunction(fromVURIString, toVURIString)
	}

	override createRemapFunction(String from, String to) {
		return tools.vitruv.framework.versioning.extensions.impl.URIRemapperImpl.REMAP_URI_String.curry(from).curry(to)
	}

	override createEChangeRemapFunction(String from, String to) {
		val remapMyUriFunction = createRemapFunction(from, to)
		return [ EChange e |
			if (e instanceof CreateAndInsertRoot<?>)
				remapCreateAndInsertRootUri(e, from, to)
			else
				processEchange(e, remapMyUriFunction)
		]
	}

	override createEChangeRemapFunction(VURI from, VURI to) {
		val remapTheirUriFunction = createRemapFunction(from, to)
		return [ EChange e |
			if (e instanceof CreateAndInsertRoot<?>)
				remapCreateAndInsertRootUri(e, from, to)
			else
				processEchange(e, remapTheirUriFunction)
		]
	}

	override createNewVURI(VURI oldVURI, Pair<String, String> stringWithNewString) {
		val sourceVURIString = oldVURI.EMFUri.toString
		val newSourceVURIString = sourceVURIString.replace(stringWithNewString.key, stringWithNewString.value)
		val newSourceVURI = VURI::getInstance(URI::createURI(newSourceVURIString))
		return newSourceVURI
	}
}

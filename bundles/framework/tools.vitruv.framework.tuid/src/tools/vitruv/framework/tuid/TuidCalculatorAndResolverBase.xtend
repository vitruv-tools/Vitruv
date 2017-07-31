package tools.vitruv.framework.tuid

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.bridges.EcoreBridge
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import java.util.List
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.URIConverter

/**
 * Base class for Tuid calculators and resolvers. It handles the default parts of the Tuid like
 * generator identifier and URI inclusion for Tuid calculation and resolution.
 * @author Stephan Seifermann
 */
abstract class TuidCalculatorAndResolverBase implements TuidCalculatorAndResolver {
	static extension Logger = Logger::getLogger(TuidCalculatorAndResolverBase.simpleName)
	val String tuidPrefix
	// TODO MK check whether cachedResourcelessRoots and cachedRoot2KeyMap can be replaced with a
	// BiMap
	val ClaimableMap<Integer, EObject> cachedResourcelessRoots
	int nextCacheKey = 0
	// TODO MK (cache): if necessary remove objects that were created and cached but never stored,
	// as this is the only way to have something in the cache that should no longer be there
	val Map<EObject, Integer> cachedRoot2KeyMap

	new(String tuidPrefix) {
		this.tuidPrefix = tuidPrefix
		this.cachedResourcelessRoots = new ClaimableHashMap<Integer, EObject>()
		this.cachedRoot2KeyMap = new HashMap<EObject, Integer>()
	}

	override isValidTuid(String tuid) {
		return tuid.startsWith(getTuidPrefixAndSeparator())
	}

	/**
	 * Return the most distant parent eObject of the tree that contains the given eObject if this
	 * tree exists and otherwise the given eObject itself (beeing the root of a containment tree of
	 * depth 0).
	 * @param eObject
	 * @return the root
	 */
	def static EObject getMostDistantParentOfSameMetamodel(EObject eObject) {
		var EObject root = EcoreBridge::getRootEObject(eObject)
		if (!(root instanceof ChangeDescription)) {
			return root
		} else {
			return getMostDistantParentOfSameMetamodelRecirsivly(eObject)
		}
	}

	def static EObject getMostDistantParentOfSameMetamodelRecirsivly(EObject eObject) {
		var EObject parent = eObject.eContainer
		if (null === parent || (parent instanceof ChangeDescription)) {
			return eObject
		}
		return getMostDistantParentOfSameMetamodelRecirsivly(parent)
	}

	override calculateTuidsFromEObjects(List<EObject> eObjects) {
		return eObjects.map[calculateTuidFromEObject(it)]
	}

	override calculateTuidFromEObject(EObject eObject) {
		var EObject root = getMostDistantParentOfSameMetamodel(eObject)
		var String tuidPrefix = null
		if (root.eResource === null) {
			tuidPrefix = getTuidPrefixAndSeparator() + getVURIReplacementForCachedRoot(root)
			if (!isCached(root)) {
				// the root has no resource and therefore it has to be cached for correct Tuid
				// resolution and calculation
				addRootToCache(
					root)

				trace('''The given EObject «eObject» has no resource attached. Added it to the Tuid cache using the prefix '«»«tuidPrefix»'.''')
			}

		} else {
			if (isCached(root)) {
				// the root has a resource but was cached before, so it should be removed from the
				// cache
				removeRootFromCache(root)
			}
			tuidPrefix = getTuidPrefixAndSeparator() + VURI::getInstance(eObject.eResource)
		}
		var EObject virtualRootEObject = root.eContainer
		return calculateTuidFromEObject(eObject, virtualRootEObject, tuidPrefix)
	}

	private def void addRootToCache(EObject root) {
		var int key = getCacheKey(root)
		this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root)
	}

	private def boolean isCached(EObject root) {
		var int key = getCacheKey(root)
		var boolean cached = this.cachedResourcelessRoots.containsKey(key)
		debug('''The key '«»«key»' is currently «(if (cached) "" else "not")» in the tuid cache.''')
		return cached
	}

	override removeRootFromCache(EObject root) {
		var int key = getCacheKey(root)
		removeCacheEntryForKey(key)
	}

	private def void removeCacheEntryForKey(int key) {
		var EObject value = this.cachedResourcelessRoots.remove(key)
		this.cachedRoot2KeyMap.remove(value)
		debug('''Removed the value '«»«value»'for the key '«»«key»' from the tuid cache.''')
	}

	override removeIfRootAndCached(String tuid) {
		var String[] segmentsAfterMarker = getSegmentsAfterCachedTuidMarker(tuid)
		var boolean isTuidOfRoot = segmentsAfterMarker !== null && segmentsAfterMarker.length === 1
		if (isTuidOfRoot) {
			var Integer key = getCacheKeyForTuidString(tuid)
			if (key !== null) {
				removeCacheEntryForKey(key)
			}

		}

	}

	private def String getVURIReplacementForCachedRoot(EObject root) {
		return VitruviusConstants::cachedTuidMarker + getCacheKey(root)
	}

	private def Integer claimCacheKey(String tuid) {
		var Integer key = getCacheKeyForTuidString(tuid)
		if (key === null) {
			warn(''' TUID «tuid» is not in the cache''')
			return null
		} else {
			return key
		}
	}

	private def Integer getCacheKeyForTuidString(String tuid) {
		var String[] segmentsAfterMarker = getSegmentsAfterCachedTuidMarker(tuid)
		if (segmentsAfterMarker !== null && segmentsAfterMarker.length > 0) {
			var String keyString = segmentsAfterMarker.get(0)
			return getCacheKeyForKeySegment(keyString)
		} else {
			return null
		}
	}

	private def int getCacheKeyForKeySegment(String keySegment) {
		return Integer::parseInt(keySegment)
	}

	private def Integer getCacheKey(EObject root) {
		var Integer key = this.cachedRoot2KeyMap.get(root)
		if (key === null) {
			key = getNextCacheKey()
			this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root)
			this.cachedRoot2KeyMap.put(root, key)
		}
		return key
	}

	private def int getNextCacheKey() {
		return this.nextCacheKey++
	}

	private def String[] getSegmentsAfterCachedTuidMarker(String tuid) {
		var String cachedTuidMarker = VitruviusConstants::cachedTuidMarker
		var String tuidSuffix = getTuidWithoutDefaultPrefix(tuid)
		if (tuidSuffix.startsWith(cachedTuidMarker)) {
			var int markerEndIndex = cachedTuidMarker.length
			var String suffixAfterMarker = tuidSuffix.substring(markerEndIndex)
			return getSegments(suffixAfterMarker)
		} else {
			return null
		}
	}

	def String getTuidPrefixAndSeparator() {
		getTuidPrefix() + VitruviusConstants::tuidSegmentSeperator
	}

	override getModelVURIContainingIdentifiedEObject(String extTuid) {
		var String tuidSuffix = getTuidWithoutDefaultPrefix(extTuid)
		var String[] segments = getSegments(tuidSuffix)
		var String firstSegmentOfSuffix = segments.get(0)
		if (isCached(firstSegmentOfSuffix))
			return null
		else {
			return firstSegmentOfSuffix
		}
	}

	private def String[] getSegments(String tuid) {
		return tuid.split(VitruviusConstants.tuidSegmentSeperator)
	}

	private def boolean isCached(String firstSegmentOfSuffix) {
		return firstSegmentOfSuffix.startsWith(VitruviusConstants.cachedTuidMarker)
	}

	override resolveEObjectFromRootAndFullTuid(EObject root_finalParam_, String tuid) {
		var root = root_finalParam_
		if (root === null) {
			root = claimRootFromCache(tuid)
			if (root === null) {
				warn('''No EObject found for Tuid: «tuid» in the cache''')
				return null
			}
		}
		var String identifier = getTuidWithoutRootObjectPrefix(root, tuid)
		if (identifier === null)
			return null
		var String[] segments = getSegments(identifier)
		if (identifier.length === 0)
			segments = newArrayOfSize(0)
		var EObject foundElement = getIdentifiedEObjectWithinRootEObjectInternal(root, segments)
		if (foundElement !== null)
			return foundElement
		warn('''No EObject found for Tuid: «tuid» in root object: «root»''')
		return null
	}

	private def EObject claimRootFromCache(String tuid) {
		var key = claimCacheKey(tuid)
		if(key === null) return null
		return this.cachedResourcelessRoots.claimValueForKey(key)
	}

	/**
	 * Removes the root object prefix from the given Tuid and returns the result.
	 * @param rootThe root object.
	 * @param extTuidThe Tuid to process.
	 * @return The Tuid without the root object prefix.
	 */
	private def String getTuidWithoutRootObjectPrefix(EObject root, String extTuid) {
		var String rootTuid = calculateTuidFromEObject(root)
		var String normalizedExtTuid = extTuid
		if (root.eResource !== null) {
			val uriConverter = root.eResource.resourceSet.URIConverter
			rootTuid = rootTuid.getUriNormalizedTuid(uriConverter)
			normalizedExtTuid = extTuid.getUriNormalizedTuid(uriConverter)
		}
		if (!normalizedExtTuid.startsWith(rootTuid)) {
			error('''Tuid «normalizedExtTuid» is not in EObject «root»''')
			return null
		}
		var String identifyingTuidPart = normalizedExtTuid.substring(rootTuid.length)
		if (identifyingTuidPart.startsWith(VitruviusConstants.tuidSegmentSeperator))
			identifyingTuidPart = identifyingTuidPart.substring(VitruviusConstants.tuidSegmentSeperator.length)
		return identifyingTuidPart
	}

	/**
	 * Normalizes the URI segment of the given {@link Tuid}. It converts pathmap URIs into ordinary platform URIs::
	 * It also converts the sometimes occurring plugin references via a resource reference (platform:/resource/platform:/resource)
	 * into an ordinary plugin reference.
	 * @param tuid the original {@link Tuid}
	 * @param uriConverter the {@link URIConverter} to normalize the {@link URI} with.
	 * @return The normalized and simplified {@link URI}.
	 */
	private static def String getUriNormalizedTuid(String tuid, URIConverter uriConverter) {
		val String[] tuidSegments = tuid.split(VitruviusConstants.tuidSegmentSeperator)
		if (tuidSegments.length < 2)
			throw new IllegalStateException("Tuid too short")
		val uri = uriConverter.normalize(
			URI.createURI(tuidSegments.get(1).replace("platform:/resource/platform:/plugin", "platform:/plugin")))
		val newTuid = tuid.replace(tuidSegments.get(1), uri.toString)
		return newTuid
	}

	/**
	 * Finds the object described by the given IDs in the given root object.
	 * @param rootThe root object.
	 * @param idsThe IDs of the object to find.
	 * @return The found object or null if no such object exists.
	 */
	def protected abstract EObject getIdentifiedEObjectWithinRootEObjectInternal(EObject root, String[] ids)

	/**
	 * @param tuidThe Tuid::
	 * @return The given Tuid without the default part.
	 */
	private def String getTuidWithoutDefaultPrefix(String tuid) {
		if (!isValidTuid(tuid))
			throw new IllegalArgumentException('''Tuid: «tuid» not generated by class «getTuidPrefix()»''')
		return tuid.substring(getTuidPrefixAndSeparator().length)
	}

	private def String getTuidPrefix() {
		return this.tuidPrefix
	}

}

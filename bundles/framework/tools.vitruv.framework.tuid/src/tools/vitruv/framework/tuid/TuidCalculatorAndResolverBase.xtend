package tools.vitruv.framework.tuid

import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.URIConverter
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.bridges.EcoreBridge
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import tools.vitruv.framework.util.datatypes.VURI
import org.apache.log4j.Level

/**
 * Base class for Tuid calculators and resolvers. It handles the default parts of the Tuid like
 * generator identifier and URI inclusion for Tuid calculation and resolution.
 * @author Stephan Seifermann
 */
abstract class TuidCalculatorAndResolverBase implements TuidCalculatorAndResolver {
	static extension Logger = Logger::getLogger(TuidCalculatorAndResolverBase.simpleName)
	@Accessors(PRIVATE_GETTER)
	val String tuidPrefix
	// TODO MK check whether cachedResourcelessRoots and cachedRoot2KeyMap can be replaced with a
	// BiMap
	val ClaimableMap<Integer, EObject> cachedResourcelessRoots
	int nextCacheKey = 0
	// TODO MK (cache): if necessary remove objects that were created and cached but never stored,
	// as this is the only way to have something in the cache that should no longer be there
	val Map<EObject, Integer> cachedRoot2KeyMap

	/**
	 * Return the most distant parent eObject of the tree that contains the given eObject if this
	 * tree exists and otherwise the given eObject itself (beeing the root of a containment tree of
	 * depth 0).
	 * @param eObject
	 * @return the root
	 */
	def static EObject getMostDistantParentOfSameMetamodel(EObject eObject) {
		val root = EcoreBridge::getRootEObject(eObject)
		if (!(root instanceof ChangeDescription))
			return root
		return getMostDistantParentOfSameMetamodelRecirsivly(eObject)
	}

	def static EObject getMostDistantParentOfSameMetamodelRecirsivly(EObject eObject) {
		val parent = eObject.eContainer
		if (null === parent || (parent instanceof ChangeDescription))
			return eObject
		return getMostDistantParentOfSameMetamodelRecirsivly(parent)
	}

	/**
	 * Normalizes the URI segment of the given {@link Tuid}. It converts pathmap URIs into ordinary platform URIs::
	 * It also converts the sometimes occurring plugin references via a resource reference (platform:/resource/platform:/resource)
	 * into an ordinary plugin reference.
	 * @param tuid the original {@link Tuid}
	 * @param uriConverter the {@link URIConverter} to normalize the {@link URI} with.
	 * @return The normalized and simplified {@link URI}.
	 */
	private static def String getUriNormalizedTuid(
		String tuid,
		URIConverter uriConverter
	) {
		level = Level::DEBUG
		val tuidSegments = tuid.split(VitruviusConstants.tuidSegmentSeperator)
		if (tuidSegments.length < 2)
			throw new IllegalStateException("Tuid too short")
		val uri = uriConverter.normalize(
			URI::createURI(tuidSegments.get(1).replace("platform:/resource/platform:/plugin", "platform:/plugin")))
		val newTuid = tuid.replace(tuidSegments.get(1), uri.toString)
		return newTuid
	}

	new(String tuidPrefix) {
		this.tuidPrefix = tuidPrefix
		cachedResourcelessRoots = new ClaimableHashMap<Integer, EObject>
		cachedRoot2KeyMap = newHashMap
	}

	override isValidTuid(String tuid) {
		tuid.startsWith(tuidPrefixAndSeparator)
	}

	override calculateTuidsFromEObjects(List<EObject> eObjects) {
		eObjects.map[calculateTuidFromEObject]
	}

	override calculateTuidFromEObject(EObject eObject) {
		val root = getMostDistantParentOfSameMetamodel(eObject)
		var String tuidPrefix = null
		if (root.eResource === null) {
			tuidPrefix = tuidPrefixAndSeparator + root.VURIReplacementForCachedRoot
			if (!isCached(root)) {
				// the root has no resource and therefore it has to be cached for correct Tuid
				// resolution and calculation
				root.
					addRootToCache
				trace('''
					The given EObject «eObject» has no resource attached. Added it to the Tuid cache using the prefix '«»«tuidPrefix»'.
				''')
			}

		} else {
			if (isCached(root)) {
				// the root has a resource but was cached before, so it should be removed from the
				// cache
				removeRootFromCache(root)
			}
			tuidPrefix = getTuidPrefixAndSeparator() + VURI::getInstance(eObject.eResource)
		}
		val EObject virtualRootEObject = root.eContainer
		return calculateTuidFromEObject(eObject, virtualRootEObject, tuidPrefix)
	}

	private def boolean isCached(EObject root) {
		val int key = getCacheKey(root)
		val boolean cached = cachedResourcelessRoots.containsKey(key)
		debug('''The key '«»«key»' is currently «(if (cached) "" else "not")» in the tuid cache.''')
		return cached
	}

	override removeRootFromCache(EObject root) {
		val int key = getCacheKey(root)
		removeCacheEntryForKey(key)
	}

	override removeIfRootAndCached(String tuid) {
		val segmentsAfterMarker = getSegmentsAfterCachedTuidMarker(tuid)
		val isTuidOfRoot = segmentsAfterMarker !== null && segmentsAfterMarker.length === 1
		if (isTuidOfRoot) {
			val key = tuid.cacheKeyForTuidString
			if (key !== null)
				removeCacheEntryForKey(key)
		}
	}

	def String getTuidPrefixAndSeparator() {
		getTuidPrefix() + VitruviusConstants::tuidSegmentSeperator
	}

	override getModelVURIContainingIdentifiedEObject(String extTuid) {
		val String tuidSuffix = getTuidWithoutDefaultPrefix(extTuid)
		val String[] segments = getSegments(tuidSuffix)
		val String firstSegmentOfSuffix = segments.get(0)
		if (isCached(firstSegmentOfSuffix))
			return null
		else {
			return firstSegmentOfSuffix
		}
	}

	override resolveEObjectFromRootAndFullTuid(
		EObject root_finalParam_,
		String tuid
	) {
		var root = root_finalParam_
		if (root === null) {
			root = claimRootFromCache(tuid)
			if (root === null) {
				warn('''No EObject found for Tuid: «tuid» in the cache''')
				return null
			}
		}
		val String identifier = getTuidWithoutRootObjectPrefix(root, tuid)
		if (identifier === null)
			return null
		var String[] segments = getSegments(identifier)
		if (identifier.length === 0)
			segments = newArrayOfSize(0)
		val EObject foundElement = getIdentifiedEObjectWithinRootEObjectInternal(root, segments)
		if (foundElement !== null)
			return foundElement
		warn('''No EObject found for Tuid: «tuid» in root object: «root»''')
		return null
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
			throw new IllegalArgumentException('''Tuid: «tuid» not generated by class «getTuidPrefix»''')
		return tuid.substring(getTuidPrefixAndSeparator().length)
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

	private def EObject claimRootFromCache(String tuid) {
		val key = claimCacheKey(tuid)
		if(key === null) return null
		return cachedResourcelessRoots.claimValueForKey(key)
	}

	private def String[] getSegments(String tuid) {
		tuid.split(VitruviusConstants.tuidSegmentSeperator)
	}

	private def boolean isCached(String firstSegmentOfSuffix) {
		firstSegmentOfSuffix.startsWith(VitruviusConstants.cachedTuidMarker)
	}

	private def String getVURIReplacementForCachedRoot(EObject root) {
		VitruviusConstants::cachedTuidMarker + getCacheKey(root)
	}

	private def Integer claimCacheKey(String tuid) {
		val Integer key = getCacheKeyForTuidString(tuid)
		if (key !== null)
			return key
		warn(''' TUID «tuid» is not in the cache''')
		return null
	}

	private def Integer getCacheKeyForTuidString(String tuid) {
		val String[] segmentsAfterMarker = getSegmentsAfterCachedTuidMarker(tuid)
		if (segmentsAfterMarker !== null && segmentsAfterMarker.length > 0) {
			val String keyString = segmentsAfterMarker.get(0)
			return getCacheKeyForKeySegment(keyString)
		} else {
			return null
		}
	}

	private def int getCacheKeyForKeySegment(String keySegment) {
		return Integer::parseInt(keySegment)
	}

	private def Integer getCacheKey(EObject root) {
		var Integer key = cachedRoot2KeyMap.get(root)
		if (key === null) {
			key = getNextCacheKey()
			cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root)
			cachedRoot2KeyMap.put(root, key)
		}
		return key
	}

	private def int getNextCacheKey() {
		nextCacheKey++
	}

	private def void addRootToCache(EObject root) {
		val int key = getCacheKey(root)
		cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root)
	}

	private def String[] getSegmentsAfterCachedTuidMarker(String tuid) {
		val cachedTuidMarker = VitruviusConstants::cachedTuidMarker
		val tuidSuffix = getTuidWithoutDefaultPrefix(tuid)
		if (!tuidSuffix.startsWith(cachedTuidMarker))
			return null
		val markerEndIndex = cachedTuidMarker.length
		val suffixAfterMarker = tuidSuffix.substring(markerEndIndex)
		return getSegments(suffixAfterMarker)
	}

	private def void removeCacheEntryForKey(int key) {
		val EObject value = cachedResourcelessRoots.remove(key)
		cachedRoot2KeyMap.remove(value)
		debug('''Removed the value '«»«value»'for the key '«»«key»' from the tuid cache.''')
	}
}

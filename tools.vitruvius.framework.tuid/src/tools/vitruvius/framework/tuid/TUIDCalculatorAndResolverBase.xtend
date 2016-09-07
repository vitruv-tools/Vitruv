package tools.vitruvius.framework.tuid

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruvius.framework.util.VitruviusConstants
import tools.vitruvius.framework.util.bridges.EcoreBridge
import tools.vitruvius.framework.util.datatypes.ClaimableHashMap
import tools.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.List
import tools.vitruvius.framework.util.datatypes.VURI

/** 
 * Base class for TUID calculators and resolvers. It handles the default parts of the TUID like
 * generator identifier and URI inclusion for TUID calculation and resolution.
 * @author Stephan Seifermann
 */
abstract class TUIDCalculatorAndResolverBase implements TUIDCalculatorAndResolver {
	static final Logger LOGGER = Logger.getLogger(TUIDCalculatorAndResolverBase.getSimpleName())
	final String tuidPrefix
	// TODO MK check whether cachedResourcelessRoots and cachedRoot2KeyMap can be replaced with a
	// BiMap
	final ClaimableMap<Integer, EObject> cachedResourcelessRoots
	int nextCacheKey = 0
	// TODO MK (cache): if necessary remove objects that were created and cached but never stored,
	// as this is the only way to have something in the cache that should no longer be there
	final Map<EObject, Integer> cachedRoot2KeyMap 

	new(String tuidPrefix) {
		this.tuidPrefix = tuidPrefix
		this.cachedResourcelessRoots = new ClaimableHashMap<Integer, EObject>()
		this.cachedRoot2KeyMap = new HashMap<EObject, Integer>()
	}

	override boolean isValidTUID(String tuid) {
		return tuid.startsWith(getTUIDPrefixAndSeparator())
	}

	/** 
	 * Return the most distant parent eObject of the tree that contains the given eObject if this
	 * tree exists and otherwise the given eObject itself (beeing the root of a containment tree of
	 * depth 0).
	 * @param eObject
	 * @return the root
	 */
	def static EObject getMostDistantParentOfSameMetamodel(EObject eObject) {
		var EObject root = EcoreBridge.getRootEObject(eObject)
		if (!(root instanceof ChangeDescription)) {
			return root
		} else {
			return getMostDistantParentOfSameMetamodelRecirsivly(eObject)
		}
	}

	def private static EObject getMostDistantParentOfSameMetamodelRecirsivly(EObject eObject) {
		var EObject parent = eObject.eContainer()
		if (null === parent || (parent instanceof ChangeDescription)) {
			return eObject
		}
		return getMostDistantParentOfSameMetamodelRecirsivly(parent)
	}
	
	
    override List<String> calculateTUIDsFromEObjects(List<EObject> eObjects) {
        return eObjects.map[calculateTUIDFromEObject(it)]
    }

	override String calculateTUIDFromEObject(EObject eObject) {
		var EObject root = getMostDistantParentOfSameMetamodel(eObject)
		var String tuidPrefix = null
		if (root.eResource() === null) {
			tuidPrefix = getTUIDPrefixAndSeparator() + getVURIReplacementForCachedRoot(root)
			if (!isCached(root)) {
				// the root has no resource and therefore it has to be cached for correct TUID
				// resolution and calculation
				addRootToCache(root)
				LOGGER.
					trace('''The given EObject «eObject» has no resource attached. Added it to the TUID cache using the prefix '«»«tuidPrefix»'.''')
			}

		} else {
			if (isCached(root)) {
				// the root has a resource but was cached before, so it should be removed from the
				// cache
				removeRootFromCache(root)
			}
			tuidPrefix = getTUIDPrefixAndSeparator() + VURI.getInstance(eObject.eResource())
		}
		var EObject virtualRootEObject = root.eContainer()
		return calculateTUIDFromEObject(eObject, virtualRootEObject, tuidPrefix)
	}

	def private void addRootToCache(EObject root) {
		var int key = getCacheKey(root)
		this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root)
	}

	def private boolean isCached(EObject root) {
		var int key = getCacheKey(root)
		var boolean cached = this.cachedResourcelessRoots.containsKey(key)
		LOGGER.debug('''The key '«»«key»' is currently «(if (cached) "" else "not")» in the tuid cache.''')
		return cached
	}

	override void removeRootFromCache(EObject root) {
		var int key = getCacheKey(root)
		removeCacheEntryForKey(key)
	}

	def private void removeCacheEntryForKey(int key) {
		var EObject value = this.cachedResourcelessRoots.remove(key)
		this.cachedRoot2KeyMap.remove(value)
		LOGGER.debug('''Removed the value '«»«value»'for the key '«»«key»' from the tuid cache.''')
	}

	override void removeIfRootAndCached(String tuid) {
		var String[] segmentsAfterMarker = getSegmentsAfterCachedTUIDMarker(tuid)
		var boolean isTUIDOfRoot = segmentsAfterMarker !== null && segmentsAfterMarker.length === 1
		if (isTUIDOfRoot) {
			var Integer key = getCacheKeyForTUIDString(tuid)
			if (key !== null) {
				removeCacheEntryForKey(key)
			}

		}

	}

	def private String getVURIReplacementForCachedRoot(EObject root) {
		return VitruviusConstants.getCachedTUIDMarker() + getCacheKey(root)
	}

	def private Integer claimCacheKey(String tuid) {
		var Integer key = getCacheKeyForTUIDString(tuid)
		if (key === null) {
			throw new IllegalArgumentException('''Cannot get the cache key for the tuid '«»«tuid»' because it is not of the form '{marker}{key}...'!''')
		} else {
			return key
		}
	}

	def private Integer getCacheKeyForTUIDString(String tuid) {
		var String[] segmentsAfterMarker = getSegmentsAfterCachedTUIDMarker(tuid)
		if (segmentsAfterMarker !== null && segmentsAfterMarker.length > 0) {
			var String keyString = segmentsAfterMarker.get(0)
			return getCacheKeyForKeySegment(keyString)
		} else {
			return null
		}
	}

	def private int getCacheKeyForKeySegment(String keySegment) {
		return Integer.parseInt(keySegment)
	}

	def private Integer getCacheKey(EObject root) {
		var Integer key = this.cachedRoot2KeyMap.get(root)
		if (key === null) {
			key = getNextCacheKey()
			this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root)
			this.cachedRoot2KeyMap.put(root, key)
		}
		return key
	}

	def private int getNextCacheKey() {
		return this.nextCacheKey++
	}

	def private String[] getSegmentsAfterCachedTUIDMarker(String tuid) {
		var String cachedTUIDMarker = VitruviusConstants.getCachedTUIDMarker()
		var String tuidSuffix = getTUIDWithoutDefaultPrefix(tuid)
		if (tuidSuffix.startsWith(cachedTUIDMarker)) {
			var int markerEndIndex = cachedTUIDMarker.length()
			var String suffixAfterMarker = tuidSuffix.substring(markerEndIndex)
			return getSegments(suffixAfterMarker)
		} else {
			return null
		}
	}

	def String getTUIDPrefixAndSeparator() {
		return getTUIDPrefix() + VitruviusConstants.getTUIDSegmentSeperator()
	}

	override String getModelVURIContainingIdentifiedEObject(String extTuid) {
		var String tuidSuffix = getTUIDWithoutDefaultPrefix(extTuid)
		var String[] segments = getSegments(tuidSuffix)
		var String firstSegmentOfSuffix = segments.get(0)
		if (isCached(firstSegmentOfSuffix)) {
			return null
		} else {
			return firstSegmentOfSuffix
		}
	}

	def private String[] getSegments(String tuid) {
		return tuid.split(VitruviusConstants.getTUIDSegmentSeperator())
	}

	def private boolean isCached(String firstSegmentOfSuffix) {
		return firstSegmentOfSuffix.startsWith(VitruviusConstants.getCachedTUIDMarker())
	}

	override EObject resolveEObjectFromRootAndFullTUID(EObject root_finalParam_, String tuid) {
		var root = root_finalParam_
		if (root === null) {
			root = claimRootFromCache(tuid)
		}
		var String identifier = getTUIDWithoutRootObjectPrefix(root, tuid)
		if (identifier === null) {
			return null
		}
		var String[] segments = getSegments(identifier)
		if (identifier.length() === 0) {
			segments = newArrayOfSize(0)
		}
		var EObject foundElement = getIdentifiedEObjectWithinRootEObjectInternal(root, segments)
		if (foundElement !== null) {
			return foundElement
		}
		LOGGER.warn('''No EObject found for TUID: «tuid» in root object: «root»''')
		return null
	}

	def private EObject claimRootFromCache(String tuid) {
		var int key = claimCacheKey(tuid)
		return this.cachedResourcelessRoots.claimValueForKey(key)
	}

	/** 
	 * Removes the root object prefix from the given TUID and returns the result.
	 * @param rootThe root object.
	 * @param extTuidThe TUID to process.
	 * @return The TUID without the root object prefix.
	 */
	def private String getTUIDWithoutRootObjectPrefix(EObject root, String extTuid) {
		var String rootTUID = calculateTUIDFromEObject(root)
		if (!extTuid.startsWith(rootTUID)) {
			LOGGER.warn('''TUID «extTuid» is not in EObject «root»''')
			return null
		}
		var String identifyingTUIDPart = extTuid.substring(rootTUID.length())
		if (identifyingTUIDPart.startsWith(VitruviusConstants.getTUIDSegmentSeperator())) {
			identifyingTUIDPart = identifyingTUIDPart.substring(VitruviusConstants.getTUIDSegmentSeperator().length())
		}
		return identifyingTUIDPart
	}

	/** 
	 * Finds the object described by the given IDs in the given root object.
	 * @param rootThe root object.
	 * @param idsThe IDs of the object to find.
	 * @return The found object or null if no such object exists.
	 */
	def protected abstract EObject getIdentifiedEObjectWithinRootEObjectInternal(EObject root, String[] ids)

	/** 
	 * @param tuidThe TUID.
	 * @return The given TUID without the default part.
	 */
	def private String getTUIDWithoutDefaultPrefix(String tuid) {
		if (!isValidTUID(tuid)) {
			throw new IllegalArgumentException('''TUID: «tuid» not generated by class «getTUIDPrefix()»''')
		}
		return tuid.substring(getTUIDPrefixAndSeparator().length())
	}

	def private String getTUIDPrefix() {
		return this.tuidPrefix
	}

}

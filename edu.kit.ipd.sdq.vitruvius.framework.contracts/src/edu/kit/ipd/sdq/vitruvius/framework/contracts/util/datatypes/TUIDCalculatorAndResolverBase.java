// package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;
//
// import java.util.HashMap;
// import java.util.Map;
//
// import org.apache.log4j.Logger;
// import org.eclipse.emf.ecore.EObject;
// import org.eclipse.emf.ecore.change.ChangeDescription;
//
// import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
// import
// edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUIDCalculatorAndResolver;
// import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
// import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreBridge;
// import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
// import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
//
/// **
// * Base class for TUID calculators and resolvers. It handles the default parts of the TUID like
// * generator identifier and URI inclusion for TUID calculation and resolution.
// *
// * @author Stephan Seifermann
// *
// */
// public abstract class TUIDCalculatorAndResolverBase implements TUIDCalculatorAndResolver {
//
// private static final Logger LOGGER =
// Logger.getLogger(TUIDCalculatorAndResolverBase.class.getSimpleName());
//
// private final String tuidPrefix;
//
// private final ClaimableMap<Integer, EObject> cachedResourcelessRoots;
// private int nextCacheKey = 0;
// // TODO MK (cache): if necessary remove objects that were created and cached but never stored,
// // as this is the only way to have something in the cache that should no longer be there
// private final Map<EObject, Integer> cachedRoot2KeyMap;
//
// public TUIDCalculatorAndResolverBase(final String tuidPrefix) {
// this.tuidPrefix = tuidPrefix;
// this.cachedResourcelessRoots = new ClaimableHashMap<Integer, EObject>();
// this.cachedRoot2KeyMap = new HashMap<EObject, Integer>();
// }
//
// @Override
// public boolean isValidTUID(final String tuid) {
// return tuid.startsWith(getTUIDPrefixAndSeparator());
// }
//
// /**
// * Return the most distant parent eObject of the tree that contains the given eObject if this
// * tree exists and otherwise the given eObject itself (beeing the root of a containment tree of
// * depth 0).
// *
// * @param eObject
// * @return the root
// */
// public static EObject getMostDistantParentOfSameMetamodel(final EObject eObject) {
// EObject root = EcoreBridge.getRootEObject(eObject);
// if (!(root instanceof ChangeDescription)) {
// return root;
// } else {
// return getMostDistantParentOfSameMetamodelRecirsivly(eObject);
// }
// }
//
// private static EObject getMostDistantParentOfSameMetamodelRecirsivly(final EObject eObject) {
// EObject parent = eObject.eContainer();
// if (null == parent || (parent instanceof ChangeDescription)) {
// return eObject;
// }
// return getMostDistantParentOfSameMetamodelRecirsivly(parent);
// }
//
// @Override
// public String calculateTUIDFromEObject(final EObject eObject) {
// EObject root = getMostDistantParentOfSameMetamodel(eObject);
//
// String tuidPrefix = null;
// if (root.eResource() == null) {
// tuidPrefix = getTUIDPrefixAndSeparator() + getVURIReplacementForCachedRoot(root);
// if (!isCached(root)) {
// // the root has no resource and therefore it has to be cached for correct TUID
// // resolution and calculation
// addRootToCache(root);
// LOGGER.trace("The given EObject " + eObject
// + " has no resource attached. Added it to the TUID cache using the prefix '" + tuidPrefix
// + "'.");
// }
// } else {
// if (isCached(root)) {
// // the root has a resource but was cached before, so it should be removed from the
// // cache
// removeRootFromCache(root);
// }
// tuidPrefix = getTUIDPrefixAndSeparator() + VURI.getInstance(eObject.eResource());
// }
// EObject virtualRootEObject = root.eContainer();
// return calculateTUIDFromEObject(eObject, virtualRootEObject, tuidPrefix);
// }
//
// private void addRootToCache(final EObject root) {
// int key = getCacheKey(root);
// this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root);
// }
//
// private boolean isCached(final EObject root) {
// int key = getCacheKey(root);
// boolean cached = this.cachedResourcelessRoots.containsKey(key);
// LOGGER.debug("The key '" + key + "' is currently " + (cached ? "" : "not") + " in the tuid
// cache.");
// return cached;
// }
//
// @Override
// public void removeRootFromCache(final EObject root) {
// int key = getCacheKey(root);
// removeCacheEntryForKey(key);
// }
//
// private void removeCacheEntryForKey(final int key) {
// EObject value = this.cachedResourcelessRoots.remove(key);
// LOGGER.debug("Removed the value '" + value + "'for the key '" + key + "' from the tuid cache.");
// }
//
// @Override
// public void removeIfRootAndCached(final String tuid) {
// String[] segmentsAfterMarker = getSegmentsAfterCachedTUIDMarker(tuid);
// boolean isTUIDOfRoot = segmentsAfterMarker != null && segmentsAfterMarker.length == 1;
// if (isTUIDOfRoot) {
// Integer key = getCacheKeyForTUIDString(tuid);
// if (key != null) {
// removeCacheEntryForKey(key);
// }
// }
// }
//
// private String getVURIReplacementForCachedRoot(final EObject root) {
// return VitruviusConstants.getCachedTUIDMarker() + getCacheKey(root);
// }
//
// private Integer claimCacheKey(final String tuid) {
// Integer key = getCacheKeyForTUIDString(tuid);
// if (key == null) {
// throw new IllegalArgumentException("Cannot get the cache key for the tuid '" + tuid
// + "' because it is not of the form '{marker}{key}...'!");
// } else {
// return key;
// }
// }
//
// private Integer getCacheKeyForTUIDString(final String tuid) {
// String[] segmentsAfterMarker = getSegmentsAfterCachedTUIDMarker(tuid);
// if (segmentsAfterMarker != null && segmentsAfterMarker.length > 0) {
// String keyString = segmentsAfterMarker[0];
// return getCacheKeyForKeySegment(keyString);
// } else {
// return null;
// }
// }
//
// private int getCacheKeyForKeySegment(final String keySegment) {
// return Integer.parseInt(keySegment);
// }
//
// private Integer getCacheKey(final EObject root) {
// Integer key = this.cachedRoot2KeyMap.get(root);
// if (key == null) {
// key = getNextCacheKey();
// this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root);
// this.cachedRoot2KeyMap.put(root, key);
// }
// return key;
// }
//
// private int getNextCacheKey() {
// return this.nextCacheKey++;
// }
//
// private String[] getSegmentsAfterCachedTUIDMarker(final String tuid) {
// String cachedTUIDMarker = VitruviusConstants.getCachedTUIDMarker();
// String tuidSuffix = getTUIDWithoutDefaultPrefix(tuid);
// if (tuidSuffix.startsWith(cachedTUIDMarker)) {
// int markerEndIndex = cachedTUIDMarker.length();
// String suffixAfterMarker = tuidSuffix.substring(markerEndIndex);
// return getSegments(suffixAfterMarker);
// } else {
// return null;
// }
// }
//
// public String getTUIDPrefixAndSeparator() {
// return getTUIDPrefix() + VitruviusConstants.getTUIDSegmentSeperator();
// }
//
// @Override
// public String getModelVURIContainingIdentifiedEObject(final String extTuid) {
// String tuidSuffix = getTUIDWithoutDefaultPrefix(extTuid);
// String[] segments = getSegments(tuidSuffix);
// String firstSegmentOfSuffix = segments[0];
// if (isCached(firstSegmentOfSuffix)) {
// return null;
// } else {
// return firstSegmentOfSuffix;
// }
// }
//
// private String[] getSegments(final String tuid) {
// return tuid.split(VitruviusConstants.getTUIDSegmentSeperator());
// }
//
// private boolean isCached(final String firstSegmentOfSuffix) {
// return firstSegmentOfSuffix.startsWith(VitruviusConstants.getCachedTUIDMarker());
// }
//
// @Override
// public EObject resolveEObjectFromRootAndFullTUID(EObject root, final String tuid) {
// if (root == null) {
// root = claimRootFromCache(tuid);
// }
// String identifier = getTUIDWithoutRootObjectPrefix(root, tuid);
// if (identifier == null) {
// return null;
// }
//
// String[] segments = getSegments(identifier);
// if (identifier.length() == 0) {
// segments = new String[0];
// }
//
// EObject foundElement = getIdentifiedEObjectWithinRootEObjectInternal(root, segments);
// if (foundElement != null) {
// return foundElement;
// }
//
// LOGGER.warn("No EObject found for TUID: " + tuid + " in root object: " + root);
// return null;
// }
//
// private EObject claimRootFromCache(final String tuid) {
// int key = claimCacheKey(tuid);
// return this.cachedResourcelessRoots.claimValueForKey(key);
// }
//
// /**
// * Removes the root object prefix from the given TUID and returns the result.
// *
// * @param root
// * The root object.
// * @param extTuid
// * The TUID to process.
// * @return The TUID without the root object prefix.
// */
// private String getTUIDWithoutRootObjectPrefix(final EObject root, final String extTuid) {
// String rootTUID = calculateTUIDFromEObject(root);
// if (!extTuid.startsWith(rootTUID)) {
// LOGGER.warn("TUID " + extTuid + " is not in EObject " + root);
// return null;
// }
//
// String identifyingTUIDPart = extTuid.substring(rootTUID.length());
// if (identifyingTUIDPart.startsWith(VitruviusConstants.getTUIDSegmentSeperator())) {
// identifyingTUIDPart =
// identifyingTUIDPart.substring(VitruviusConstants.getTUIDSegmentSeperator().length());
// }
// return identifyingTUIDPart;
// }
//
// /**
// * Finds the object described by the given IDs in the given root object.
// *
// * @param root
// * The root object.
// * @param ids
// * The IDs of the object to find.
// * @return The found object or null if no such object exists.
// */
// protected abstract EObject getIdentifiedEObjectWithinRootEObjectInternal(final EObject root,
// final String[] ids);
//
// /**
// * @param tuid
// * The TUID.
// * @return The given TUID without the default part.
// */
// private String getTUIDWithoutDefaultPrefix(final String tuid) {
// if (!isValidTUID(tuid)) {
// throw new IllegalArgumentException("TUID: " + tuid + " not generated by class " +
// getTUIDPrefix());
// }
// return tuid.substring(getTUIDPrefixAndSeparator().length());
// }
//
// private String getTUIDPrefix() {
// return this.tuidPrefix;
// }
//
// }

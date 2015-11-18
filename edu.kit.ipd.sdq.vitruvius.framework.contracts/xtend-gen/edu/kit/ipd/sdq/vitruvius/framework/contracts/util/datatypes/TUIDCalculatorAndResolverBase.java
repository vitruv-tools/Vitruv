package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Base class for TUID calculators and resolvers. It handles the default parts of the TUID like
 * generator identifier and URI inclusion for TUID calculation and resolution.
 * @author Stephan Seifermann
 */
@SuppressWarnings("all")
public abstract class TUIDCalculatorAndResolverBase implements TUIDCalculatorAndResolver {
  private final static Logger LOGGER = Logger.getLogger(TUIDCalculatorAndResolverBase.class.getSimpleName());
  
  private final String tuidPrefix;
  
  private final ClaimableMap<Integer, EObject> cachedResourcelessRoots;
  
  private int nextCacheKey = 0;
  
  private final Map<EObject, Integer> cachedRoot2KeyMap;
  
  public TUIDCalculatorAndResolverBase(final String tuidPrefix) {
    this.tuidPrefix = tuidPrefix;
    ClaimableHashMap<Integer, EObject> _claimableHashMap = new ClaimableHashMap<Integer, EObject>();
    this.cachedResourcelessRoots = _claimableHashMap;
    HashMap<EObject, Integer> _hashMap = new HashMap<EObject, Integer>();
    this.cachedRoot2KeyMap = _hashMap;
  }
  
  @Override
  public boolean isValidTUID(final String tuid) {
    String _tUIDPrefixAndSeparator = this.getTUIDPrefixAndSeparator();
    return tuid.startsWith(_tUIDPrefixAndSeparator);
  }
  
  /**
   * Return the most distant parent eObject of the tree that contains the given eObject if this
   * tree exists and otherwise the given eObject itself (beeing the root of a containment tree of
   * depth 0).
   * @param eObject
   * @return the root
   */
  public static EObject getMostDistantParentOfSameMetamodel(final EObject eObject) {
    EObject root = EcoreBridge.getRootEObject(eObject);
    if ((!(root instanceof ChangeDescription))) {
      return root;
    } else {
      return TUIDCalculatorAndResolverBase.getMostDistantParentOfSameMetamodelRecirsivly(eObject);
    }
  }
  
  private static EObject getMostDistantParentOfSameMetamodelRecirsivly(final EObject eObject) {
    EObject parent = eObject.eContainer();
    boolean _or = false;
    if ((null == parent)) {
      _or = true;
    } else {
      _or = (parent instanceof ChangeDescription);
    }
    if (_or) {
      return eObject;
    }
    return TUIDCalculatorAndResolverBase.getMostDistantParentOfSameMetamodelRecirsivly(parent);
  }
  
  @Override
  public List<String> calculateTUIDsFromEObjects(final List<EObject> eObjects) {
    final Function1<EObject, String> _function = (EObject it) -> {
      return this.calculateTUIDFromEObject(it);
    };
    return ListExtensions.<EObject, String>map(eObjects, _function);
  }
  
  @Override
  public String calculateTUIDFromEObject(final EObject eObject) {
    EObject root = TUIDCalculatorAndResolverBase.getMostDistantParentOfSameMetamodel(eObject);
    String tuidPrefix = null;
    Resource _eResource = root.eResource();
    boolean _tripleEquals = (_eResource == null);
    if (_tripleEquals) {
      String _tUIDPrefixAndSeparator = this.getTUIDPrefixAndSeparator();
      String _vURIReplacementForCachedRoot = this.getVURIReplacementForCachedRoot(root);
      String _plus = (_tUIDPrefixAndSeparator + _vURIReplacementForCachedRoot);
      tuidPrefix = _plus;
      boolean _isCached = this.isCached(root);
      boolean _not = (!_isCached);
      if (_not) {
        this.addRootToCache(root);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The given EObject ");
        _builder.append(eObject, "");
        _builder.append(" has no resource attached. Added it to the TUID cache using the prefix \'");
        _builder.append(tuidPrefix, "");
        _builder.append("\'.");
        TUIDCalculatorAndResolverBase.LOGGER.trace(_builder);
      }
    } else {
      boolean _isCached_1 = this.isCached(root);
      if (_isCached_1) {
        this.removeRootFromCache(root);
      }
      String _tUIDPrefixAndSeparator_1 = this.getTUIDPrefixAndSeparator();
      Resource _eResource_1 = eObject.eResource();
      VURI _instance = VURI.getInstance(_eResource_1);
      String _plus_1 = (_tUIDPrefixAndSeparator_1 + _instance);
      tuidPrefix = _plus_1;
    }
    EObject virtualRootEObject = root.eContainer();
    return this.calculateTUIDFromEObject(eObject, virtualRootEObject, tuidPrefix);
  }
  
  private void addRootToCache(final EObject root) {
    int key = (this.getCacheKey(root)).intValue();
    this.cachedResourcelessRoots.putClaimingNullOrSameMapped(Integer.valueOf(key), root);
  }
  
  private boolean isCached(final EObject root) {
    int key = (this.getCacheKey(root)).intValue();
    boolean cached = this.cachedResourcelessRoots.containsKey(Integer.valueOf(key));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The key \'");
    _builder.append(key, "");
    _builder.append("\' is currently ");
    String _xifexpression = null;
    if (cached) {
      _xifexpression = "";
    } else {
      _xifexpression = "not";
    }
    _builder.append(_xifexpression, "");
    _builder.append(" in the tuid cache.");
    TUIDCalculatorAndResolverBase.LOGGER.debug(_builder);
    return cached;
  }
  
  @Override
  public void removeRootFromCache(final EObject root) {
    int key = (this.getCacheKey(root)).intValue();
    this.removeCacheEntryForKey(key);
  }
  
  private void removeCacheEntryForKey(final int key) {
    EObject value = this.cachedResourcelessRoots.remove(Integer.valueOf(key));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Removed the value \'");
    _builder.append(value, "");
    _builder.append("\'for the key \'");
    _builder.append(key, "");
    _builder.append("\' from the tuid cache.");
    TUIDCalculatorAndResolverBase.LOGGER.debug(_builder);
  }
  
  @Override
  public void removeIfRootAndCached(final String tuid) {
    String[] segmentsAfterMarker = this.getSegmentsAfterCachedTUIDMarker(tuid);
    boolean _and = false;
    if (!(segmentsAfterMarker != null)) {
      _and = false;
    } else {
      int _length = segmentsAfterMarker.length;
      boolean _tripleEquals = (_length == 1);
      _and = _tripleEquals;
    }
    boolean isTUIDOfRoot = _and;
    if (isTUIDOfRoot) {
      Integer key = this.getCacheKeyForTUIDString(tuid);
      if ((key != null)) {
        this.removeCacheEntryForKey((key).intValue());
      }
    }
  }
  
  private String getVURIReplacementForCachedRoot(final EObject root) {
    String _cachedTUIDMarker = VitruviusConstants.getCachedTUIDMarker();
    Integer _cacheKey = this.getCacheKey(root);
    return (_cachedTUIDMarker + _cacheKey);
  }
  
  private Integer claimCacheKey(final String tuid) {
    Integer key = this.getCacheKeyForTUIDString(tuid);
    if ((key == null)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Cannot get the cache key for the tuid \'");
      _builder.append(tuid, "");
      _builder.append("\' because it is not of the form \'{marker}{key}...\'!");
      throw new IllegalArgumentException(_builder.toString());
    } else {
      return key;
    }
  }
  
  private Integer getCacheKeyForTUIDString(final String tuid) {
    String[] segmentsAfterMarker = this.getSegmentsAfterCachedTUIDMarker(tuid);
    boolean _and = false;
    if (!(segmentsAfterMarker != null)) {
      _and = false;
    } else {
      int _length = segmentsAfterMarker.length;
      boolean _greaterThan = (_length > 0);
      _and = _greaterThan;
    }
    if (_and) {
      String keyString = segmentsAfterMarker[0];
      return Integer.valueOf(this.getCacheKeyForKeySegment(keyString));
    } else {
      return null;
    }
  }
  
  private int getCacheKeyForKeySegment(final String keySegment) {
    return Integer.parseInt(keySegment);
  }
  
  private Integer getCacheKey(final EObject root) {
    Integer key = this.cachedRoot2KeyMap.get(root);
    if ((key == null)) {
      int _nextCacheKey = this.getNextCacheKey();
      key = Integer.valueOf(_nextCacheKey);
      this.cachedResourcelessRoots.putClaimingNullOrSameMapped(key, root);
      this.cachedRoot2KeyMap.put(root, key);
    }
    return key;
  }
  
  private int getNextCacheKey() {
    return this.nextCacheKey++;
  }
  
  private String[] getSegmentsAfterCachedTUIDMarker(final String tuid) {
    String cachedTUIDMarker = VitruviusConstants.getCachedTUIDMarker();
    String tuidSuffix = this.getTUIDWithoutDefaultPrefix(tuid);
    boolean _startsWith = tuidSuffix.startsWith(cachedTUIDMarker);
    if (_startsWith) {
      int markerEndIndex = cachedTUIDMarker.length();
      String suffixAfterMarker = tuidSuffix.substring(markerEndIndex);
      return this.getSegments(suffixAfterMarker);
    } else {
      return null;
    }
  }
  
  public String getTUIDPrefixAndSeparator() {
    String _tUIDPrefix = this.getTUIDPrefix();
    String _tUIDSegmentSeperator = VitruviusConstants.getTUIDSegmentSeperator();
    return (_tUIDPrefix + _tUIDSegmentSeperator);
  }
  
  @Override
  public String getModelVURIContainingIdentifiedEObject(final String extTuid) {
    String tuidSuffix = this.getTUIDWithoutDefaultPrefix(extTuid);
    String[] segments = this.getSegments(tuidSuffix);
    String firstSegmentOfSuffix = segments[0];
    boolean _isCached = this.isCached(firstSegmentOfSuffix);
    if (_isCached) {
      return null;
    } else {
      return firstSegmentOfSuffix;
    }
  }
  
  private String[] getSegments(final String tuid) {
    String _tUIDSegmentSeperator = VitruviusConstants.getTUIDSegmentSeperator();
    return tuid.split(_tUIDSegmentSeperator);
  }
  
  private boolean isCached(final String firstSegmentOfSuffix) {
    String _cachedTUIDMarker = VitruviusConstants.getCachedTUIDMarker();
    return firstSegmentOfSuffix.startsWith(_cachedTUIDMarker);
  }
  
  @Override
  public EObject resolveEObjectFromRootAndFullTUID(final EObject root_finalParam_, final String tuid) {
    EObject root = root_finalParam_;
    if ((root == null)) {
      EObject _claimRootFromCache = this.claimRootFromCache(tuid);
      root = _claimRootFromCache;
    }
    String identifier = this.getTUIDWithoutRootObjectPrefix(root, tuid);
    if ((identifier == null)) {
      return null;
    }
    String[] segments = this.getSegments(identifier);
    int _length = identifier.length();
    boolean _tripleEquals = (_length == 0);
    if (_tripleEquals) {
      String[] _newArrayOfSize = new String[0];
      segments = _newArrayOfSize;
    }
    EObject foundElement = this.getIdentifiedEObjectWithinRootEObjectInternal(root, segments);
    if ((foundElement != null)) {
      return foundElement;
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("No EObject found for TUID: ");
    _builder.append(tuid, "");
    _builder.append(" in root object: ");
    _builder.append(root, "");
    TUIDCalculatorAndResolverBase.LOGGER.warn(_builder);
    return null;
  }
  
  private EObject claimRootFromCache(final String tuid) {
    int key = (this.claimCacheKey(tuid)).intValue();
    return this.cachedResourcelessRoots.claimValueForKey(Integer.valueOf(key));
  }
  
  /**
   * Removes the root object prefix from the given TUID and returns the result.
   * @param rootThe root object.
   * @param extTuidThe TUID to process.
   * @return The TUID without the root object prefix.
   */
  private String getTUIDWithoutRootObjectPrefix(final EObject root, final String extTuid) {
    String rootTUID = this.calculateTUIDFromEObject(root);
    boolean _startsWith = extTuid.startsWith(rootTUID);
    boolean _not = (!_startsWith);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("TUID ");
      _builder.append(extTuid, "");
      _builder.append(" is not in EObject ");
      _builder.append(root, "");
      TUIDCalculatorAndResolverBase.LOGGER.warn(_builder);
      return null;
    }
    int _length = rootTUID.length();
    String identifyingTUIDPart = extTuid.substring(_length);
    String _tUIDSegmentSeperator = VitruviusConstants.getTUIDSegmentSeperator();
    boolean _startsWith_1 = identifyingTUIDPart.startsWith(_tUIDSegmentSeperator);
    if (_startsWith_1) {
      String _tUIDSegmentSeperator_1 = VitruviusConstants.getTUIDSegmentSeperator();
      int _length_1 = _tUIDSegmentSeperator_1.length();
      String _substring = identifyingTUIDPart.substring(_length_1);
      identifyingTUIDPart = _substring;
    }
    return identifyingTUIDPart;
  }
  
  /**
   * Finds the object described by the given IDs in the given root object.
   * @param rootThe root object.
   * @param idsThe IDs of the object to find.
   * @return The found object or null if no such object exists.
   */
  protected abstract EObject getIdentifiedEObjectWithinRootEObjectInternal(final EObject root, final String[] ids);
  
  /**
   * @param tuidThe TUID.
   * @return The given TUID without the default part.
   */
  private String getTUIDWithoutDefaultPrefix(final String tuid) {
    boolean _isValidTUID = this.isValidTUID(tuid);
    boolean _not = (!_isValidTUID);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("TUID: ");
      _builder.append(tuid, "");
      _builder.append(" not generated by class ");
      String _tUIDPrefix = this.getTUIDPrefix();
      _builder.append(_tUIDPrefix, "");
      throw new IllegalArgumentException(_builder.toString());
    }
    String _tUIDPrefixAndSeparator = this.getTUIDPrefixAndSeparator();
    int _length = _tUIDPrefixAndSeparator.length();
    return tuid.substring(_length);
  }
  
  private String getTUIDPrefix() {
    return this.tuidPrefix;
  }
}

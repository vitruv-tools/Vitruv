package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractURIHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.DefaultTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class Metamodel extends AbstractURIHaving {
  private String[] fileExtensions;
  
  private TUIDCalculatorAndResolver tuidCalculatorAndResolver;
  
  private Set<String> nsURIs;
  
  private Map<Object, Object> defaultLoadOptions;
  
  private Map<Object, Object> defaultSaveOptions;
  
  private static Set<String> getNsURISet(final String... nsURIs) {
    List<String> _asList = Arrays.<String>asList(nsURIs);
    return new HashSet<String>(_asList);
  }
  
  private static String getTUIDPrefix(final String... nsURIs) {
    Set<String> _nsURISet = Metamodel.getNsURISet(nsURIs);
    return Metamodel.getTUIDPrefix(_nsURISet);
  }
  
  private static String getTUIDPrefix(final Set<String> nsURIs) {
    boolean _and = false;
    if (!(nsURIs != null)) {
      _and = false;
    } else {
      int _size = nsURIs.size();
      boolean _greaterThan = (_size > 0);
      _and = _greaterThan;
    }
    if (_and) {
      Iterator<String> _iterator = nsURIs.iterator();
      return _iterator.next();
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Cannot get a TUID prefix from the set of namespace URIs \'");
      _builder.append(nsURIs, "");
      _builder.append("\'!");
      String _string = _builder.toString();
      throw new RuntimeException(_string);
    }
  }
  
  public Metamodel(final String nsURI, final VURI uri, final String... fileExtensions) {
    this(Metamodel.getNsURISet(nsURI), uri, new DefaultTUIDCalculatorAndResolver(Metamodel.getTUIDPrefix(nsURI)), fileExtensions);
  }
  
  public Metamodel(final Set<String> nsURIs, final VURI uri, final String... fileExtensions) {
    this(nsURIs, uri, new DefaultTUIDCalculatorAndResolver(Metamodel.getTUIDPrefix(nsURIs)), fileExtensions);
  }
  
  public Metamodel(final String nsURI, final String nameOfIDFeature, final String nameOfNameAttribute, final VURI uri, final String... fileExtensions) {
    this(Metamodel.getNsURISet(nsURI), uri, 
      new DefaultTUIDCalculatorAndResolver(Metamodel.getTUIDPrefix(nsURI), nameOfIDFeature, nameOfNameAttribute), fileExtensions);
  }
  
  public Metamodel(final Set<String> nsURIs, final String nameOfIDFeature, final VURI uri, final String... fileExtensions) {
    this(nsURIs, uri, new DefaultTUIDCalculatorAndResolver(Metamodel.getTUIDPrefix(nsURIs), nameOfIDFeature), fileExtensions);
  }
  
  public Metamodel(final String nsURI, final VURI uri, final TUIDCalculatorAndResolver tuidCalculatorAndResolver, final String... fileExtensions) {
    this(Metamodel.getNsURISet(nsURI), uri, tuidCalculatorAndResolver, fileExtensions);
  }
  
  public Metamodel(final Set<String> nsURIs, final VURI uri, final TUIDCalculatorAndResolver tuidCalculatorAndResolver, final String... fileExtensions) {
    this(nsURIs, uri, tuidCalculatorAndResolver, Collections.<Object, Object>emptyMap(), Collections.<Object, Object>emptyMap(), fileExtensions);
  }
  
  public Metamodel(final Set<String> nsURIs, final VURI uri, final TUIDCalculatorAndResolver tuidCalculatorAndResolver, final Map<Object, Object> defaultLoadOptions, final Map<Object, Object> defaultSaveOptions, final String... fileExtensions) {
    super(uri);
    this.fileExtensions = fileExtensions;
    this.tuidCalculatorAndResolver = tuidCalculatorAndResolver;
    this.nsURIs = nsURIs;
    this.defaultLoadOptions = defaultLoadOptions;
    this.defaultSaveOptions = defaultSaveOptions;
  }
  
  public String[] getFileExtensions() {
    return this.fileExtensions;
  }
  
  public String calculateTUIDFromEObject(final EObject eObject) {
    return this.tuidCalculatorAndResolver.calculateTUIDFromEObject(eObject);
  }
  
  /**
   * syntactic sugar for map[{@link #calculateTUIDFromEObject(EObject)}]
   * @param eObjects
   * @return
   */
  public List<String> calculateTUIDsFromEObjects(final List<EObject> eObjects) {
    final Function1<EObject, String> _function = (EObject it) -> {
      return this.calculateTUIDFromEObject(it);
    };
    return ListExtensions.<EObject, String>map(eObjects, _function);
  }
  
  public VURI getModelVURIContainingIdentifiedEObject(final String tuid) {
    final String modelVURI = this.tuidCalculatorAndResolver.getModelVURIContainingIdentifiedEObject(tuid);
    boolean _equals = Objects.equal(null, modelVURI);
    if (_equals) {
      return null;
    }
    return VURI.getInstance(modelVURI);
  }
  
  public EObject resolveEObjectFromRootAndFullTUID(final EObject root, final String tuid) {
    return this.tuidCalculatorAndResolver.resolveEObjectFromRootAndFullTUID(root, tuid);
  }
  
  public void removeRootFromTUIDCache(final EObject root) {
    this.tuidCalculatorAndResolver.removeRootFromCache(root);
  }
  
  public void removeIfRootAndCached(final String tuid) {
    this.tuidCalculatorAndResolver.removeIfRootAndCached(tuid);
  }
  
  public boolean hasMetaclassInstances(final List<EObject> eObjects) {
    for (final EObject eObject : eObjects) {
      boolean _or = false;
      boolean _or_1 = false;
      boolean _or_2 = false;
      boolean _or_3 = false;
      if ((null == eObject)) {
        _or_3 = true;
      } else {
        EClass _eClass = eObject.eClass();
        boolean _tripleEquals = (null == _eClass);
        _or_3 = _tripleEquals;
      }
      if (_or_3) {
        _or_2 = true;
      } else {
        EClass _eClass_1 = eObject.eClass();
        EPackage _ePackage = _eClass_1.getEPackage();
        boolean _tripleEquals_1 = (null == _ePackage);
        _or_2 = _tripleEquals_1;
      }
      if (_or_2) {
        _or_1 = true;
      } else {
        EClass _eClass_2 = eObject.eClass();
        EPackage _ePackage_1 = _eClass_2.getEPackage();
        String _nsURI = _ePackage_1.getNsURI();
        boolean _tripleEquals_2 = (null == _nsURI);
        _or_1 = _tripleEquals_2;
      }
      if (_or_1) {
        _or = true;
      } else {
        EClass _eClass_3 = eObject.eClass();
        EPackage _ePackage_2 = _eClass_3.getEPackage();
        String _nsURI_1 = _ePackage_2.getNsURI();
        boolean _contains = this.nsURIs.contains(_nsURI_1);
        boolean _not = (!_contains);
        _or = _not;
      }
      if (_or) {
        return false;
      }
    }
    return true;
  }
  
  public boolean hasTUID(final String tuid) {
    return this.tuidCalculatorAndResolver.isValidTUID(tuid);
  }
  
  public Set<String> getNsURIs() {
    return this.nsURIs;
  }
  
  public Map<Object, Object> getDefaultLoadOptions() {
    return this.defaultLoadOptions;
  }
  
  public Map<Object, Object> getDefaultSaveOptions() {
    return this.defaultSaveOptions;
  }
}

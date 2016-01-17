package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.MappingHelper;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class MappedCorrespondenceInstance extends AbstractDelegatingCorrespondenceInstanceDecorator<HashMap<Correspondence, Collection<String>>> {
  private HashMap<Correspondence, Collection<String>> correspondence2MappingMap;
  
  @SuppressWarnings("unchecked")
  public MappedCorrespondenceInstance(final CorrespondenceInstanceDecorator correspondenceInstance) {
    super(correspondenceInstance, 
      ((Class<HashMap<Correspondence, Collection<String>>>) new HashMap<Correspondence, MIRMappingRealization>().getClass()));
    HashMap<Correspondence, Collection<String>> _hashMap = new HashMap<Correspondence, Collection<String>>();
    this.correspondence2MappingMap = _hashMap;
  }
  
  @Override
  protected String getDecoratorFileExtPrefix() {
    return MappingHelper.getCorrespondenceDecoratorFileExtPrefix();
  }
  
  @Override
  protected HashMap<Correspondence, Collection<String>> getDecoratorObject() {
    return this.correspondence2MappingMap;
  }
  
  @Override
  protected void initializeFromDecoratorObject(final HashMap<Correspondence, Collection<String>> object) {
    this.correspondence2MappingMap = object;
  }
  
  @Override
  protected void initializeWithoutDecoratorObject() {
  }
  
  /**
   * Register a mapping for a correspondence that can then be retrieved by
   * calling {@link #getMappingsForCorrespondence(Correspondence)}.
   * @param correspondence
   * @param mapping
   */
  public void registerMappingForCorrespondence(final Correspondence correspondence, final MIRMappingRealization mapping) {
    boolean _containsKey = this.correspondence2MappingMap.containsKey(correspondence);
    boolean _not = (!_containsKey);
    if (_not) {
      HashSet<String> _hashSet = new HashSet<String>();
      this.correspondence2MappingMap.put(correspondence, _hashSet);
    }
    Collection<String> _get = this.correspondence2MappingMap.get(correspondence);
    String _mappingID = mapping.getMappingID();
    _get.add(_mappingID);
  }
  
  public Correspondence getMappedCorrespondence(final List<EObject> eObjects, final MIRMappingRealization mapping) {
    Set<Correspondence> _correspondences = this.getCorrespondences(eObjects);
    Iterable<Correspondence> _filter = Iterables.<Correspondence>filter(_correspondences, Correspondence.class);
    final Function1<Correspondence, Boolean> _function = (Correspondence it) -> {
      Collection<String> _mappingsForCorrespondence = this.getMappingsForCorrespondence(it);
      String _mappingID = mapping.getMappingID();
      return Boolean.valueOf(_mappingsForCorrespondence.contains(_mappingID));
    };
    Iterable<Correspondence> _filter_1 = IterableExtensions.<Correspondence>filter(_filter, _function);
    return IterableExtensions.<Correspondence>head(_filter_1);
  }
  
  /**
   * Returns the MIRMappingRealization that created a correspondence, or
   * <code>null</code>, if no mapping is coupled to the correspondence. To get
   * all MIRMappingRealizations for an EObject, first get all correspondences
   * from the {@link CorrespondenceInstance}, then use this method.
   */
  public Collection<String> getMappingsForCorrespondence(final Correspondence correspondence) {
    return this.correspondence2MappingMap.get(correspondence);
  }
  
  /**
   * Returns all Correspondences that correspond to a mapping.
   */
  public Set<Correspondence> getCorrespondencesForMapping(final MIRMappingRealization mapping) {
    Set<Map.Entry<Correspondence, Collection<String>>> _entrySet = this.correspondence2MappingMap.entrySet();
    final Function1<Map.Entry<Correspondence, Collection<String>>, Boolean> _function = (Map.Entry<Correspondence, Collection<String>> it) -> {
      Collection<String> _value = it.getValue();
      String _mappingID = mapping.getMappingID();
      return Boolean.valueOf(_value.contains(_mappingID));
    };
    Iterable<Map.Entry<Correspondence, Collection<String>>> _filter = IterableExtensions.<Map.Entry<Correspondence, Collection<String>>>filter(_entrySet, _function);
    final Function1<Map.Entry<Correspondence, Collection<String>>, Correspondence> _function_1 = (Map.Entry<Correspondence, Collection<String>> it) -> {
      return it.getKey();
    };
    Iterable<Correspondence> _map = IterableExtensions.<Map.Entry<Correspondence, Collection<String>>, Correspondence>map(_filter, _function_1);
    return IterableExtensions.<Correspondence>toSet(_map);
  }
  
  public void unregisterMappingForCorrespondence(final MIRMappingRealization mapping, final Correspondence correspondence) {
    boolean _or = false;
    boolean _containsKey = this.correspondence2MappingMap.containsKey(correspondence);
    boolean _not = (!_containsKey);
    if (_not) {
      _or = true;
    } else {
      Collection<String> _get = this.correspondence2MappingMap.get(correspondence);
      String _mappingID = mapping.getMappingID();
      boolean _contains = _get.contains(_mappingID);
      boolean _not_1 = (!_contains);
      _or = _not_1;
    }
    if (_or) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Mapping ");
      String _mappingID_1 = mapping.getMappingID();
      _builder.append(_mappingID_1, "");
      _builder.append(" is not registered for correspondence ");
      String _string = correspondence.toString();
      _builder.append(_string, "");
      String _string_1 = _builder.toString();
      throw new IllegalArgumentException(_string_1);
    } else {
      Collection<String> _get_1 = this.correspondence2MappingMap.get(correspondence);
      String _mappingID_2 = mapping.getMappingID();
      _get_1.remove(_mappingID_2);
    }
  }
  
  /**
   * Checks if the given mapping maps <code>eObject</code> and returns the
   * target.
   * @param eObjectthe {@link EObject} to check
   * @param correspondenceInstance
   * @param mapping
   * @return The target of the mapping if this mapping maps
   * <code>eObject</code>, <code>null</code> otherwise.
   */
  public List<EObject> getMappingTarget(final List<EObject> eObjects, final MIRMappingRealization mapping) {
    Set<Correspondence> _correspondences = this.correspondenceInstance.getCorrespondences(eObjects);
    Iterable<Correspondence> _filter = Iterables.<Correspondence>filter(_correspondences, Correspondence.class);
    final Function1<Correspondence, Boolean> _function = (Correspondence it) -> {
      Collection<String> _mappingsForCorrespondence = this.getMappingsForCorrespondence(it);
      String _mappingID = mapping.getMappingID();
      return Boolean.valueOf(_mappingsForCorrespondence.contains(_mappingID));
    };
    Iterable<Correspondence> _filter_1 = IterableExtensions.<Correspondence>filter(_filter, _function);
    Correspondence _head = IterableExtensions.<Correspondence>head(_filter_1);
    List<EObject> _opposite = null;
    if (_head!=null) {
      _opposite=MappedCorrespondenceInstance.getOpposite(_head, eObjects);
    }
    return _opposite;
  }
  
  public static List<EObject> getOpposite(final Correspondence correspondence, final List<EObject> objects) {
    final EList<EObject> as = correspondence.getAs();
    final EList<EObject> bs = correspondence.getBs();
    boolean _equals = Objects.equal(objects, as);
    if (_equals) {
      return bs;
    } else {
      boolean _equals_1 = Objects.equal(objects, bs);
      if (_equals_1) {
        return as;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The given List<EObject> (");
        String _string = objects.toString();
        _builder.append(_string, "");
        _builder.append(") is not on one side of correspondence ");
        String _string_1 = correspondence.toString();
        _builder.append(_string_1, "");
        throw new IllegalArgumentException(_builder.toString());
      }
    }
  }
  
  /**
   * Checks if the given mapping maps <code>eObject</code>.
   * @param eObjectthe {@link EObject} to check
   * @param correspondenceInstance
   * @param mapping
   * @return <code>true</code> if this mapping maps <code>eObject</code>
   */
  public boolean checkIfMappedBy(final List<EObject> eObjects, final MIRMappingRealization mapping) {
    List<EObject> _mappingTarget = this.getMappingTarget(eObjects, mapping);
    return (_mappingTarget != null);
  }
}

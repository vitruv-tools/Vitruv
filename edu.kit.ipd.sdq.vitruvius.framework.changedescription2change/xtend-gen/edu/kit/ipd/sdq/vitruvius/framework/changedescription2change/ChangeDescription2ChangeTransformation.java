package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.changedescription2change.ChangeBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class ChangeDescription2ChangeTransformation {
  public static List<EChange> transform(final ChangeDescription changeDescription) {
    final BasicEList<EChange> eChanges = new BasicEList<EChange>();
    final EList<EObject> objectsToAttach = changeDescription.getObjectsToAttach();
    final Consumer<EObject> _function = (EObject it) -> {
      ChangeDescription2ChangeTransformation.recursivelyAddChangesForNonDefaultValues(it, eChanges);
    };
    objectsToAttach.forEach(_function);
    final EList<EObject> objectsToDetach = changeDescription.getObjectsToDetach();
    EMap<EObject, EList<FeatureChange>> _objectChanges = changeDescription.getObjectChanges();
    final Set<Map.Entry<EObject, EList<FeatureChange>>> objectAndFeatureChangePairs = _objectChanges.entrySet();
    return null;
  }
  
  public static void recursivelyAddChangesForNonDefaultValues(final EObject eObject, final List<EChange> eChanges) {
    boolean _hasNonDefaultValue = ChangeDescription2ChangeTransformation.hasNonDefaultValue(eObject);
    if (_hasNonDefaultValue) {
      EClass _eClass = eObject.eClass();
      EList<EReference> _eAllReferences = _eClass.getEAllReferences();
      for (final EReference reference : _eAllReferences) {
        boolean _hasChangeableUnderivedPersistedNotContainingNonDefaultValue = ChangeDescription2ChangeTransformation.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(eObject, reference);
        if (_hasChangeableUnderivedPersistedNotContainingNonDefaultValue) {
          EChange _createChangeForReferencedObject = ChangeBridge.createChangeForReferencedObject(eObject, reference);
          eChanges.add(_createChangeForReferencedObject);
          EList<EObject> _referenceValueList = ChangeDescription2ChangeTransformation.getReferenceValueList(eObject, reference);
          final Consumer<EObject> _function = (EObject it) -> {
            ChangeDescription2ChangeTransformation.recursivelyAddChangesForNonDefaultValues(it, eChanges);
          };
          _referenceValueList.forEach(_function);
        }
      }
    }
  }
  
  public static boolean hasNonDefaultValue(final EObject eObject) {
    boolean hasNonDefaultValue = false;
    EClass _eClass = eObject.eClass();
    EList<EStructuralFeature> _eAllStructuralFeatures = _eClass.getEAllStructuralFeatures();
    for (final EStructuralFeature feature : _eAllStructuralFeatures) {
      boolean _isChangeableUnderivedPersistedNotContainingFeature = ChangeDescription2ChangeTransformation.isChangeableUnderivedPersistedNotContainingFeature(eObject, feature);
      if (_isChangeableUnderivedPersistedNotContainingFeature) {
        boolean _or = false;
        if (hasNonDefaultValue) {
          _or = true;
        } else {
          boolean _hasNonDefaultValue = ChangeDescription2ChangeTransformation.hasNonDefaultValue(eObject, feature);
          _or = _hasNonDefaultValue;
        }
        hasNonDefaultValue = _or;
      }
    }
    return hasNonDefaultValue;
  }
  
  public static boolean isChangeableUnderivedPersistedNotContainingFeature(final EObject eObject, final EStructuralFeature feature) {
    boolean _and = false;
    boolean _and_1 = false;
    boolean _and_2 = false;
    boolean _isChangeable = feature.isChangeable();
    if (!_isChangeable) {
      _and_2 = false;
    } else {
      boolean _isDerived = feature.isDerived();
      boolean _not = (!_isDerived);
      _and_2 = _not;
    }
    if (!_and_2) {
      _and_1 = false;
    } else {
      boolean _isTransient = feature.isTransient();
      boolean _not_1 = (!_isTransient);
      _and_1 = _not_1;
    }
    if (!_and_1) {
      _and = false;
    } else {
      EStructuralFeature _eContainingFeature = eObject.eContainingFeature();
      boolean _notEquals = (!Objects.equal(feature, _eContainingFeature));
      _and = _notEquals;
    }
    return _and;
  }
  
  public static boolean hasNonDefaultValue(final EObject eObject, final EStructuralFeature feature) {
    final Object value = eObject.eGet(feature);
    boolean _isMany = feature.isMany();
    if (_isMany) {
      final EList<?> eList = ((EList<?>) value);
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(eList, null));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _isEmpty = eList.isEmpty();
        boolean _not = (!_isEmpty);
        _and = _not;
      }
      return _and;
    } else {
      Object _defaultValue = feature.getDefaultValue();
      return (!Objects.equal(value, _defaultValue));
    }
  }
  
  public static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(final EObject eObject, final EStructuralFeature feature) {
    boolean _and = false;
    boolean _isChangeableUnderivedPersistedNotContainingFeature = ChangeDescription2ChangeTransformation.isChangeableUnderivedPersistedNotContainingFeature(eObject, feature);
    if (!_isChangeableUnderivedPersistedNotContainingFeature) {
      _and = false;
    } else {
      boolean _hasNonDefaultValue = ChangeDescription2ChangeTransformation.hasNonDefaultValue(eObject, feature);
      _and = _hasNonDefaultValue;
    }
    return _and;
  }
  
  public static EList<EObject> getReferenceValueList(final EObject eObject, final EReference reference) {
    EList<?> _valueList = ChangeDescription2ChangeTransformation.getValueList(eObject, reference);
    return ((EList<EObject>) _valueList);
  }
  
  public static EList<?> getValueList(final EObject eObject, final EStructuralFeature feature) {
    final Object value = eObject.eGet(feature);
    boolean _and = false;
    boolean _isMany = feature.isMany();
    if (!_isMany) {
      _and = false;
    } else {
      boolean _notEquals = (!Objects.equal(value, null));
      _and = _notEquals;
    }
    if (_and) {
      return ((EList<?>) value);
    } else {
      return new BasicEList<Object>(Collections.<Object>unmodifiableList(CollectionLiterals.<Object>newArrayList(value)));
    }
  }
}

package edu.kit.ipd.sdq.vitruvius.framework.transformationexecuter;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class TransformationUtils {
  private TransformationUtils() {
  }
  
  public static TransformationChangeResult createTransformationChangeResult(final EObject[] newRootObjectsToSave, final EObject[] objectsToDelete, final EObject[] existingObjectsToSave) {
    TransformationChangeResult _xblockexpression = null;
    {
      TransformationChangeResult transformationChangeResult = new TransformationChangeResult();
      boolean _notEquals = (!Objects.equal(null, newRootObjectsToSave));
      if (_notEquals) {
        Set<EObject> _newRootObjectsToSave = transformationChangeResult.getNewRootObjectsToSave();
        CollectionExtensions.<EObject>addAll(_newRootObjectsToSave, newRootObjectsToSave);
      }
      boolean _notEquals_1 = (!Objects.equal(null, objectsToDelete));
      if (_notEquals_1) {
        Set<EObject> _existingObjectsToDelete = transformationChangeResult.getExistingObjectsToDelete();
        CollectionExtensions.<EObject>addAll(_existingObjectsToDelete, objectsToDelete);
      }
      boolean _notEquals_2 = (!Objects.equal(null, existingObjectsToSave));
      if (_notEquals_2) {
        Set<EObject> _existingObjectsToSave = transformationChangeResult.getExistingObjectsToSave();
        CollectionExtensions.<EObject>addAll(_existingObjectsToSave, existingObjectsToSave);
      }
      _xblockexpression = transformationChangeResult;
    }
    return _xblockexpression;
  }
  
  public static TransformationChangeResult createTransformationChangeResultForNewRootEObjects(final EObject[] newRootEObjects) {
    return TransformationUtils.createTransformationChangeResult(newRootEObjects, null, null);
  }
  
  public static TransformationChangeResult createTransformationChangeResultForEObjectsToSave(final EObject[] eObjectsToSave) {
    return TransformationUtils.createTransformationChangeResult(null, null, eObjectsToSave);
  }
  
  public static TransformationChangeResult createTransformationChangeResultForEObjectsToDelete(final EObject[] eObjectsToDelete) {
    return TransformationUtils.createTransformationChangeResult(null, eObjectsToDelete, null);
  }
  
  public static EAttribute getAttributeByNameFromEObject(final String attributeName, final EObject eObject) {
    EClass _eClass = eObject.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(attributeName));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    return _iterator.next();
  }
}

package edu.kit.ipd.sdq.vitruvius.framework.util.changes;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.change.ChangeDescriptionUtil;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.change.impl.ChangeDescriptionImplUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ResourceChange;
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.lib.annotations.Delegate;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * A forward change description that encapsulates the state before a change,
 * the delta to obtain the state after a change,
 * and information about the containment of attached and detached objects before the change happened.<br/>
 * 
 * EMF's {@link ChangeDescription}, however always yields containment information before the last application of the change,
 * even if this application was a call to {@link ChangeDescription#applyAndReverse applyAndReverse} or {@link ChangeDescription#copyAndReverse copyAndReverse}!
 */
@SuppressWarnings("all")
public class ForwardChangeDescription implements ChangeDescription {
  @Delegate
  public final ChangeDescriptionImpl changeDescription;
  
  /**
   * maps objects to their container and containment before the recorded change is reversed
   */
  private final Map<EObject, Pair<EObject, EReference>> containmentBeforeReversion;
  
  /**
   * Creates a new forward change description using the given backward change description by copying and reversing it if the given map is not {@code null} and otherwise applying and reversing it.
   */
  public ForwardChangeDescription(final ChangeDescription backwardChangeDescription, final Map<EObject, URI> eObjectToProxyURIMap) {
    final ChangeDescriptionImpl cdi = ChangeDescriptionUtil.asImpl(backwardChangeDescription);
    this.changeDescription = cdi;
    Map<EObject, Pair<EObject, EReference>> _containmentBeforeReversion = ChangeDescriptionImplUtil.getContainmentBeforeReversion(cdi);
    this.containmentBeforeReversion = _containmentBeforeReversion;
    boolean _equals = Objects.equal(eObjectToProxyURIMap, null);
    if (_equals) {
      this.applyAndReverse();
    } else {
      this.copyAndReverse(eObjectToProxyURIMap);
    }
  }
  
  /**
   * Creates a new forward change description using the given backward change description by applying and reversing it.
   */
  public ForwardChangeDescription(final ChangeDescription changeDescription) {
    this(changeDescription, null);
  }
  
  public EObject getContainerBeforeReversion(final EObject eObject) {
    Pair<EObject, EReference> _get = this.containmentBeforeReversion.get(eObject);
    EObject _key = null;
    if (_get!=null) {
      _key=_get.getKey();
    }
    return _key;
  }
  
  public EReference getContainmentReferenceBeforeReversion(final EObject eObject) {
    Pair<EObject, EReference> _get = this.containmentBeforeReversion.get(eObject);
    EReference _value = null;
    if (_get!=null) {
      _value=_get.getValue();
    }
    return _value;
  }
  
  public EList<Adapter> eAdapters() {
    return this.changeDescription.eAdapters();
  }
  
  public boolean eDeliver() {
    return this.changeDescription.eDeliver();
  }
  
  public void eNotify(final Notification notification) {
    this.changeDescription.eNotify(notification);
  }
  
  public void eSetDeliver(final boolean deliver) {
    this.changeDescription.eSetDeliver(deliver);
  }
  
  public TreeIterator<EObject> eAllContents() {
    return this.changeDescription.eAllContents();
  }
  
  public EClass eClass() {
    return this.changeDescription.eClass();
  }
  
  public EObject eContainer() {
    return this.changeDescription.eContainer();
  }
  
  public EStructuralFeature eContainingFeature() {
    return this.changeDescription.eContainingFeature();
  }
  
  public EReference eContainmentFeature() {
    return this.changeDescription.eContainmentFeature();
  }
  
  public EList<EObject> eContents() {
    return this.changeDescription.eContents();
  }
  
  public EList<EObject> eCrossReferences() {
    return this.changeDescription.eCrossReferences();
  }
  
  public Object eGet(final EStructuralFeature feature) {
    return this.changeDescription.eGet(feature);
  }
  
  public Object eGet(final EStructuralFeature feature, final boolean resolve) {
    return this.changeDescription.eGet(feature, resolve);
  }
  
  public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
    return this.changeDescription.eInvoke(operation, arguments);
  }
  
  public boolean eIsProxy() {
    return this.changeDescription.eIsProxy();
  }
  
  public boolean eIsSet(final EStructuralFeature feature) {
    return this.changeDescription.eIsSet(feature);
  }
  
  public Resource eResource() {
    return this.changeDescription.eResource();
  }
  
  public void eSet(final EStructuralFeature feature, final Object newValue) {
    this.changeDescription.eSet(feature, newValue);
  }
  
  public void eUnset(final EStructuralFeature feature) {
    this.changeDescription.eUnset(feature);
  }
  
  public void apply() {
    this.changeDescription.apply();
  }
  
  public void applyAndReverse() {
    this.changeDescription.applyAndReverse();
  }
  
  public void copyAndReverse(final Map<EObject, URI> eObjectToProxyURIMap) {
    this.changeDescription.copyAndReverse(eObjectToProxyURIMap);
  }
  
  public EMap<EObject, EList<FeatureChange>> getObjectChanges() {
    return this.changeDescription.getObjectChanges();
  }
  
  public EList<EObject> getObjectsToAttach() {
    return this.changeDescription.getObjectsToAttach();
  }
  
  public EList<EObject> getObjectsToDetach() {
    return this.changeDescription.getObjectsToDetach();
  }
  
  public EList<ResourceChange> getResourceChanges() {
    return this.changeDescription.getResourceChanges();
  }
}

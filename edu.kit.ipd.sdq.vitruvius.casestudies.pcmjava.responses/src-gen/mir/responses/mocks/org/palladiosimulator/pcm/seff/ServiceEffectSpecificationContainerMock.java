package mir.responses.mocks.org.palladiosimulator.pcm.seff;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.eclipse.emf.cdo.CDOLock;
import org.eclipse.emf.cdo.CDOObjectHistory;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.lock.CDOLockState;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.security.CDOPermission;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class ServiceEffectSpecificationContainerMock implements ServiceEffectSpecification {
  public ServiceEffectSpecificationContainerMock(final ServiceEffectSpecification containedObject, final EObject containerObject) {
    super();
    this.containedObject = containedObject;
    this.containerObject = containerObject;
  }
  
  private ServiceEffectSpecification containedObject;
  
  private EObject containerObject;
  
  public BasicComponent getBasicComponent_ServiceEffectSpecification() {
    return containedObject.getBasicComponent_ServiceEffectSpecification();
  }
  
  public void setBasicComponent_ServiceEffectSpecification(final BasicComponent arg0) {
    containedObject.setBasicComponent_ServiceEffectSpecification(arg0);
  }
  
  public String getSeffTypeID() {
    return containedObject.getSeffTypeID();
  }
  
  public void setSeffTypeID(final String arg0) {
    containedObject.setSeffTypeID(arg0);
  }
  
  public void setDescribedService__SEFF(final Signature arg0) {
    containedObject.setDescribedService__SEFF(arg0);
  }
  
  public Signature getDescribedService__SEFF() {
    return containedObject.getDescribedService__SEFF();
  }
  
  public boolean ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(final DiagnosticChain arg0, final Map arg1) {
    return containedObject.ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(arg0, arg1);
  }
  
  public CDORevision cdoRevision() {
    return containedObject.cdoRevision();
  }
  
  public CDORevision cdoRevision(final boolean arg0) {
    return containedObject.cdoRevision(arg0);
  }
  
  public CDOLockState cdoLockState() {
    return containedObject.cdoLockState();
  }
  
  public CDOID cdoID() {
    return containedObject.cdoID();
  }
  
  public CDOState cdoState() {
    return containedObject.cdoState();
  }
  
  public CDOView cdoView() {
    return containedObject.cdoView();
  }
  
  public CDOResource cdoDirectResource() {
    return containedObject.cdoDirectResource();
  }
  
  public boolean cdoConflict() {
    return containedObject.cdoConflict();
  }
  
  public CDOObjectHistory cdoHistory() {
    return containedObject.cdoHistory();
  }
  
  public CDOPermission cdoPermission() {
    return containedObject.cdoPermission();
  }
  
  public boolean cdoInvalid() {
    return containedObject.cdoInvalid();
  }
  
  public void cdoPrefetch(final int arg0) {
    containedObject.cdoPrefetch(arg0);
  }
  
  public CDOLock cdoReadLock() {
    return containedObject.cdoReadLock();
  }
  
  public void cdoReload() {
    containedObject.cdoReload();
  }
  
  public CDOResource cdoResource() {
    return containedObject.cdoResource();
  }
  
  public CDOLock cdoWriteOption() {
    return containedObject.cdoWriteOption();
  }
  
  public CDOLock cdoWriteLock() {
    return containedObject.cdoWriteLock();
  }
  
  public EObject eContainer() {
    return containerObject;
  }
  
  public EList eCrossReferences() {
    return containedObject.eCrossReferences();
  }
  
  public EClass eClass() {
    return containedObject.eClass();
  }
  
  public void eUnset(final EStructuralFeature arg0) {
    containedObject.eUnset(arg0);
  }
  
  public Object eInvoke(final EOperation arg0, final EList arg1) throws InvocationTargetException {
    return containedObject.eInvoke(arg0, arg1);
  }
  
  public Resource eResource() {
    return containerObject.eResource();
  }
  
  public boolean eIsSet(final EStructuralFeature arg0) {
    return containedObject.eIsSet(arg0);
  }
  
  public void eSet(final EStructuralFeature arg0, final Object arg1) {
    containedObject.eSet(arg0, arg1);
  }
  
  public Object eGet(final EStructuralFeature arg0) {
    return containedObject.eGet(arg0);
  }
  
  public Object eGet(final EStructuralFeature arg0, final boolean arg1) {
    return containedObject.eGet(arg0, arg1);
  }
  
  public boolean eIsProxy() {
    return containedObject.eIsProxy();
  }
  
  public EStructuralFeature eContainingFeature() {
    return containedObject.eContainingFeature();
  }
  
  public EReference eContainmentFeature() {
    return containedObject.eContainmentFeature();
  }
  
  public EList eContents() {
    return containedObject.eContents();
  }
  
  public TreeIterator eAllContents() {
    return containedObject.eAllContents();
  }
  
  public boolean eDeliver() {
    return containedObject.eDeliver();
  }
  
  public void eSetDeliver(final boolean arg0) {
    containedObject.eSetDeliver(arg0);
  }
  
  public void eNotify(final Notification arg0) {
    containedObject.eNotify(arg0);
  }
  
  public EList eAdapters() {
    return containedObject.eAdapters();
  }
}

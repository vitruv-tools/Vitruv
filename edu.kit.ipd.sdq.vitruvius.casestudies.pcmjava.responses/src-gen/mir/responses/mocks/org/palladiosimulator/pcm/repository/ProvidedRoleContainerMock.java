package mir.responses.mocks.org.palladiosimulator.pcm.repository;

import java.lang.reflect.InvocationTargetException;
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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
public class ProvidedRoleContainerMock implements ProvidedRole {
  public ProvidedRoleContainerMock(final ProvidedRole containedObject, final EObject containerObject) {
    super();
    this.containedObject = containedObject;
    this.containerObject = containerObject;
  }
  
  private ProvidedRole containedObject;
  
  private EObject containerObject;
  
  public InterfaceProvidingEntity getProvidingEntity_ProvidedRole() {
    return containedObject.getProvidingEntity_ProvidedRole();
  }
  
  public void setProvidingEntity_ProvidedRole(final InterfaceProvidingEntity arg0) {
    containedObject.setProvidingEntity_ProvidedRole(arg0);
  }
  
  public String getId() {
    return containedObject.getId();
  }
  
  public void setId(final String arg0) {
    containedObject.setId(arg0);
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
  
  public CDORevision cdoRevision(final boolean arg0) {
    return containedObject.cdoRevision(arg0);
  }
  
  public CDORevision cdoRevision() {
    return containedObject.cdoRevision();
  }
  
  public CDOResource cdoDirectResource() {
    return containedObject.cdoDirectResource();
  }
  
  public void cdoPrefetch(final int arg0) {
    containedObject.cdoPrefetch(arg0);
  }
  
  public CDOLock cdoWriteLock() {
    return containedObject.cdoWriteLock();
  }
  
  public boolean cdoInvalid() {
    return containedObject.cdoInvalid();
  }
  
  public void cdoReload() {
    containedObject.cdoReload();
  }
  
  public CDOLock cdoReadLock() {
    return containedObject.cdoReadLock();
  }
  
  public boolean cdoConflict() {
    return containedObject.cdoConflict();
  }
  
  public CDOObjectHistory cdoHistory() {
    return containedObject.cdoHistory();
  }
  
  public CDOLock cdoWriteOption() {
    return containedObject.cdoWriteOption();
  }
  
  public CDOResource cdoResource() {
    return containedObject.cdoResource();
  }
  
  public CDOPermission cdoPermission() {
    return containedObject.cdoPermission();
  }
  
  public EStructuralFeature eContainingFeature() {
    return containedObject.eContainingFeature();
  }
  
  public EReference eContainmentFeature() {
    return containedObject.eContainmentFeature();
  }
  
  public boolean eIsProxy() {
    return containedObject.eIsProxy();
  }
  
  public Resource eResource() {
    return containerObject.eResource();
  }
  
  public Object eInvoke(final EOperation arg0, final EList arg1) throws InvocationTargetException {
    return containedObject.eInvoke(arg0, arg1);
  }
  
  public Object eGet(final EStructuralFeature arg0, final boolean arg1) {
    return containedObject.eGet(arg0, arg1);
  }
  
  public Object eGet(final EStructuralFeature arg0) {
    return containedObject.eGet(arg0);
  }
  
  public void eSet(final EStructuralFeature arg0, final Object arg1) {
    containedObject.eSet(arg0, arg1);
  }
  
  public boolean eIsSet(final EStructuralFeature arg0) {
    return containedObject.eIsSet(arg0);
  }
  
  public void eUnset(final EStructuralFeature arg0) {
    containedObject.eUnset(arg0);
  }
  
  public EList eContents() {
    return containedObject.eContents();
  }
  
  public TreeIterator eAllContents() {
    return containedObject.eAllContents();
  }
  
  public EList eCrossReferences() {
    return containedObject.eCrossReferences();
  }
  
  public EClass eClass() {
    return containedObject.eClass();
  }
  
  public EObject eContainer() {
    return containerObject;
  }
  
  public void eNotify(final Notification arg0) {
    containedObject.eNotify(arg0);
  }
  
  public boolean eDeliver() {
    return containedObject.eDeliver();
  }
  
  public void eSetDeliver(final boolean arg0) {
    containedObject.eSetDeliver(arg0);
  }
  
  public EList eAdapters() {
    return containedObject.eAdapters();
  }
  
  public String getEntityName() {
    return containedObject.getEntityName();
  }
  
  public void setEntityName(final String arg0) {
    containedObject.setEntityName(arg0);
  }
}

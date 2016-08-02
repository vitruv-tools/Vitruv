package mir.responses.mocks.org.palladiosimulator.pcm.repository;

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
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class OperationInterfaceContainerMock implements OperationInterface {
  public OperationInterfaceContainerMock(final OperationInterface containedObject, final EObject containerObject) {
    super();
    this.containedObject = containedObject;
    this.containerObject = containerObject;
  }
  
  private OperationInterface containedObject;
  
  private EObject containerObject;
  
  public boolean SignaturesHaveToBeUniqueForAnInterface(final DiagnosticChain arg0, final Map arg1) {
    return containedObject.SignaturesHaveToBeUniqueForAnInterface(arg0, arg1);
  }
  
  public EList getSignatures__OperationInterface() {
    return containedObject.getSignatures__OperationInterface();
  }
  
  public boolean NoProtocolTypeIDUsedTwice(final DiagnosticChain arg0, final Map arg1) {
    return containedObject.NoProtocolTypeIDUsedTwice(arg0, arg1);
  }
  
  public EList getProtocols__Interface() {
    return containedObject.getProtocols__Interface();
  }
  
  public void setRepository__Interface(final Repository arg0) {
    containedObject.setRepository__Interface(arg0);
  }
  
  public EList getRequiredCharacterisations() {
    return containedObject.getRequiredCharacterisations();
  }
  
  public EList getParentInterfaces__Interface() {
    return containedObject.getParentInterfaces__Interface();
  }
  
  public Repository getRepository__Interface() {
    return containedObject.getRepository__Interface();
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
  
  public CDORevision cdoRevision() {
    return containedObject.cdoRevision();
  }
  
  public CDORevision cdoRevision(final boolean arg0) {
    return containedObject.cdoRevision(arg0);
  }
  
  public CDOState cdoState() {
    return containedObject.cdoState();
  }
  
  public CDOView cdoView() {
    return containedObject.cdoView();
  }
  
  public boolean cdoConflict() {
    return containedObject.cdoConflict();
  }
  
  public CDOObjectHistory cdoHistory() {
    return containedObject.cdoHistory();
  }
  
  public boolean cdoInvalid() {
    return containedObject.cdoInvalid();
  }
  
  public CDOPermission cdoPermission() {
    return containedObject.cdoPermission();
  }
  
  public void cdoPrefetch(final int arg0) {
    containedObject.cdoPrefetch(arg0);
  }
  
  public CDOLock cdoReadLock() {
    return containedObject.cdoReadLock();
  }
  
  public CDOLock cdoWriteOption() {
    return containedObject.cdoWriteOption();
  }
  
  public CDOLock cdoWriteLock() {
    return containedObject.cdoWriteLock();
  }
  
  public CDOResource cdoResource() {
    return containedObject.cdoResource();
  }
  
  public void cdoReload() {
    containedObject.cdoReload();
  }
  
  public CDOResource cdoDirectResource() {
    return containedObject.cdoDirectResource();
  }
  
  public EReference eContainmentFeature() {
    return containedObject.eContainmentFeature();
  }
  
  public EStructuralFeature eContainingFeature() {
    return containedObject.eContainingFeature();
  }
  
  public EObject eContainer() {
    return containerObject;
  }
  
  public EClass eClass() {
    return containedObject.eClass();
  }
  
  public boolean eIsSet(final EStructuralFeature arg0) {
    return containedObject.eIsSet(arg0);
  }
  
  public void eSet(final EStructuralFeature arg0, final Object arg1) {
    containedObject.eSet(arg0, arg1);
  }
  
  public Object eGet(final EStructuralFeature arg0, final boolean arg1) {
    return containedObject.eGet(arg0, arg1);
  }
  
  public Object eGet(final EStructuralFeature arg0) {
    return containedObject.eGet(arg0);
  }
  
  public void eUnset(final EStructuralFeature arg0) {
    containedObject.eUnset(arg0);
  }
  
  public boolean eIsProxy() {
    return containedObject.eIsProxy();
  }
  
  public Object eInvoke(final EOperation arg0, final EList arg1) throws InvocationTargetException {
    return containedObject.eInvoke(arg0, arg1);
  }
  
  public Resource eResource() {
    return containerObject.eResource();
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
  
  public void eNotify(final Notification arg0) {
    containedObject.eNotify(arg0);
  }
  
  public void eSetDeliver(final boolean arg0) {
    containedObject.eSetDeliver(arg0);
  }
  
  public boolean eDeliver() {
    return containedObject.eDeliver();
  }
  
  public EList eAdapters() {
    return containedObject.eAdapters();
  }
  
  public void setEntityName(final String arg0) {
    containedObject.setEntityName(arg0);
  }
  
  public String getEntityName() {
    return containedObject.getEntityName();
  }
}

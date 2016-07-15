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
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

@SuppressWarnings("all")
public class ParameterContainerMock implements Parameter {
  public ParameterContainerMock(final Parameter containedObject, final EObject containerObject) {
    super();
    this.containedObject = containedObject;
    this.containerObject = containerObject;
  }
  
  private Parameter containedObject;
  
  private EObject containerObject;
  
  public InfrastructureSignature getInfrastructureSignature__Parameter() {
    return containedObject.getInfrastructureSignature__Parameter();
  }
  
  public void setInfrastructureSignature__Parameter(final InfrastructureSignature arg0) {
    containedObject.setInfrastructureSignature__Parameter(arg0);
  }
  
  public void setParameterName(final String arg0) {
    containedObject.setParameterName(arg0);
  }
  
  public String getParameterName() {
    return containedObject.getParameterName();
  }
  
  public void setEventType__Parameter(final EventType arg0) {
    containedObject.setEventType__Parameter(arg0);
  }
  
  public void setOperationSignature__Parameter(final OperationSignature arg0) {
    containedObject.setOperationSignature__Parameter(arg0);
  }
  
  public ParameterModifier getModifier__Parameter() {
    return containedObject.getModifier__Parameter();
  }
  
  public void setResourceSignature__Parameter(final ResourceSignature arg0) {
    containedObject.setResourceSignature__Parameter(arg0);
  }
  
  public void setDataType__Parameter(final DataType arg0) {
    containedObject.setDataType__Parameter(arg0);
  }
  
  public OperationSignature getOperationSignature__Parameter() {
    return containedObject.getOperationSignature__Parameter();
  }
  
  public void setModifier__Parameter(final ParameterModifier arg0) {
    containedObject.setModifier__Parameter(arg0);
  }
  
  public DataType getDataType__Parameter() {
    return containedObject.getDataType__Parameter();
  }
  
  public ResourceSignature getResourceSignature__Parameter() {
    return containedObject.getResourceSignature__Parameter();
  }
  
  public EventType getEventType__Parameter() {
    return containedObject.getEventType__Parameter();
  }
  
  public String getEntityName() {
    return containedObject.getEntityName();
  }
  
  public void setEntityName(final String arg0) {
    containedObject.setEntityName(arg0);
  }
  
  public CDOResource cdoDirectResource() {
    return containedObject.cdoDirectResource();
  }
  
  public CDOLockState cdoLockState() {
    return containedObject.cdoLockState();
  }
  
  public CDORevision cdoRevision(final boolean arg0) {
    return containedObject.cdoRevision(arg0);
  }
  
  public CDORevision cdoRevision() {
    return containedObject.cdoRevision();
  }
  
  public CDOView cdoView() {
    return containedObject.cdoView();
  }
  
  public CDOID cdoID() {
    return containedObject.cdoID();
  }
  
  public CDOState cdoState() {
    return containedObject.cdoState();
  }
  
  public CDOPermission cdoPermission() {
    return containedObject.cdoPermission();
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
  
  public CDOLock cdoWriteLock() {
    return containedObject.cdoWriteLock();
  }
  
  public void cdoPrefetch(final int arg0) {
    containedObject.cdoPrefetch(arg0);
  }
  
  public boolean cdoInvalid() {
    return containedObject.cdoInvalid();
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
  
  public Object eGet(final EStructuralFeature arg0) {
    return containedObject.eGet(arg0);
  }
  
  public Object eGet(final EStructuralFeature arg0, final boolean arg1) {
    return containedObject.eGet(arg0, arg1);
  }
  
  public Resource eResource() {
    return containerObject.eResource();
  }
  
  public void eUnset(final EStructuralFeature arg0) {
    containedObject.eUnset(arg0);
  }
  
  public void eSet(final EStructuralFeature arg0, final Object arg1) {
    containedObject.eSet(arg0, arg1);
  }
  
  public boolean eIsProxy() {
    return containedObject.eIsProxy();
  }
  
  public Object eInvoke(final EOperation arg0, final EList arg1) throws InvocationTargetException {
    return containedObject.eInvoke(arg0, arg1);
  }
  
  public boolean eIsSet(final EStructuralFeature arg0) {
    return containedObject.eIsSet(arg0);
  }
  
  public EList eContents() {
    return containedObject.eContents();
  }
  
  public EList eCrossReferences() {
    return containedObject.eCrossReferences();
  }
  
  public TreeIterator eAllContents() {
    return containedObject.eAllContents();
  }
  
  public EList eAdapters() {
    return containedObject.eAdapters();
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
}

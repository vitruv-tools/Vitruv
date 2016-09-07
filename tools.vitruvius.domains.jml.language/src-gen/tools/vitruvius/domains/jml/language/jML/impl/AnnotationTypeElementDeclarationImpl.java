/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration;
import tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation Type Element Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeElementDeclarationImpl#getAnnotationtypeelementrest <em>Annotationtypeelementrest</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationTypeElementDeclarationImpl extends ModifiableImpl implements AnnotationTypeElementDeclaration
{
  /**
   * The cached value of the '{@link #getAnnotationtypeelementrest() <em>Annotationtypeelementrest</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotationtypeelementrest()
   * @generated
   * @ordered
   */
  protected AnnotationTypeElementRest annotationtypeelementrest;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AnnotationTypeElementDeclarationImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return JMLPackage.Literals.ANNOTATION_TYPE_ELEMENT_DECLARATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationTypeElementRest getAnnotationtypeelementrest()
  {
    return annotationtypeelementrest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAnnotationtypeelementrest(AnnotationTypeElementRest newAnnotationtypeelementrest, NotificationChain msgs)
  {
    AnnotationTypeElementRest oldAnnotationtypeelementrest = annotationtypeelementrest;
    annotationtypeelementrest = newAnnotationtypeelementrest;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST, oldAnnotationtypeelementrest, newAnnotationtypeelementrest);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAnnotationtypeelementrest(AnnotationTypeElementRest newAnnotationtypeelementrest)
  {
    if (newAnnotationtypeelementrest != annotationtypeelementrest)
    {
      NotificationChain msgs = null;
      if (annotationtypeelementrest != null)
        msgs = ((InternalEObject)annotationtypeelementrest).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST, null, msgs);
      if (newAnnotationtypeelementrest != null)
        msgs = ((InternalEObject)newAnnotationtypeelementrest).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST, null, msgs);
      msgs = basicSetAnnotationtypeelementrest(newAnnotationtypeelementrest, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST, newAnnotationtypeelementrest, newAnnotationtypeelementrest));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST:
        return basicSetAnnotationtypeelementrest(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST:
        return getAnnotationtypeelementrest();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST:
        setAnnotationtypeelementrest((AnnotationTypeElementRest)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST:
        setAnnotationtypeelementrest((AnnotationTypeElementRest)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION__ANNOTATIONTYPEELEMENTREST:
        return annotationtypeelementrest != null;
    }
    return super.eIsSet(featureID);
  }

} //AnnotationTypeElementDeclarationImpl

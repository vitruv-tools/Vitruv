/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration;
import tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation Type Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeDeclarationImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationTypeDeclarationImpl#getAnnotationtypeelementdeclaration <em>Annotationtypeelementdeclaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationTypeDeclarationImpl extends InterfaceDeclarationImpl implements AnnotationTypeDeclaration
{
  /**
   * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIdentifier()
   * @generated
   * @ordered
   */
  protected static final String IDENTIFIER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIdentifier()
   * @generated
   * @ordered
   */
  protected String identifier = IDENTIFIER_EDEFAULT;

  /**
   * The cached value of the '{@link #getAnnotationtypeelementdeclaration() <em>Annotationtypeelementdeclaration</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotationtypeelementdeclaration()
   * @generated
   * @ordered
   */
  protected EList<AnnotationTypeElementDeclaration> annotationtypeelementdeclaration;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AnnotationTypeDeclarationImpl()
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
    return JMLPackage.Literals.ANNOTATION_TYPE_DECLARATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getIdentifier()
  {
    return identifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIdentifier(String newIdentifier)
  {
    String oldIdentifier = identifier;
    identifier = newIdentifier;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_TYPE_DECLARATION__IDENTIFIER, oldIdentifier, identifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AnnotationTypeElementDeclaration> getAnnotationtypeelementdeclaration()
  {
    if (annotationtypeelementdeclaration == null)
    {
      annotationtypeelementdeclaration = new EObjectContainmentEList<AnnotationTypeElementDeclaration>(AnnotationTypeElementDeclaration.class, this, JMLPackage.ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION);
    }
    return annotationtypeelementdeclaration;
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
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION:
        return ((InternalEList<?>)getAnnotationtypeelementdeclaration()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION:
        return getAnnotationtypeelementdeclaration();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION:
        getAnnotationtypeelementdeclaration().clear();
        getAnnotationtypeelementdeclaration().addAll((Collection<? extends AnnotationTypeElementDeclaration>)newValue);
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
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION:
        getAnnotationtypeelementdeclaration().clear();
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
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.ANNOTATION_TYPE_DECLARATION__ANNOTATIONTYPEELEMENTDECLARATION:
        return annotationtypeelementdeclaration != null && !annotationtypeelementdeclaration.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (identifier: ");
    result.append(identifier);
    result.append(')');
    return result.toString();
  }

} //AnnotationTypeDeclarationImpl

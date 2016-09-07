/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest;
import tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations;
import tools.vitruvius.domains.jml.language.jML.EnumConstants;
import tools.vitruvius.domains.jml.language.jML.EnumDeclaration;
import tools.vitruvius.domains.jml.language.jML.IdentifierHaving;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.Type;

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
 * An implementation of the model object '<em><b>Enum Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl#getImplementedTypes <em>Implemented Types</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl#getEnumconstants <em>Enumconstants</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumDeclarationImpl#getBodyDeclarations <em>Body Declarations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumDeclarationImpl extends ClassDeclarationImpl implements EnumDeclaration
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
   * The cached value of the '{@link #getImplementedTypes() <em>Implemented Types</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImplementedTypes()
   * @generated
   * @ordered
   */
  protected EList<Type> implementedTypes;

  /**
   * The cached value of the '{@link #getEnumconstants() <em>Enumconstants</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnumconstants()
   * @generated
   * @ordered
   */
  protected EnumConstants enumconstants;

  /**
   * The cached value of the '{@link #getBodyDeclarations() <em>Body Declarations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBodyDeclarations()
   * @generated
   * @ordered
   */
  protected EnumBodyDeclarations bodyDeclarations;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnumDeclarationImpl()
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
    return JMLPackage.Literals.ENUM_DECLARATION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_DECLARATION__IDENTIFIER, oldIdentifier, identifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Type> getImplementedTypes()
  {
    if (implementedTypes == null)
    {
      implementedTypes = new EObjectContainmentEList<Type>(Type.class, this, JMLPackage.ENUM_DECLARATION__IMPLEMENTED_TYPES);
    }
    return implementedTypes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumConstants getEnumconstants()
  {
    return enumconstants;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetEnumconstants(EnumConstants newEnumconstants, NotificationChain msgs)
  {
    EnumConstants oldEnumconstants = enumconstants;
    enumconstants = newEnumconstants;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS, oldEnumconstants, newEnumconstants);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnumconstants(EnumConstants newEnumconstants)
  {
    if (newEnumconstants != enumconstants)
    {
      NotificationChain msgs = null;
      if (enumconstants != null)
        msgs = ((InternalEObject)enumconstants).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS, null, msgs);
      if (newEnumconstants != null)
        msgs = ((InternalEObject)newEnumconstants).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS, null, msgs);
      msgs = basicSetEnumconstants(newEnumconstants, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS, newEnumconstants, newEnumconstants));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumBodyDeclarations getBodyDeclarations()
  {
    return bodyDeclarations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBodyDeclarations(EnumBodyDeclarations newBodyDeclarations, NotificationChain msgs)
  {
    EnumBodyDeclarations oldBodyDeclarations = bodyDeclarations;
    bodyDeclarations = newBodyDeclarations;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS, oldBodyDeclarations, newBodyDeclarations);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBodyDeclarations(EnumBodyDeclarations newBodyDeclarations)
  {
    if (newBodyDeclarations != bodyDeclarations)
    {
      NotificationChain msgs = null;
      if (bodyDeclarations != null)
        msgs = ((InternalEObject)bodyDeclarations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS, null, msgs);
      if (newBodyDeclarations != null)
        msgs = ((InternalEObject)newBodyDeclarations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS, null, msgs);
      msgs = basicSetBodyDeclarations(newBodyDeclarations, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS, newBodyDeclarations, newBodyDeclarations));
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
      case JMLPackage.ENUM_DECLARATION__IMPLEMENTED_TYPES:
        return ((InternalEList<?>)getImplementedTypes()).basicRemove(otherEnd, msgs);
      case JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS:
        return basicSetEnumconstants(null, msgs);
      case JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS:
        return basicSetBodyDeclarations(null, msgs);
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
      case JMLPackage.ENUM_DECLARATION__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.ENUM_DECLARATION__IMPLEMENTED_TYPES:
        return getImplementedTypes();
      case JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS:
        return getEnumconstants();
      case JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS:
        return getBodyDeclarations();
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
      case JMLPackage.ENUM_DECLARATION__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.ENUM_DECLARATION__IMPLEMENTED_TYPES:
        getImplementedTypes().clear();
        getImplementedTypes().addAll((Collection<? extends Type>)newValue);
        return;
      case JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS:
        setEnumconstants((EnumConstants)newValue);
        return;
      case JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS:
        setBodyDeclarations((EnumBodyDeclarations)newValue);
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
      case JMLPackage.ENUM_DECLARATION__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.ENUM_DECLARATION__IMPLEMENTED_TYPES:
        getImplementedTypes().clear();
        return;
      case JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS:
        setEnumconstants((EnumConstants)null);
        return;
      case JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS:
        setBodyDeclarations((EnumBodyDeclarations)null);
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
      case JMLPackage.ENUM_DECLARATION__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.ENUM_DECLARATION__IMPLEMENTED_TYPES:
        return implementedTypes != null && !implementedTypes.isEmpty();
      case JMLPackage.ENUM_DECLARATION__ENUMCONSTANTS:
        return enumconstants != null;
      case JMLPackage.ENUM_DECLARATION__BODY_DECLARATIONS:
        return bodyDeclarations != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == AnnotationTypeElementRest.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == IdentifierHaving.class)
    {
      switch (derivedFeatureID)
      {
        case JMLPackage.ENUM_DECLARATION__IDENTIFIER: return JMLPackage.IDENTIFIER_HAVING__IDENTIFIER;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == AnnotationTypeElementRest.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == IdentifierHaving.class)
    {
      switch (baseFeatureID)
      {
        case JMLPackage.IDENTIFIER_HAVING__IDENTIFIER: return JMLPackage.ENUM_DECLARATION__IDENTIFIER;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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

} //EnumDeclarationImpl

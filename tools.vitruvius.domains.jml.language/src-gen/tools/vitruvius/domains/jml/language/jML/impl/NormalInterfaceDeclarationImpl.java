/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.TypeParameters;

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
 * An implementation of the model object '<em><b>Normal Interface Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl#getTypeparameters <em>Typeparameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl#getImplementedTypes <em>Implemented Types</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.NormalInterfaceDeclarationImpl#getBodyDeclarations <em>Body Declarations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NormalInterfaceDeclarationImpl extends InterfaceDeclarationImpl implements NormalInterfaceDeclaration
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
   * The cached value of the '{@link #getTypeparameters() <em>Typeparameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeparameters()
   * @generated
   * @ordered
   */
  protected TypeParameters typeparameters;

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
   * The cached value of the '{@link #getBodyDeclarations() <em>Body Declarations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBodyDeclarations()
   * @generated
   * @ordered
   */
  protected EList<ClassBodyDeclaration> bodyDeclarations;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NormalInterfaceDeclarationImpl()
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
    return JMLPackage.Literals.NORMAL_INTERFACE_DECLARATION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.NORMAL_INTERFACE_DECLARATION__IDENTIFIER, oldIdentifier, identifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeParameters getTypeparameters()
  {
    return typeparameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTypeparameters(TypeParameters newTypeparameters, NotificationChain msgs)
  {
    TypeParameters oldTypeparameters = typeparameters;
    typeparameters = newTypeparameters;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS, oldTypeparameters, newTypeparameters);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTypeparameters(TypeParameters newTypeparameters)
  {
    if (newTypeparameters != typeparameters)
    {
      NotificationChain msgs = null;
      if (typeparameters != null)
        msgs = ((InternalEObject)typeparameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS, null, msgs);
      if (newTypeparameters != null)
        msgs = ((InternalEObject)newTypeparameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS, null, msgs);
      msgs = basicSetTypeparameters(newTypeparameters, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS, newTypeparameters, newTypeparameters));
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
      implementedTypes = new EObjectContainmentEList<Type>(Type.class, this, JMLPackage.NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES);
    }
    return implementedTypes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ClassBodyDeclaration> getBodyDeclarations()
  {
    if (bodyDeclarations == null)
    {
      bodyDeclarations = new EObjectContainmentEList<ClassBodyDeclaration>(ClassBodyDeclaration.class, this, JMLPackage.NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS);
    }
    return bodyDeclarations;
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
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS:
        return basicSetTypeparameters(null, msgs);
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES:
        return ((InternalEList<?>)getImplementedTypes()).basicRemove(otherEnd, msgs);
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS:
        return ((InternalEList<?>)getBodyDeclarations()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS:
        return getTypeparameters();
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES:
        return getImplementedTypes();
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS:
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
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS:
        setTypeparameters((TypeParameters)newValue);
        return;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES:
        getImplementedTypes().clear();
        getImplementedTypes().addAll((Collection<? extends Type>)newValue);
        return;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS:
        getBodyDeclarations().clear();
        getBodyDeclarations().addAll((Collection<? extends ClassBodyDeclaration>)newValue);
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
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS:
        setTypeparameters((TypeParameters)null);
        return;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES:
        getImplementedTypes().clear();
        return;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS:
        getBodyDeclarations().clear();
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
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__TYPEPARAMETERS:
        return typeparameters != null;
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__IMPLEMENTED_TYPES:
        return implementedTypes != null && !implementedTypes.isEmpty();
      case JMLPackage.NORMAL_INTERFACE_DECLARATION__BODY_DECLARATIONS:
        return bodyDeclarations != null && !bodyDeclarations.isEmpty();
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

} //NormalInterfaceDeclarationImpl

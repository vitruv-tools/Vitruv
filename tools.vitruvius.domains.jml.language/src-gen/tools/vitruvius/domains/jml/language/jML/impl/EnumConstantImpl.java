/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Annotations;
import tools.vitruvius.domains.jml.language.jML.Arguments;
import tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration;
import tools.vitruvius.domains.jml.language.jML.EnumConstant;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Constant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantImpl#getClassbodydeclaration <em>Classbodydeclaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumConstantImpl extends MinimalEObjectImpl.Container implements EnumConstant
{
  /**
   * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotations()
   * @generated
   * @ordered
   */
  protected Annotations annotations;

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
   * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArguments()
   * @generated
   * @ordered
   */
  protected Arguments arguments;

  /**
   * The cached value of the '{@link #getClassbodydeclaration() <em>Classbodydeclaration</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClassbodydeclaration()
   * @generated
   * @ordered
   */
  protected EList<ClassBodyDeclaration> classbodydeclaration;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnumConstantImpl()
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
    return JMLPackage.Literals.ENUM_CONSTANT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Annotations getAnnotations()
  {
    return annotations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAnnotations(Annotations newAnnotations, NotificationChain msgs)
  {
    Annotations oldAnnotations = annotations;
    annotations = newAnnotations;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_CONSTANT__ANNOTATIONS, oldAnnotations, newAnnotations);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAnnotations(Annotations newAnnotations)
  {
    if (newAnnotations != annotations)
    {
      NotificationChain msgs = null;
      if (annotations != null)
        msgs = ((InternalEObject)annotations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_CONSTANT__ANNOTATIONS, null, msgs);
      if (newAnnotations != null)
        msgs = ((InternalEObject)newAnnotations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_CONSTANT__ANNOTATIONS, null, msgs);
      msgs = basicSetAnnotations(newAnnotations, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_CONSTANT__ANNOTATIONS, newAnnotations, newAnnotations));
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_CONSTANT__IDENTIFIER, oldIdentifier, identifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Arguments getArguments()
  {
    return arguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetArguments(Arguments newArguments, NotificationChain msgs)
  {
    Arguments oldArguments = arguments;
    arguments = newArguments;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_CONSTANT__ARGUMENTS, oldArguments, newArguments);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setArguments(Arguments newArguments)
  {
    if (newArguments != arguments)
    {
      NotificationChain msgs = null;
      if (arguments != null)
        msgs = ((InternalEObject)arguments).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_CONSTANT__ARGUMENTS, null, msgs);
      if (newArguments != null)
        msgs = ((InternalEObject)newArguments).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ENUM_CONSTANT__ARGUMENTS, null, msgs);
      msgs = basicSetArguments(newArguments, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ENUM_CONSTANT__ARGUMENTS, newArguments, newArguments));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ClassBodyDeclaration> getClassbodydeclaration()
  {
    if (classbodydeclaration == null)
    {
      classbodydeclaration = new EObjectContainmentEList<ClassBodyDeclaration>(ClassBodyDeclaration.class, this, JMLPackage.ENUM_CONSTANT__CLASSBODYDECLARATION);
    }
    return classbodydeclaration;
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
      case JMLPackage.ENUM_CONSTANT__ANNOTATIONS:
        return basicSetAnnotations(null, msgs);
      case JMLPackage.ENUM_CONSTANT__ARGUMENTS:
        return basicSetArguments(null, msgs);
      case JMLPackage.ENUM_CONSTANT__CLASSBODYDECLARATION:
        return ((InternalEList<?>)getClassbodydeclaration()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.ENUM_CONSTANT__ANNOTATIONS:
        return getAnnotations();
      case JMLPackage.ENUM_CONSTANT__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.ENUM_CONSTANT__ARGUMENTS:
        return getArguments();
      case JMLPackage.ENUM_CONSTANT__CLASSBODYDECLARATION:
        return getClassbodydeclaration();
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
      case JMLPackage.ENUM_CONSTANT__ANNOTATIONS:
        setAnnotations((Annotations)newValue);
        return;
      case JMLPackage.ENUM_CONSTANT__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.ENUM_CONSTANT__ARGUMENTS:
        setArguments((Arguments)newValue);
        return;
      case JMLPackage.ENUM_CONSTANT__CLASSBODYDECLARATION:
        getClassbodydeclaration().clear();
        getClassbodydeclaration().addAll((Collection<? extends ClassBodyDeclaration>)newValue);
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
      case JMLPackage.ENUM_CONSTANT__ANNOTATIONS:
        setAnnotations((Annotations)null);
        return;
      case JMLPackage.ENUM_CONSTANT__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.ENUM_CONSTANT__ARGUMENTS:
        setArguments((Arguments)null);
        return;
      case JMLPackage.ENUM_CONSTANT__CLASSBODYDECLARATION:
        getClassbodydeclaration().clear();
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
      case JMLPackage.ENUM_CONSTANT__ANNOTATIONS:
        return annotations != null;
      case JMLPackage.ENUM_CONSTANT__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.ENUM_CONSTANT__ARGUMENTS:
        return arguments != null;
      case JMLPackage.ENUM_CONSTANT__CLASSBODYDECLARATION:
        return classbodydeclaration != null && !classbodydeclaration.isEmpty();
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

} //EnumConstantImpl

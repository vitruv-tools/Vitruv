/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.DeclaredException;
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.MethodBody;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.TypeParameter;
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
 * An implementation of the model object '<em><b>Type Parameters</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl#getTypeparameter <em>Typeparameter</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl#getExceptions <em>Exceptions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeParametersImpl#getMethodbody <em>Methodbody</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeParametersImpl extends GenericMethodOrConstructorDeclOldImpl implements TypeParameters
{
  /**
   * The cached value of the '{@link #getTypeparameter() <em>Typeparameter</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeparameter()
   * @generated
   * @ordered
   */
  protected EList<TypeParameter> typeparameter;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected Type type;

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
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<FormalParameterDecl> parameters;

  /**
   * The cached value of the '{@link #getExceptions() <em>Exceptions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExceptions()
   * @generated
   * @ordered
   */
  protected EList<DeclaredException> exceptions;

  /**
   * The cached value of the '{@link #getMethodbody() <em>Methodbody</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMethodbody()
   * @generated
   * @ordered
   */
  protected MethodBody methodbody;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TypeParametersImpl()
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
    return JMLPackage.Literals.TYPE_PARAMETERS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TypeParameter> getTypeparameter()
  {
    if (typeparameter == null)
    {
      typeparameter = new EObjectContainmentEList<TypeParameter>(TypeParameter.class, this, JMLPackage.TYPE_PARAMETERS__TYPEPARAMETER);
    }
    return typeparameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type getType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetType(Type newType, NotificationChain msgs)
  {
    Type oldType = type;
    type = newType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_PARAMETERS__TYPE, oldType, newType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(Type newType)
  {
    if (newType != type)
    {
      NotificationChain msgs = null;
      if (type != null)
        msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TYPE_PARAMETERS__TYPE, null, msgs);
      if (newType != null)
        msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TYPE_PARAMETERS__TYPE, null, msgs);
      msgs = basicSetType(newType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_PARAMETERS__TYPE, newType, newType));
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_PARAMETERS__IDENTIFIER, oldIdentifier, identifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FormalParameterDecl> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<FormalParameterDecl>(FormalParameterDecl.class, this, JMLPackage.TYPE_PARAMETERS__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<DeclaredException> getExceptions()
  {
    if (exceptions == null)
    {
      exceptions = new EObjectContainmentEList<DeclaredException>(DeclaredException.class, this, JMLPackage.TYPE_PARAMETERS__EXCEPTIONS);
    }
    return exceptions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MethodBody getMethodbody()
  {
    return methodbody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMethodbody(MethodBody newMethodbody, NotificationChain msgs)
  {
    MethodBody oldMethodbody = methodbody;
    methodbody = newMethodbody;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_PARAMETERS__METHODBODY, oldMethodbody, newMethodbody);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMethodbody(MethodBody newMethodbody)
  {
    if (newMethodbody != methodbody)
    {
      NotificationChain msgs = null;
      if (methodbody != null)
        msgs = ((InternalEObject)methodbody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TYPE_PARAMETERS__METHODBODY, null, msgs);
      if (newMethodbody != null)
        msgs = ((InternalEObject)newMethodbody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TYPE_PARAMETERS__METHODBODY, null, msgs);
      msgs = basicSetMethodbody(newMethodbody, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_PARAMETERS__METHODBODY, newMethodbody, newMethodbody));
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
      case JMLPackage.TYPE_PARAMETERS__TYPEPARAMETER:
        return ((InternalEList<?>)getTypeparameter()).basicRemove(otherEnd, msgs);
      case JMLPackage.TYPE_PARAMETERS__TYPE:
        return basicSetType(null, msgs);
      case JMLPackage.TYPE_PARAMETERS__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case JMLPackage.TYPE_PARAMETERS__EXCEPTIONS:
        return ((InternalEList<?>)getExceptions()).basicRemove(otherEnd, msgs);
      case JMLPackage.TYPE_PARAMETERS__METHODBODY:
        return basicSetMethodbody(null, msgs);
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
      case JMLPackage.TYPE_PARAMETERS__TYPEPARAMETER:
        return getTypeparameter();
      case JMLPackage.TYPE_PARAMETERS__TYPE:
        return getType();
      case JMLPackage.TYPE_PARAMETERS__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.TYPE_PARAMETERS__PARAMETERS:
        return getParameters();
      case JMLPackage.TYPE_PARAMETERS__EXCEPTIONS:
        return getExceptions();
      case JMLPackage.TYPE_PARAMETERS__METHODBODY:
        return getMethodbody();
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
      case JMLPackage.TYPE_PARAMETERS__TYPEPARAMETER:
        getTypeparameter().clear();
        getTypeparameter().addAll((Collection<? extends TypeParameter>)newValue);
        return;
      case JMLPackage.TYPE_PARAMETERS__TYPE:
        setType((Type)newValue);
        return;
      case JMLPackage.TYPE_PARAMETERS__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.TYPE_PARAMETERS__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends FormalParameterDecl>)newValue);
        return;
      case JMLPackage.TYPE_PARAMETERS__EXCEPTIONS:
        getExceptions().clear();
        getExceptions().addAll((Collection<? extends DeclaredException>)newValue);
        return;
      case JMLPackage.TYPE_PARAMETERS__METHODBODY:
        setMethodbody((MethodBody)newValue);
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
      case JMLPackage.TYPE_PARAMETERS__TYPEPARAMETER:
        getTypeparameter().clear();
        return;
      case JMLPackage.TYPE_PARAMETERS__TYPE:
        setType((Type)null);
        return;
      case JMLPackage.TYPE_PARAMETERS__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.TYPE_PARAMETERS__PARAMETERS:
        getParameters().clear();
        return;
      case JMLPackage.TYPE_PARAMETERS__EXCEPTIONS:
        getExceptions().clear();
        return;
      case JMLPackage.TYPE_PARAMETERS__METHODBODY:
        setMethodbody((MethodBody)null);
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
      case JMLPackage.TYPE_PARAMETERS__TYPEPARAMETER:
        return typeparameter != null && !typeparameter.isEmpty();
      case JMLPackage.TYPE_PARAMETERS__TYPE:
        return type != null;
      case JMLPackage.TYPE_PARAMETERS__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.TYPE_PARAMETERS__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case JMLPackage.TYPE_PARAMETERS__EXCEPTIONS:
        return exceptions != null && !exceptions.isEmpty();
      case JMLPackage.TYPE_PARAMETERS__METHODBODY:
        return methodbody != null;
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

} //TypeParametersImpl

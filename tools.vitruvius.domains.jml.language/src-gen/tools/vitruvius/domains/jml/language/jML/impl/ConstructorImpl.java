/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Constructor;
import tools.vitruvius.domains.jml.language.jML.ConstructorBody;
import tools.vitruvius.domains.jml.language.jML.DeclaredException;
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl;
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
 * An implementation of the model object '<em><b>Constructor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl#getExceptions <em>Exceptions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorImpl#getConstructorbody <em>Constructorbody</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstructorImpl extends MemberDeclImpl implements Constructor
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
   * The cached value of the '{@link #getConstructorbody() <em>Constructorbody</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstructorbody()
   * @generated
   * @ordered
   */
  protected ConstructorBody constructorbody;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConstructorImpl()
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
    return JMLPackage.Literals.CONSTRUCTOR;
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CONSTRUCTOR__IDENTIFIER, oldIdentifier, identifier));
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
      parameters = new EObjectContainmentEList<FormalParameterDecl>(FormalParameterDecl.class, this, JMLPackage.CONSTRUCTOR__PARAMETERS);
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
      exceptions = new EObjectContainmentEList<DeclaredException>(DeclaredException.class, this, JMLPackage.CONSTRUCTOR__EXCEPTIONS);
    }
    return exceptions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConstructorBody getConstructorbody()
  {
    return constructorbody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstructorbody(ConstructorBody newConstructorbody, NotificationChain msgs)
  {
    ConstructorBody oldConstructorbody = constructorbody;
    constructorbody = newConstructorbody;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY, oldConstructorbody, newConstructorbody);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstructorbody(ConstructorBody newConstructorbody)
  {
    if (newConstructorbody != constructorbody)
    {
      NotificationChain msgs = null;
      if (constructorbody != null)
        msgs = ((InternalEObject)constructorbody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY, null, msgs);
      if (newConstructorbody != null)
        msgs = ((InternalEObject)newConstructorbody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY, null, msgs);
      msgs = basicSetConstructorbody(newConstructorbody, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY, newConstructorbody, newConstructorbody));
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
      case JMLPackage.CONSTRUCTOR__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case JMLPackage.CONSTRUCTOR__EXCEPTIONS:
        return ((InternalEList<?>)getExceptions()).basicRemove(otherEnd, msgs);
      case JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY:
        return basicSetConstructorbody(null, msgs);
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
      case JMLPackage.CONSTRUCTOR__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.CONSTRUCTOR__PARAMETERS:
        return getParameters();
      case JMLPackage.CONSTRUCTOR__EXCEPTIONS:
        return getExceptions();
      case JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY:
        return getConstructorbody();
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
      case JMLPackage.CONSTRUCTOR__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.CONSTRUCTOR__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends FormalParameterDecl>)newValue);
        return;
      case JMLPackage.CONSTRUCTOR__EXCEPTIONS:
        getExceptions().clear();
        getExceptions().addAll((Collection<? extends DeclaredException>)newValue);
        return;
      case JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY:
        setConstructorbody((ConstructorBody)newValue);
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
      case JMLPackage.CONSTRUCTOR__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.CONSTRUCTOR__PARAMETERS:
        getParameters().clear();
        return;
      case JMLPackage.CONSTRUCTOR__EXCEPTIONS:
        getExceptions().clear();
        return;
      case JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY:
        setConstructorbody((ConstructorBody)null);
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
      case JMLPackage.CONSTRUCTOR__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.CONSTRUCTOR__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case JMLPackage.CONSTRUCTOR__EXCEPTIONS:
        return exceptions != null && !exceptions.isEmpty();
      case JMLPackage.CONSTRUCTOR__CONSTRUCTORBODY:
        return constructorbody != null;
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

} //ConstructorImpl

/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.DeclaredException;
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.MethodBody;
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration;

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
 * An implementation of the model object '<em><b>Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl#getExceptions <em>Exceptions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MethodDeclarationImpl#getMethodbody <em>Methodbody</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MethodDeclarationImpl extends IdentifierHavingImpl implements MethodDeclaration
{
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
  protected MethodDeclarationImpl()
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
    return JMLPackage.Literals.METHOD_DECLARATION;
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
      parameters = new EObjectContainmentEList<FormalParameterDecl>(FormalParameterDecl.class, this, JMLPackage.METHOD_DECLARATION__PARAMETERS);
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
      exceptions = new EObjectContainmentEList<DeclaredException>(DeclaredException.class, this, JMLPackage.METHOD_DECLARATION__EXCEPTIONS);
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.METHOD_DECLARATION__METHODBODY, oldMethodbody, newMethodbody);
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
        msgs = ((InternalEObject)methodbody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.METHOD_DECLARATION__METHODBODY, null, msgs);
      if (newMethodbody != null)
        msgs = ((InternalEObject)newMethodbody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.METHOD_DECLARATION__METHODBODY, null, msgs);
      msgs = basicSetMethodbody(newMethodbody, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.METHOD_DECLARATION__METHODBODY, newMethodbody, newMethodbody));
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
      case JMLPackage.METHOD_DECLARATION__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case JMLPackage.METHOD_DECLARATION__EXCEPTIONS:
        return ((InternalEList<?>)getExceptions()).basicRemove(otherEnd, msgs);
      case JMLPackage.METHOD_DECLARATION__METHODBODY:
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
      case JMLPackage.METHOD_DECLARATION__PARAMETERS:
        return getParameters();
      case JMLPackage.METHOD_DECLARATION__EXCEPTIONS:
        return getExceptions();
      case JMLPackage.METHOD_DECLARATION__METHODBODY:
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
      case JMLPackage.METHOD_DECLARATION__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends FormalParameterDecl>)newValue);
        return;
      case JMLPackage.METHOD_DECLARATION__EXCEPTIONS:
        getExceptions().clear();
        getExceptions().addAll((Collection<? extends DeclaredException>)newValue);
        return;
      case JMLPackage.METHOD_DECLARATION__METHODBODY:
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
      case JMLPackage.METHOD_DECLARATION__PARAMETERS:
        getParameters().clear();
        return;
      case JMLPackage.METHOD_DECLARATION__EXCEPTIONS:
        getExceptions().clear();
        return;
      case JMLPackage.METHOD_DECLARATION__METHODBODY:
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
      case JMLPackage.METHOD_DECLARATION__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case JMLPackage.METHOD_DECLARATION__EXCEPTIONS:
        return exceptions != null && !exceptions.isEmpty();
      case JMLPackage.METHOD_DECLARATION__METHODBODY:
        return methodbody != null;
    }
    return super.eIsSet(featureID);
  }

} //MethodDeclarationImpl

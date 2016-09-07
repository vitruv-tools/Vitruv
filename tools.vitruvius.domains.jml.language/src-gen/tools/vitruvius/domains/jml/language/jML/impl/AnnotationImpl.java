/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Annotation;
import tools.vitruvius.domains.jml.language.jML.ElementValue;
import tools.vitruvius.domains.jml.language.jML.ElementValuePairs;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl#getAnnotationname <em>Annotationname</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl#getElementvaluepairs <em>Elementvaluepairs</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationImpl#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationImpl extends ModifierImpl implements Annotation
{
  /**
   * The default value of the '{@link #getAnnotationname() <em>Annotationname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotationname()
   * @generated
   * @ordered
   */
  protected static final String ANNOTATIONNAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAnnotationname() <em>Annotationname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnotationname()
   * @generated
   * @ordered
   */
  protected String annotationname = ANNOTATIONNAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getElementvaluepairs() <em>Elementvaluepairs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElementvaluepairs()
   * @generated
   * @ordered
   */
  protected ElementValuePairs elementvaluepairs;

  /**
   * The cached value of the '{@link #getElementvalue() <em>Elementvalue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElementvalue()
   * @generated
   * @ordered
   */
  protected ElementValue elementvalue;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AnnotationImpl()
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
    return JMLPackage.Literals.ANNOTATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAnnotationname()
  {
    return annotationname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAnnotationname(String newAnnotationname)
  {
    String oldAnnotationname = annotationname;
    annotationname = newAnnotationname;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION__ANNOTATIONNAME, oldAnnotationname, annotationname));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValuePairs getElementvaluepairs()
  {
    return elementvaluepairs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElementvaluepairs(ElementValuePairs newElementvaluepairs, NotificationChain msgs)
  {
    ElementValuePairs oldElementvaluepairs = elementvaluepairs;
    elementvaluepairs = newElementvaluepairs;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS, oldElementvaluepairs, newElementvaluepairs);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElementvaluepairs(ElementValuePairs newElementvaluepairs)
  {
    if (newElementvaluepairs != elementvaluepairs)
    {
      NotificationChain msgs = null;
      if (elementvaluepairs != null)
        msgs = ((InternalEObject)elementvaluepairs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS, null, msgs);
      if (newElementvaluepairs != null)
        msgs = ((InternalEObject)newElementvaluepairs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS, null, msgs);
      msgs = basicSetElementvaluepairs(newElementvaluepairs, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS, newElementvaluepairs, newElementvaluepairs));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValue getElementvalue()
  {
    return elementvalue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElementvalue(ElementValue newElementvalue, NotificationChain msgs)
  {
    ElementValue oldElementvalue = elementvalue;
    elementvalue = newElementvalue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION__ELEMENTVALUE, oldElementvalue, newElementvalue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElementvalue(ElementValue newElementvalue)
  {
    if (newElementvalue != elementvalue)
    {
      NotificationChain msgs = null;
      if (elementvalue != null)
        msgs = ((InternalEObject)elementvalue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION__ELEMENTVALUE, null, msgs);
      if (newElementvalue != null)
        msgs = ((InternalEObject)newElementvalue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION__ELEMENTVALUE, null, msgs);
      msgs = basicSetElementvalue(newElementvalue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION__ELEMENTVALUE, newElementvalue, newElementvalue));
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
      case JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS:
        return basicSetElementvaluepairs(null, msgs);
      case JMLPackage.ANNOTATION__ELEMENTVALUE:
        return basicSetElementvalue(null, msgs);
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
      case JMLPackage.ANNOTATION__ANNOTATIONNAME:
        return getAnnotationname();
      case JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS:
        return getElementvaluepairs();
      case JMLPackage.ANNOTATION__ELEMENTVALUE:
        return getElementvalue();
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
      case JMLPackage.ANNOTATION__ANNOTATIONNAME:
        setAnnotationname((String)newValue);
        return;
      case JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS:
        setElementvaluepairs((ElementValuePairs)newValue);
        return;
      case JMLPackage.ANNOTATION__ELEMENTVALUE:
        setElementvalue((ElementValue)newValue);
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
      case JMLPackage.ANNOTATION__ANNOTATIONNAME:
        setAnnotationname(ANNOTATIONNAME_EDEFAULT);
        return;
      case JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS:
        setElementvaluepairs((ElementValuePairs)null);
        return;
      case JMLPackage.ANNOTATION__ELEMENTVALUE:
        setElementvalue((ElementValue)null);
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
      case JMLPackage.ANNOTATION__ANNOTATIONNAME:
        return ANNOTATIONNAME_EDEFAULT == null ? annotationname != null : !ANNOTATIONNAME_EDEFAULT.equals(annotationname);
      case JMLPackage.ANNOTATION__ELEMENTVALUEPAIRS:
        return elementvaluepairs != null;
      case JMLPackage.ANNOTATION__ELEMENTVALUE:
        return elementvalue != null;
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
    result.append(" (annotationname: ");
    result.append(annotationname);
    result.append(')');
    return result.toString();
  }

} //AnnotationImpl

/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration;
import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classifier Declaration With Modifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ClassifierDeclarationWithModifierImpl#getClassOrInterfaceDeclaration <em>Class Or Interface Declaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassifierDeclarationWithModifierImpl extends ModifiableImpl implements ClassifierDeclarationWithModifier
{
  /**
   * The cached value of the '{@link #getClassOrInterfaceDeclaration() <em>Class Or Interface Declaration</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClassOrInterfaceDeclaration()
   * @generated
   * @ordered
   */
  protected ClassOrInterfaceDeclaration classOrInterfaceDeclaration;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ClassifierDeclarationWithModifierImpl()
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
    return JMLPackage.Literals.CLASSIFIER_DECLARATION_WITH_MODIFIER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassOrInterfaceDeclaration getClassOrInterfaceDeclaration()
  {
    return classOrInterfaceDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration newClassOrInterfaceDeclaration, NotificationChain msgs)
  {
    ClassOrInterfaceDeclaration oldClassOrInterfaceDeclaration = classOrInterfaceDeclaration;
    classOrInterfaceDeclaration = newClassOrInterfaceDeclaration;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION, oldClassOrInterfaceDeclaration, newClassOrInterfaceDeclaration);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration newClassOrInterfaceDeclaration)
  {
    if (newClassOrInterfaceDeclaration != classOrInterfaceDeclaration)
    {
      NotificationChain msgs = null;
      if (classOrInterfaceDeclaration != null)
        msgs = ((InternalEObject)classOrInterfaceDeclaration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION, null, msgs);
      if (newClassOrInterfaceDeclaration != null)
        msgs = ((InternalEObject)newClassOrInterfaceDeclaration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION, null, msgs);
      msgs = basicSetClassOrInterfaceDeclaration(newClassOrInterfaceDeclaration, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION, newClassOrInterfaceDeclaration, newClassOrInterfaceDeclaration));
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
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION:
        return basicSetClassOrInterfaceDeclaration(null, msgs);
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
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION:
        return getClassOrInterfaceDeclaration();
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
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION:
        setClassOrInterfaceDeclaration((ClassOrInterfaceDeclaration)newValue);
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
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION:
        setClassOrInterfaceDeclaration((ClassOrInterfaceDeclaration)null);
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
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER__CLASS_OR_INTERFACE_DECLARATION:
        return classOrInterfaceDeclaration != null;
    }
    return super.eIsSet(featureID);
  }

} //ClassifierDeclarationWithModifierImpl

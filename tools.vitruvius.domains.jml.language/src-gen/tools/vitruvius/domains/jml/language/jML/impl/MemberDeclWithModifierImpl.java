/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLMemberModifier;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.MemberDecl;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;

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
 * An implementation of the model object '<em><b>Member Decl With Modifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierImpl#getJmlModifiers <em>Jml Modifiers</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclWithModifierImpl#getMemberdecl <em>Memberdecl</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MemberDeclWithModifierImpl extends ModifiableImpl implements MemberDeclWithModifier
{
  /**
   * The cached value of the '{@link #getJmlModifiers() <em>Jml Modifiers</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJmlModifiers()
   * @generated
   * @ordered
   */
  protected EList<JMLMemberModifier> jmlModifiers;

  /**
   * The cached value of the '{@link #getMemberdecl() <em>Memberdecl</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMemberdecl()
   * @generated
   * @ordered
   */
  protected MemberDecl memberdecl;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MemberDeclWithModifierImpl()
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
    return JMLPackage.Literals.MEMBER_DECL_WITH_MODIFIER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<JMLMemberModifier> getJmlModifiers()
  {
    if (jmlModifiers == null)
    {
      jmlModifiers = new EObjectContainmentEList<JMLMemberModifier>(JMLMemberModifier.class, this, JMLPackage.MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS);
    }
    return jmlModifiers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDecl getMemberdecl()
  {
    return memberdecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMemberdecl(MemberDecl newMemberdecl, NotificationChain msgs)
  {
    MemberDecl oldMemberdecl = memberdecl;
    memberdecl = newMemberdecl;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL, oldMemberdecl, newMemberdecl);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMemberdecl(MemberDecl newMemberdecl)
  {
    if (newMemberdecl != memberdecl)
    {
      NotificationChain msgs = null;
      if (memberdecl != null)
        msgs = ((InternalEObject)memberdecl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL, null, msgs);
      if (newMemberdecl != null)
        msgs = ((InternalEObject)newMemberdecl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL, null, msgs);
      msgs = basicSetMemberdecl(newMemberdecl, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL, newMemberdecl, newMemberdecl));
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
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS:
        return ((InternalEList<?>)getJmlModifiers()).basicRemove(otherEnd, msgs);
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL:
        return basicSetMemberdecl(null, msgs);
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
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS:
        return getJmlModifiers();
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL:
        return getMemberdecl();
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
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS:
        getJmlModifiers().clear();
        getJmlModifiers().addAll((Collection<? extends JMLMemberModifier>)newValue);
        return;
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL:
        setMemberdecl((MemberDecl)newValue);
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
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS:
        getJmlModifiers().clear();
        return;
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL:
        setMemberdecl((MemberDecl)null);
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
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__JML_MODIFIERS:
        return jmlModifiers != null && !jmlModifiers.isEmpty();
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER__MEMBERDECL:
        return memberdecl != null;
    }
    return super.eIsSet(featureID);
  }

} //MemberDeclWithModifierImpl

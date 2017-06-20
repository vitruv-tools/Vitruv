/** 
 */
package tools.vitruv.framework.versioning.author.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature
import tools.vitruv.framework.versioning.author.Signed

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signed</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.SignedImpl#getSignature <em>Signature</em>}</li>
 * </ul>
 * @generated
 */
abstract class SignedImpl extends MinimalEObjectImpl.Container implements Signed {
	/** 
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected Signature signature

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return AuthorPackage.Literals.SIGNED
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Signature getSignature() {
		if (signature !== null && signature.eIsProxy()) {
			var InternalEObject oldSignature = (signature as InternalEObject)
			signature = eResolveProxy(oldSignature) as Signature
			if (signature !== oldSignature) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, AuthorPackage.SIGNED__SIGNATURE, oldSignature,
						signature))
			}
		}
		return signature
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Signature basicGetSignature() {
		return signature
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setSignature(Signature newSignature) {
		var Signature oldSignature = signature
		signature = newSignature
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, AuthorPackage.SIGNED__SIGNATURE, oldSignature, signature))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case AuthorPackage.SIGNED__SIGNATURE: {
				if (resolve) return getSignature()
				return basicGetSignature()
			}
		}
		return super.eGet(featureID, resolve, coreType)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eSet(int featureID, Object newValue) {

		switch (featureID) {
			case AuthorPackage.SIGNED__SIGNATURE: {
				setSignature((newValue as Signature))
				return
			}
		}
		super.eSet(featureID, newValue)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eUnset(int featureID) {

		switch (featureID) {
			case AuthorPackage.SIGNED__SIGNATURE: {
				setSignature((null as Signature))
				return
			}
		}
		super.eUnset(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override boolean eIsSet(int featureID) {

		switch (featureID) {
			case AuthorPackage.SIGNED__SIGNATURE: {
				return signature !== null
			}
		}
		return super.eIsSet(featureID)
	} // SignedImpl
}

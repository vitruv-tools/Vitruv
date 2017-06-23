/** 
 */
package tools.vitruv.framework.versioning.author.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.author.impl.SignatureImpl#getSigner <em>Signer</em>}</li>
 * </ul>
 * @generated
 */
class SignatureImpl extends MinimalEObjectImpl.Container implements Signature {
	/** 
	 * The cached value of the '{@link #getSigner() <em>Signer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSigner()
	 * @generated
	 * @ordered
	 */
	protected Author signer

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
		return AuthorPackage.Literals.SIGNATURE
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Author getSigner() {
		if (signer !== null && signer.eIsProxy()) {
			var InternalEObject oldSigner = (signer as InternalEObject)
			signer = eResolveProxy(oldSigner) as Author
			if (signer !== oldSigner) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, AuthorPackage.SIGNATURE__SIGNER, oldSigner,
						signer))
				}
			}
			return signer
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		def Author basicGetSigner() {
			return signer
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		override void setSigner(Author newSigner) {
			var Author oldSigner = signer
			signer = newSigner
			if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, AuthorPackage.SIGNATURE__SIGNER, oldSigner, signer))
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		override Object eGet(int featureID, boolean resolve, boolean coreType) {

			switch (featureID) {
				case AuthorPackage.SIGNATURE__SIGNER: {
					if (resolve) return getSigner()
					return basicGetSigner()
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
				case AuthorPackage.SIGNATURE__SIGNER: {
					setSigner((newValue as Author))
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
				case AuthorPackage.SIGNATURE__SIGNER: {
					setSigner((null as Author))
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
				case AuthorPackage.SIGNATURE__SIGNER: {
					return signer !== null
				}
			}
			return super.eIsSet(featureID)
		} // SignatureImpl
	}
	
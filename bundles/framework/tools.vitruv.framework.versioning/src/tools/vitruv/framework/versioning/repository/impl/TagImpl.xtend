/** 
 */
package tools.vitruv.framework.versioning.repository.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature
import tools.vitruv.framework.versioning.author.Signed
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.impl.NamedImpl
import tools.vitruv.framework.versioning.repository.RepositoryPackage
import tools.vitruv.framework.versioning.repository.Tag

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.repository.impl.TagImpl#getSignature <em>Signature</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.repository.impl.TagImpl#getCommit <em>Commit</em>}</li>
 * </ul>
 * @generated
 */
class TagImpl extends NamedImpl implements Tag {
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
	 * The cached value of the '{@link #getCommit() <em>Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommit()
	 * @generated
	 * @ordered
	 */
	protected Commit commit

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
		return RepositoryPackage.Literals.TAG
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
					new ENotificationImpl(this, Notification.RESOLVE, RepositoryPackage.TAG__SIGNATURE, oldSignature,
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
			new ENotificationImpl(this, Notification.SET, RepositoryPackage.TAG__SIGNATURE, oldSignature, signature))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Commit getCommit() {
		if (commit !== null && commit.eIsProxy()) {
			var InternalEObject oldCommit = (commit as InternalEObject)
			commit = eResolveProxy(oldCommit) as Commit
			if (commit !== oldCommit) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, RepositoryPackage.TAG__COMMIT, oldCommit, commit))
			}
		}
		return commit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Commit basicGetCommit() {
		return commit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setCommit(Commit newCommit) {
		var Commit oldCommit = commit
		commit = newCommit
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, RepositoryPackage.TAG__COMMIT, oldCommit, commit))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case RepositoryPackage.TAG__SIGNATURE: {
				if (resolve) return getSignature()
				return basicGetSignature()
			}
			case RepositoryPackage.TAG__COMMIT: {
				if (resolve) return getCommit()
				return basicGetCommit()
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
			case RepositoryPackage.TAG__SIGNATURE: {
				setSignature((newValue as Signature))
				return
			}
			case RepositoryPackage.TAG__COMMIT: {
				setCommit((newValue as Commit))
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
			case RepositoryPackage.TAG__SIGNATURE: {
				setSignature((null as Signature))
				return
			}
			case RepositoryPackage.TAG__COMMIT: {
				setCommit((null as Commit))
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
			case RepositoryPackage.TAG__SIGNATURE: {
				return signature !== null
			}
			case RepositoryPackage.TAG__COMMIT: {
				return commit !== null
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass === Signed) {

			switch (derivedFeatureID) {
				case RepositoryPackage.TAG__SIGNATURE: {
					return AuthorPackage.SIGNED__SIGNATURE
				}
				default: {
					return -1
				}
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass === Signed) {

			switch (baseFeatureID) {
				case AuthorPackage.SIGNED__SIGNATURE: {
					return RepositoryPackage.TAG__SIGNATURE
				}
				default: {
					return -1
				}
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass)
	} // TagImpl
}

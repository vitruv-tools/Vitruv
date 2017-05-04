/**
 */
package tools.vitruv.framework.versioning.repository.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.versioning.author.AuthorPackage;
import tools.vitruv.framework.versioning.author.Signature;
import tools.vitruv.framework.versioning.author.Signed;

import tools.vitruv.framework.versioning.commit.Commit;

import tools.vitruv.framework.versioning.impl.NamedImpl;

import tools.vitruv.framework.versioning.repository.RepositoryPackage;
import tools.vitruv.framework.versioning.repository.Tag;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.repository.impl.TagImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.repository.impl.TagImpl#getCommit <em>Commit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TagImpl extends NamedImpl implements Tag {
	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected Signature signature;

	/**
	 * The cached value of the '{@link #getCommit() <em>Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommit()
	 * @generated
	 * @ordered
	 */
	protected Commit commit;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TagImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryPackage.Literals.TAG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Signature getSignature() {
		if (signature != null && signature.eIsProxy()) {
			InternalEObject oldSignature = (InternalEObject)signature;
			signature = (Signature)eResolveProxy(oldSignature);
			if (signature != oldSignature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryPackage.TAG__SIGNATURE, oldSignature, signature));
			}
		}
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Signature basicGetSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(Signature newSignature) {
		Signature oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryPackage.TAG__SIGNATURE, oldSignature, signature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getCommit() {
		if (commit != null && commit.eIsProxy()) {
			InternalEObject oldCommit = (InternalEObject)commit;
			commit = (Commit)eResolveProxy(oldCommit);
			if (commit != oldCommit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryPackage.TAG__COMMIT, oldCommit, commit));
			}
		}
		return commit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit basicGetCommit() {
		return commit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommit(Commit newCommit) {
		Commit oldCommit = commit;
		commit = newCommit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryPackage.TAG__COMMIT, oldCommit, commit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryPackage.TAG__SIGNATURE:
				if (resolve) return getSignature();
				return basicGetSignature();
			case RepositoryPackage.TAG__COMMIT:
				if (resolve) return getCommit();
				return basicGetCommit();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RepositoryPackage.TAG__SIGNATURE:
				setSignature((Signature)newValue);
				return;
			case RepositoryPackage.TAG__COMMIT:
				setCommit((Commit)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case RepositoryPackage.TAG__SIGNATURE:
				setSignature((Signature)null);
				return;
			case RepositoryPackage.TAG__COMMIT:
				setCommit((Commit)null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RepositoryPackage.TAG__SIGNATURE:
				return signature != null;
			case RepositoryPackage.TAG__COMMIT:
				return commit != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Signed.class) {
			switch (derivedFeatureID) {
				case RepositoryPackage.TAG__SIGNATURE: return AuthorPackage.SIGNED__SIGNATURE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Signed.class) {
			switch (baseFeatureID) {
				case AuthorPackage.SIGNED__SIGNATURE: return RepositoryPackage.TAG__SIGNATURE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TagImpl

/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.SimpleCommit

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl#getParent <em>Parent</em>}</li>
 * </ul>
 * @generated
 */
class SimpleCommitImpl extends CommitImpl implements SimpleCommit {
	/** 
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Commit parent

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
		return CommitPackage.Literals.SIMPLE_COMMIT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Commit getParent() {
		if (parent !== null && parent.eIsProxy()) {
			var InternalEObject oldParent = (parent as InternalEObject)
			parent = eResolveProxy(oldParent) as Commit
			if (parent !== oldParent) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, CommitPackage.SIMPLE_COMMIT__PARENT, oldParent,
						parent))
			}
		}
		return parent
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Commit basicGetParent() {
		return parent
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def NotificationChain basicSetParent(Commit newParent, NotificationChain msgs_finalParam_) {
		var msgs = msgs_finalParam_
		var Commit oldParent = parent
		parent = newParent
		if (eNotificationRequired()) {
			var ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				CommitPackage.SIMPLE_COMMIT__PARENT, oldParent, newParent)
			if (msgs === null) msgs = notification else msgs.add(notification)
		}
		return msgs
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setParent(Commit newParent) {
		if (newParent !== parent) {
			var NotificationChain msgs = null
			if (parent !== null) msgs = ((parent as InternalEObject)).eInverseRemove(this,
				CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, Commit, msgs)
			if (newParent !== null) msgs = ((newParent as InternalEObject)).eInverseAdd(this,
				CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, Commit, msgs)
			msgs = basicSetParent(newParent, msgs)
			if (msgs !== null) msgs.^dispatch()
		} else if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, CommitPackage.SIMPLE_COMMIT__PARENT, newParent, newParent))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID,
		NotificationChain msgs_finalParam_) {
		var msgs = msgs_finalParam_

		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT: {
				if (parent !== null) msgs = ((parent as InternalEObject)).eInverseRemove(this,
					CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, Commit, msgs)
				return basicSetParent((otherEnd as Commit), msgs)
			}
		}
		return super.eInverseAdd(otherEnd, featureID, msgs)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {

		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT: {
				return basicSetParent(null, msgs)
			}
		}
		return super.eInverseRemove(otherEnd, featureID, msgs)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT: {
				if (resolve) return getParent()
				return basicGetParent()
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
			case CommitPackage.SIMPLE_COMMIT__PARENT: {
				setParent((newValue as Commit))
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
			case CommitPackage.SIMPLE_COMMIT__PARENT: {
				setParent((null as Commit))
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
			case CommitPackage.SIMPLE_COMMIT__PARENT: {
				return parent !== null
			}
		}
		return super.eIsSet(featureID)
	} // SimpleCommitImpl
}

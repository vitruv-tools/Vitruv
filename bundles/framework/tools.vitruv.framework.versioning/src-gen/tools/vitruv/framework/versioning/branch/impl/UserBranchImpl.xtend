/** 
 */
package tools.vitruv.framework.versioning.branch.impl

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.UserBranch

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.UserBranchImpl#getOwner <em>Owner</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.UserBranchImpl#getBranchedFrom <em>Branched From</em>}</li>
 * </ul>
 * @generated
 */
class UserBranchImpl extends BranchImpl implements UserBranch {
	/** 
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected Author owner
	/** 
	 * The cached value of the '{@link #getBranchedFrom() <em>Branched From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranchedFrom()
	 * @generated
	 * @ordered
	 */
	protected Branch branchedFrom

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
		return BranchPackage.Literals.USER_BRANCH
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Author getOwner() {
		if (owner !== null && owner.eIsProxy()) {
			var InternalEObject oldOwner = (owner as InternalEObject)
			owner = eResolveProxy(oldOwner) as Author
			if (owner !== oldOwner) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.USER_BRANCH__OWNER, oldOwner,
						owner))
				}
			}
			return owner
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		def Author basicGetOwner() {
			return owner
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		def NotificationChain basicSetOwner(Author newOwner, NotificationChain msgs_finalParam_) {
			var msgs = msgs_finalParam_
			var Author oldOwner = owner
			owner = newOwner
			if (eNotificationRequired()) {
				var ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BranchPackage.USER_BRANCH__OWNER, oldOwner, newOwner)
				if (msgs === null) msgs = notification else msgs.add(notification)
			}
			return msgs
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		override void setOwner(Author newOwner) {
			if (newOwner !== owner) {
				var NotificationChain msgs = null
				if (owner !== null) msgs = ((owner as InternalEObject)).eInverseRemove(this,
					AuthorPackage.AUTHOR__OWNED_BRANCHES, Author, msgs)
				if (newOwner !== null) msgs = ((newOwner as InternalEObject)).eInverseAdd(this,
					AuthorPackage.AUTHOR__OWNED_BRANCHES, Author, msgs)
				msgs = basicSetOwner(newOwner, msgs)
				if (msgs !== null) msgs.^dispatch()
			} else if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, BranchPackage.USER_BRANCH__OWNER, newOwner, newOwner))
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		override Branch getBranchedFrom() {
			if (branchedFrom !== null && branchedFrom.eIsProxy()) {
				var InternalEObject oldBranchedFrom = (branchedFrom as InternalEObject)
				branchedFrom = eResolveProxy(oldBranchedFrom) as Branch
				if (branchedFrom !== oldBranchedFrom) {
					if (eNotificationRequired()) eNotify(
						new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.USER_BRANCH__BRANCHED_FROM,
							oldBranchedFrom, branchedFrom))
				}
			}
			return branchedFrom
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		def Branch basicGetBranchedFrom() {
			return branchedFrom
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		def NotificationChain basicSetBranchedFrom(Branch newBranchedFrom, NotificationChain msgs_finalParam_) {
			var msgs = msgs_finalParam_
			var Branch oldBranchedFrom = branchedFrom
			branchedFrom = newBranchedFrom
			if (eNotificationRequired()) {
				var ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					BranchPackage.USER_BRANCH__BRANCHED_FROM, oldBranchedFrom, newBranchedFrom)
				if (msgs === null) msgs = notification else msgs.add(notification)
			}
			return msgs
		}

		/** 
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		override void setBranchedFrom(Branch newBranchedFrom) {
			if (newBranchedFrom !== branchedFrom) {
				var NotificationChain msgs = null
				if (branchedFrom !== null) msgs = ((branchedFrom as InternalEObject)).eInverseRemove(this,
					BranchPackage.BRANCH__CHILD_BRANCHES, Branch, msgs)
				if (newBranchedFrom !== null) msgs = ((newBranchedFrom as InternalEObject)).eInverseAdd(this,
					BranchPackage.BRANCH__CHILD_BRANCHES, Branch, msgs)
				msgs = basicSetBranchedFrom(newBranchedFrom, msgs)
				if (msgs !== null) msgs.^dispatch()
			} else if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, BranchPackage.USER_BRANCH__BRANCHED_FROM, newBranchedFrom,
					newBranchedFrom))
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
				case BranchPackage.USER_BRANCH__OWNER: {
					if (owner !== null) msgs = ((owner as InternalEObject)).eInverseRemove(this,
						AuthorPackage.AUTHOR__OWNED_BRANCHES, Author, msgs)
					return basicSetOwner((otherEnd as Author), msgs)
				}
				case BranchPackage.USER_BRANCH__BRANCHED_FROM: {
					if (branchedFrom !== null) msgs = ((branchedFrom as InternalEObject)).eInverseRemove(this,
						BranchPackage.BRANCH__CHILD_BRANCHES, Branch, msgs)
					return basicSetBranchedFrom((otherEnd as Branch), msgs)
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
				case BranchPackage.USER_BRANCH__OWNER: {
					return basicSetOwner(null, msgs)
				}
				case BranchPackage.USER_BRANCH__BRANCHED_FROM: {
					return basicSetBranchedFrom(null, msgs)
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
				case BranchPackage.USER_BRANCH__OWNER: {
					if (resolve) return getOwner()
					return basicGetOwner()
				}
				case BranchPackage.USER_BRANCH__BRANCHED_FROM: {
					if (resolve) return getBranchedFrom()
					return basicGetBranchedFrom()
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
				case BranchPackage.USER_BRANCH__OWNER: {
					setOwner((newValue as Author))
					return
				}
				case BranchPackage.USER_BRANCH__BRANCHED_FROM: {
					setBranchedFrom((newValue as Branch))
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
				case BranchPackage.USER_BRANCH__OWNER: {
					setOwner((null as Author))
					return
				}
				case BranchPackage.USER_BRANCH__BRANCHED_FROM: {
					setBranchedFrom((null as Branch))
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
				case BranchPackage.USER_BRANCH__OWNER: {
					return owner !== null
				}
				case BranchPackage.USER_BRANCH__BRANCHED_FROM: {
					return branchedFrom !== null
				}
			}
			return super.eIsSet(featureID)
		} // UserBranchImpl
	}
	
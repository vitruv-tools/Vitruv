/** 
 */
package tools.vitruv.framework.versioning.branch.impl

import java.lang.reflect.InvocationTargetException
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.branch.BranchDiffCreator
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.UserBranch

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff Creator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffCreatorImpl#getSource <em>Source</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.branch.impl.BranchDiffCreatorImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * @generated
 */
class BranchDiffCreatorImpl extends MinimalEObjectImpl.Container implements BranchDiffCreator {
	/** 
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected UserBranch source
	/** 
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected UserBranch target

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
		return BranchPackage.Literals.BRANCH_DIFF_CREATOR
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override UserBranch getSource() {
		if (source !== null && source.eIsProxy()) {
			var InternalEObject oldSource = (source as InternalEObject)
			source = eResolveProxy(oldSource) as UserBranch
			if (source !== oldSource) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF_CREATOR__SOURCE,
						oldSource, source))
			}
		}
		return source
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def UserBranch basicGetSource() {
		return source
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setSource(UserBranch newSource) {
		var UserBranch oldSource = source
		source = newSource
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF_CREATOR__SOURCE, oldSource, source))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override UserBranch getTarget() {
		if (target !== null && target.eIsProxy()) {
			var InternalEObject oldTarget = (target as InternalEObject)
			target = eResolveProxy(oldTarget) as UserBranch
			if (target !== oldTarget) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, BranchPackage.BRANCH_DIFF_CREATOR__TARGET,
						oldTarget, target))
			}
		}
		return target
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def UserBranch basicGetTarget() {
		return target
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setTarget(UserBranch newTarget) {
		var UserBranch oldTarget = target
		target = newTarget
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, BranchPackage.BRANCH_DIFF_CREATOR__TARGET, oldTarget, target))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override BranchDiff createVersionDiff() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case BranchPackage.BRANCH_DIFF_CREATOR__SOURCE: {
				if (resolve) return getSource()
				return basicGetSource()
			}
			case BranchPackage.BRANCH_DIFF_CREATOR__TARGET: {
				if (resolve) return getTarget()
				return basicGetTarget()
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
			case BranchPackage.BRANCH_DIFF_CREATOR__SOURCE: {
				setSource((newValue as UserBranch))
				return
			}
			case BranchPackage.BRANCH_DIFF_CREATOR__TARGET: {
				setTarget((newValue as UserBranch))
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
			case BranchPackage.BRANCH_DIFF_CREATOR__SOURCE: {
				setSource((null as UserBranch))
				return
			}
			case BranchPackage.BRANCH_DIFF_CREATOR__TARGET: {
				setTarget((null as UserBranch))
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
			case BranchPackage.BRANCH_DIFF_CREATOR__SOURCE: {
				return source !== null
			}
			case BranchPackage.BRANCH_DIFF_CREATOR__TARGET: {
				return target !== null
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {

		switch (operationID) {
			case BranchPackage.BRANCH_DIFF_CREATOR___CREATE_VERSION_DIFF: {
				return createVersionDiff()
			}
		}
		return super.eInvoke(operationID, arguments)
	} // BranchDiffCreatorImpl
}

/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import java.util.Collection
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.util.EObjectResolvingEList
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.MultiChangeConflict

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.MultiChangeConflictImpl#getSourceChanges <em>Source Changes</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.MultiChangeConflictImpl#getTargetChanges <em>Target Changes</em>}</li>
 * </ul>
 * @generated
 */
class MultiChangeConflictImpl extends ConflictImpl implements MultiChangeConflict {
	/** 
	 * The cached value of the '{@link #getSourceChanges() <em>Source Changes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<ChangeMatch> sourceChanges
	/** 
	 * The cached value of the '{@link #getTargetChanges() <em>Target Changes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<ChangeMatch> targetChanges

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
		return ConflictPackage.Literals.MULTI_CHANGE_CONFLICT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<ChangeMatch> getSourceChanges() {
		if (sourceChanges === null) {
			sourceChanges = new EObjectResolvingEList<ChangeMatch>(ChangeMatch, this,
				ConflictPackage.MULTI_CHANGE_CONFLICT__SOURCE_CHANGES)
		}
		return sourceChanges
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<ChangeMatch> getTargetChanges() {
		if (targetChanges === null) {
			targetChanges = new EObjectResolvingEList<ChangeMatch>(ChangeMatch, this,
				ConflictPackage.MULTI_CHANGE_CONFLICT__TARGET_CHANGES)
		}
		return targetChanges
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case ConflictPackage.MULTI_CHANGE_CONFLICT__SOURCE_CHANGES: {
				return getSourceChanges()
			}
			case ConflictPackage.MULTI_CHANGE_CONFLICT__TARGET_CHANGES: {
				return getTargetChanges()
			}
		}
		return super.eGet(featureID, resolve, coreType)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override void eSet(int featureID, Object newValue) {

		switch (featureID) {
			case ConflictPackage.MULTI_CHANGE_CONFLICT__SOURCE_CHANGES: {
				getSourceChanges().clear()
				getSourceChanges().addAll((newValue as Collection<? extends ChangeMatch>))
				return
			}
			case ConflictPackage.MULTI_CHANGE_CONFLICT__TARGET_CHANGES: {
				getTargetChanges().clear()
				getTargetChanges().addAll((newValue as Collection<? extends ChangeMatch>))
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
			case ConflictPackage.MULTI_CHANGE_CONFLICT__SOURCE_CHANGES: {
				getSourceChanges().clear()
				return
			}
			case ConflictPackage.MULTI_CHANGE_CONFLICT__TARGET_CHANGES: {
				getTargetChanges().clear()
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
			case ConflictPackage.MULTI_CHANGE_CONFLICT__SOURCE_CHANGES: {
				return sourceChanges !== null && !sourceChanges.isEmpty()
			}
			case ConflictPackage.MULTI_CHANGE_CONFLICT__TARGET_CHANGES: {
				return targetChanges !== null && !targetChanges.isEmpty()
			}
		}
		return super.eIsSet(featureID)
	} // MultiChangeConflictImpl
}

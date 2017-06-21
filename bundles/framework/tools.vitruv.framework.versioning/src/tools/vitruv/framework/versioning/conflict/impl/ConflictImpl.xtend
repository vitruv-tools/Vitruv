/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import java.lang.reflect.InvocationTargetException
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.ConflictSolvability
import tools.vitruv.framework.versioning.conflict.ConflictType
import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl#getType <em>Type</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl#getSolvability <em>Solvability</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.conflict.impl.ConflictImpl#getOriginalChangesLevenshteinDistance <em>Original Changes Levenshtein Distance</em>}</li>
 * </ul>
 * @generated
 */
abstract class ConflictImpl extends MinimalEObjectImpl.Container implements Conflict {

	/** 
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ConflictType TYPE_EDEFAULT = ConflictType.NAMING
	/** 
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ConflictType type = TYPE_EDEFAULT
	/** 
	 * The default value of the '{@link #getSolvability() <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolvability()
	 * @generated
	 * @ordered
	 */
	protected static final ConflictSolvability SOLVABILITY_EDEFAULT = ConflictSolvability.AUTOMATICALLY
	/** 
	 * The cached value of the '{@link #getSolvability() <em>Solvability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolvability()
	 * @generated
	 * @ordered
	 */
	protected ConflictSolvability solvability = SOLVABILITY_EDEFAULT
	/** 
	 * The default value of the '{@link #getOriginalChangesLevenshteinDistance() <em>Original Changes Levenshtein Distance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalChangesLevenshteinDistance()
	 * @generated
	 * @ordered
	 */
	protected static final int ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE_EDEFAULT = 0
	/** 
	 * The cached value of the '{@link #getOriginalChangesLevenshteinDistance() <em>Original Changes Levenshtein Distance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalChangesLevenshteinDistance()
	 * @generated
	 * @ordered
	 */
	protected int originalChangesLevenshteinDistance = ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE_EDEFAULT
	@Accessors(PUBLIC_GETTER)
	protected Graph eChangeDependencyGraph

	override setEChangeDependencyGraph(Graph newGraph) {
		var oldType = eChangeDependencyGraph
		eChangeDependencyGraph = if (newGraph === null) new SingleGraph("") else newGraph
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__TYPE, oldType,
				eChangeDependencyGraph))
	}

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
		return ConflictPackage.Literals.CONFLICT
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictType getType() {
		return type
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setType(ConflictType newType) {
		var ConflictType oldType = type
		type = if (newType === null) TYPE_EDEFAULT else newType
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__TYPE, oldType, type))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictSolvability getSolvability() {
		return solvability
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setSolvability(ConflictSolvability newSolvability) {
		var ConflictSolvability oldSolvability = solvability
		solvability = if (newSolvability === null) SOLVABILITY_EDEFAULT else newSolvability
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification.SET, ConflictPackage.CONFLICT__SOLVABILITY, oldSolvability,
					solvability))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override int getOriginalChangesLevenshteinDistance() {
		return originalChangesLevenshteinDistance
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setOriginalChangesLevenshteinDistance(int newOriginalChangesLevenshteinDistance) {
		var int oldOriginalChangesLevenshteinDistance = originalChangesLevenshteinDistance
		originalChangesLevenshteinDistance = newOriginalChangesLevenshteinDistance
		if (eNotificationRequired())
			eNotify(
				new ENotificationImpl(this, Notification.SET,
					ConflictPackage.CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE,
					oldOriginalChangesLevenshteinDistance, originalChangesLevenshteinDistance))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches,
		EList<ChangeMatch> rejectedRemoteOperations) {
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
			case ConflictPackage.CONFLICT__TYPE: {
				return getType()
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				return getSolvability()
			}
			case ConflictPackage.CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE: {
				return getOriginalChangesLevenshteinDistance()
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
			case ConflictPackage.CONFLICT__TYPE: {
				setType((newValue as ConflictType))
				return
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				setSolvability((newValue as ConflictSolvability))
				return
			}
			case ConflictPackage.CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE: {
				setOriginalChangesLevenshteinDistance((newValue as Integer))
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
			case ConflictPackage.CONFLICT__TYPE: {
				setType(TYPE_EDEFAULT)
				return
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				setSolvability(SOLVABILITY_EDEFAULT)
				return
			}
			case ConflictPackage.CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE: {
				setOriginalChangesLevenshteinDistance(ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE_EDEFAULT)
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
			case ConflictPackage.CONFLICT__TYPE: {
				return type !== TYPE_EDEFAULT
			}
			case ConflictPackage.CONFLICT__SOLVABILITY: {
				return solvability !== SOLVABILITY_EDEFAULT
			}
			case ConflictPackage.CONFLICT__ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE: {
				return originalChangesLevenshteinDistance !== ORIGINAL_CHANGES_LEVENSHTEIN_DISTANCE_EDEFAULT
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	override Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {

		switch (operationID) {
			case ConflictPackage.CONFLICT___RESOLVE_CONFLICT__ELIST_ELIST: {
				resolveConflict((arguments.get(0) as EList<ChangeMatch>), (arguments.get(1) as EList<ChangeMatch>))
				return null
			}
		}
		return super.eInvoke(operationID, arguments)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String toString() {
		if (eIsProxy()) return super.toString()
		var StringBuffer result = new StringBuffer(super.toString())
		result.append(" (type: ")
		result.append(type)
		result.append(", solvability: ")
		result.append(solvability)
		result.append(", originalChangesLevenshteinDistance: ")
		result.append(originalChangesLevenshteinDistance)
		result.append(Character.valueOf(')').charValue)
		return result.toString()
	} // ConflictImpl
}

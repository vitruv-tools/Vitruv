/** 
 */
package tools.vitruv.framework.versioning.conflict.util

import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.conflict.ConflictDetector
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.MultiChangeConflict
import tools.vitruv.framework.versioning.conflict.SimpleChangeConflict

/** 
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see ConflictPackage
 * @generated
 */
class ConflictAdapterFactory extends AdapterFactoryImpl {
	/** 
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConflictPackage modelPackage

	/** 
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = ConflictPackage::eINSTANCE
		}
	}

	/** 
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	override boolean isFactoryForType(Object object) {
		if (object === modelPackage) {
			return true
		}
		if (object instanceof EObject) {
			return object.eClass().getEPackage() === modelPackage
		}
		return false
	}

	/** 
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConflictSwitch<Adapter> modelSwitch = new ConflictSwitch<Adapter>() {
		override Adapter caseSimpleChangeConflict(SimpleChangeConflict object) {
			return createSimpleChangeConflictAdapter()
		}

		override Adapter caseMultiChangeConflict(MultiChangeConflict object) {
			return createMultiChangeConflictAdapter()
		}

		override Adapter caseConflictDetector(ConflictDetector object) {
			return createConflictDetectorAdapter()
		}

		override Adapter caseConflict(Conflict object) {
			return createConflictAdapter()
		}

		override Adapter defaultCase(EObject object) {
			return createEObjectAdapter()
		}
	}

	/** 
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	override Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((target as EObject))
	}

	/** 
	 * Creates a new adapter for an object of class '{@link SimpleChangeConflict <em>Simple Change Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SimpleChangeConflict
	 * @generated
	 */
	def Adapter createSimpleChangeConflictAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link MultiChangeConflict <em>Multi Change Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MultiChangeConflict
	 * @generated
	 */
	def Adapter createMultiChangeConflictAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link ConflictDetector <em>Detector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ConflictDetector
	 * @generated
	 */
	def Adapter createConflictDetectorAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Conflict <em>Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Conflict
	 * @generated
	 */
	def Adapter createConflictAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	def Adapter createEObjectAdapter() {
		return null
	} // ConflictAdapterFactory
}

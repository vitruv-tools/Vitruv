/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage
 * @generated
 */
public interface FeatureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeatureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Unset EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unset EAttribute</em>'.
	 * @generated
	 */
	<T extends Object> UnsetEAttribute<T> createUnsetEAttribute();

	/**
	 * Returns a new object of class '<em>Unset Non Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unset Non Containment EReference</em>'.
	 * @generated
	 */
	<T extends EObject> UnsetNonContainmentEReference<T> createUnsetNonContainmentEReference();

	/**
	 * Returns a new object of class '<em>Unset Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unset Containment EReference</em>'.
	 * @generated
	 */
	<T extends EObject> UnsetContainmentEReference<T> createUnsetContainmentEReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeaturePackage getFeaturePackage();

} //FeatureFactory

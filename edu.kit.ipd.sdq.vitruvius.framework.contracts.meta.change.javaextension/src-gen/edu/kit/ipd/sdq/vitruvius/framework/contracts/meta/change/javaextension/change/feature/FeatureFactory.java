/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature.FeaturePackage
 * @generated
 */
public interface FeatureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature.impl.FeatureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Java Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Feature EChange</em>'.
	 * @generated
	 */
	<A extends EObject, F extends EStructuralFeature> JavaFeatureEChange<A, F> createJavaFeatureEChange();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeaturePackage getFeaturePackage();

} //FeatureFactory

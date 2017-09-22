/**
 */
package tools.vitruv.framework.change.echange.feature;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage
 * @generated
 */
public interface FeatureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FeatureFactory eINSTANCE = tools.vitruv.framework.change.echange.feature.impl.FeatureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Unset Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unset Feature</em>'.
	 * @generated
	 */
	<A extends EObject, F extends EStructuralFeature> UnsetFeature<A, F> createUnsetFeature();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FeaturePackage getFeaturePackage();

} //FeatureFactory

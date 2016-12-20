/**
 */
package tools.vitruv.framework.change.echange.feature.single;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.feature.single.SinglePackage
 * @generated
 */
public interface SingleFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SingleFactory eINSTANCE = tools.vitruv.framework.change.echange.feature.single.impl.SingleFactoryImpl.init();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SinglePackage getSinglePackage();

} //SingleFactory

/** 
 */
package tools.vitruv.framework.versioning.impl

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EFactoryImpl
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.framework.versioning.Root
import tools.vitruv.framework.versioning.VersioningFactory
import tools.vitruv.framework.versioning.VersioningPackage

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class VersioningFactoryImpl extends EFactoryImpl implements VersioningFactory {
	/** 
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def static VersioningFactory init() {
		try {
			var VersioningFactory theVersioningFactory = (EPackage.Registry::INSTANCE.getEFactory(
				VersioningPackage::eNS_URI) as VersioningFactory)
			if (theVersioningFactory !== null) {
				return theVersioningFactory
			}
		} catch (Exception exception) {
			EcorePlugin::INSTANCE.log(exception)
		}

		return new VersioningFactoryImpl()
	}

	/** 
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EObject create(EClass eClass) {

		switch (eClass.getClassifierID()) {
			case VersioningPackage::ROOT: {
				return createRoot()
			}
			default: {
				throw new IllegalArgumentException(
					'''The class '«»«eClass.getName()»' is not a valid classifier'''.toString);
			}
		}
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Root createRoot() {
		var RootImpl root = new RootImpl()
		return root
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override VersioningPackage getVersioningPackage() {
		return (getEPackage() as VersioningPackage)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	def static VersioningPackage getPackage() {
		return VersioningPackage::eINSTANCE
	} // VersioningFactoryImpl
}

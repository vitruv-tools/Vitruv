/** 
 */
package tools.vitruv.framework.versioning.repository.impl

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EFactoryImpl
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.framework.versioning.repository.Repository
import tools.vitruv.framework.versioning.repository.RepositoryFactory
import tools.vitruv.framework.versioning.repository.RepositoryPackage
import tools.vitruv.framework.versioning.repository.Tag

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class RepositoryFactoryImpl extends EFactoryImpl implements RepositoryFactory {
	/** 
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def static RepositoryFactory init() {
		try {
			var RepositoryFactory theRepositoryFactory = (EPackage.Registry::INSTANCE.getEFactory(
				RepositoryPackage::eNS_URI) as RepositoryFactory)
			if (theRepositoryFactory !== null) {
				return theRepositoryFactory
			}
		} catch (Exception exception) {
			EcorePlugin::INSTANCE.log(exception)
		}

		return new RepositoryFactoryImpl()
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
			case RepositoryPackage::TAG: {
				return createTag()
			}
			case RepositoryPackage::REPOSITORY: {
				return createRepository()
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
	override Tag createTag() {
		var TagImpl tag = new TagImpl()
		return tag
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Repository createRepository() {
		var RepositoryImpl repository = new RepositoryImpl()
		return repository
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override RepositoryPackage getRepositoryPackage() {
		return (getEPackage() as RepositoryPackage)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	def static RepositoryPackage getPackage() {
		return RepositoryPackage::eINSTANCE
	} // RepositoryFactoryImpl
}

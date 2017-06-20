/** 
 */
package tools.vitruv.framework.versioning.author.impl

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EFactoryImpl
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorFactory
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class AuthorFactoryImpl extends EFactoryImpl implements AuthorFactory {
	/** 
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def static AuthorFactory init() {
		try {
			var AuthorFactory theAuthorFactory = (EPackage.Registry::INSTANCE.getEFactory(
				AuthorPackage::eNS_URI) as AuthorFactory)
			if (theAuthorFactory !== null) {
				return theAuthorFactory
			}
		} catch (Exception exception) {
			EcorePlugin::INSTANCE.log(exception)
		}

		return new AuthorFactoryImpl()
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
			case AuthorPackage::AUTHOR: {
				return createAuthor()
			}
			case AuthorPackage::SIGNATURE: {
				return createSignature()
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
	override Author createAuthor() {
		var AuthorImpl author = new AuthorImpl()
		return author
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Signature createSignature() {
		var SignatureImpl signature = new SignatureImpl()
		return signature
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override AuthorPackage getAuthorPackage() {
		return (getEPackage() as AuthorPackage)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	def static AuthorPackage getPackage() {
		return AuthorPackage::eINSTANCE
	} // AuthorFactoryImpl
}

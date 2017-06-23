/** 
 */
package tools.vitruv.framework.versioning.author.util

import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.author.AuthorPackage
import tools.vitruv.framework.versioning.author.Signature
import tools.vitruv.framework.versioning.author.Signed

/** 
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see AuthorPackage
 * @generated
 */
class AuthorAdapterFactory extends AdapterFactoryImpl {
	/** 
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AuthorPackage modelPackage

	/** 
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = AuthorPackage::eINSTANCE
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
	protected AuthorSwitch<Adapter> modelSwitch = new AuthorSwitch<Adapter>() {
		override Adapter caseAuthor(Author object) {
			return createAuthorAdapter()
		}

		override Adapter caseSignature(Signature object) {
			return createSignatureAdapter()
		}

		override Adapter caseSigned(Signed object) {
			return createSignedAdapter()
		}

		override Adapter caseNamed(Named object) {
			return createNamedAdapter()
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
	 * Creates a new adapter for an object of class '{@link Author <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Author
	 * @generated
	 */
	def Adapter createAuthorAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Signature
	 * @generated
	 */
	def Adapter createSignatureAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Signed <em>Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Signed
	 * @generated
	 */
	def Adapter createSignedAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Named
	 * @generated
	 */
	def Adapter createNamedAdapter() {
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
	} // AuthorAdapterFactory
}

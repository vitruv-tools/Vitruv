/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Explicit Unset EFeature</b></em>'.
 * <!-- end-user-doc -->
 *
<<<<<<< HEAD
 * <!-- begin-model-doc -->
 * *
 * EChange which unsets a single or many valued attribute.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEFeature()
 * @model ABounds="tools.vitruv.framework.change.echange.compound.EObj" TBounds="org.eclipse.emf.ecore.EJavaObject"
=======
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedFeature <em>Affected Feature</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEFeature()
 * @model abstract="true"
>>>>>>> remotes/origin/master
 * @generated
 */
public interface ExplicitUnsetEFeature<A extends EObject, F extends EStructuralFeature> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affected EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affected EObject</em>' reference.
	 * @see #setAffectedEObject(EObject)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEFeature_AffectedEObject()
	 * @model required="true"
	 * @generated
	 */
	A getAffectedEObject();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedEObject <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected EObject</em>' reference.
	 * @see #getAffectedEObject()
	 * @generated
	 */
	void setAffectedEObject(A value);

	/**
	 * Returns the value of the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affected Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affected Feature</em>' reference.
	 * @see #setAffectedFeature(EStructuralFeature)
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEFeature_AffectedFeature()
	 * @model required="true"
	 * @generated
	 */
	F getAffectedFeature();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature#getAffectedFeature <em>Affected Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected Feature</em>' reference.
	 * @see #getAffectedFeature()
	 * @generated
	 */
	void setAffectedFeature(F value);

} // ExplicitUnsetEFeature

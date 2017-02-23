/**
 */
package tools.vitruv.framework.change.echange.feature;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObject <em>Affected EObject</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.feature.EObj" FBounds="tools.vitruv.framework.change.echange.feature.EFeat"
 * @generated
 */
public interface FeatureEChange<A extends EObject, F extends EStructuralFeature> extends AtomicEChange {
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
	 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange_AffectedFeature()
	 * @model kind="reference" required="true"
	 * @generated
	 */
	F getAffectedFeature();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedFeature <em>Affected Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected Feature</em>' reference.
	 * @see #getAffectedFeature()
	 * @generated
	 */
	void setAffectedFeature(F value);

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
	 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange_AffectedEObject()
	 * @model kind="reference" required="true"
	 * @generated
	 */
	A getAffectedEObject();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObject <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected EObject</em>' reference.
	 * @see #getAffectedEObject()
	 * @generated
	 */
	void setAffectedEObject(A value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (((!<%com.google.common.base.Objects%>.equal(this.getAffectedEObject(), null)) && (!this.getAffectedEObject().eIsProxy())) && (!<%com.google.common.base.Objects%>.equal(this.getAffectedFeature(), null)));'"
	 * @generated
	 */
	boolean isResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" resourceSetDataType="tools.vitruv.framework.change.echange.feature.ResourceSet" resourceSetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isResolved = this.isResolved();\nboolean _not = (!_isResolved);\nif (_not)\n{\n\t<%tools.vitruv.framework.change.echange.EChange%> _resolve = super.resolve(resourceSet);\n\tfinal <%tools.vitruv.framework.change.echange.feature.FeatureEChange%><A, F> resolvedChange = ((<%tools.vitruv.framework.change.echange.feature.FeatureEChange%><A, F>) _resolve);\n\tif (((<%com.google.common.base.Objects%>.equal(resolvedChange, null) || <%com.google.common.base.Objects%>.equal(this.getAffectedFeature(), null)) || <%com.google.common.base.Objects%>.equal(this.getAffectedEObject(), null)))\n\t{\n\t\treturn null;\n\t}\n\tA _affectedEObject = this.getAffectedEObject();\n\t<%org.eclipse.emf.ecore.EObject%> _resolveProxy = <%tools.vitruv.framework.change.echange.util.EChangeUtil%>.resolveProxy(_affectedEObject, resourceSet);\n\tresolvedChange.setAffectedEObject(((A) _resolveProxy));\n\tif (((!<%com.google.common.base.Objects%>.equal(resolvedChange.getAffectedEObject(), null)) && (!resolvedChange.getAffectedEObject().eIsProxy())))\n\t{\n\t\treturn resolvedChange;\n\t}\n}\nreturn this;'"
	 * @generated
	 */
	EChange resolve(ResourceSet resourceSet);

} // FeatureEChange

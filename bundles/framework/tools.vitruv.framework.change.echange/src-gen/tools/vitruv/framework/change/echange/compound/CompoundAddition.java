/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.AtomicEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Addition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CompoundAddition#getAdditiveChanges <em>Additive Changes</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCompoundAddition()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface CompoundAddition<T extends Object, S extends AdditiveEChange<T>> extends CompoundEChange {
	/**
	 * Returns the value of the '<em><b>Additive Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additive Changes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additive Changes</em>' containment reference list.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCompoundAddition_AdditiveChanges()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<S> getAdditiveChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> result = new <%org.eclipse.emf.common.util.BasicEList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>();\n<%org.eclipse.emf.common.util.EList%><S> _additiveChanges = this.getAdditiveChanges();\nresult.addAll(_additiveChanges);\nreturn result;'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model resourceSetDataType="tools.vitruv.framework.change.echange.compound.ResourceSet" resourceSetUnique="false" applyChangeUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (applyChange)\n{\n\tfor (int i = 0; (i < this.getAdditiveChanges().size()); i++)\n\t{\n\t\t<%org.eclipse.emf.common.util.EList%><S> _additiveChanges = this.getAdditiveChanges();\n\t\t<%org.eclipse.emf.common.util.EList%><S> _additiveChanges_1 = this.getAdditiveChanges();\n\t\tS _get = _additiveChanges_1.get(i);\n\t\t<%tools.vitruv.framework.change.echange.EChange%> _resolveApply = _get.resolveApply(resourceSet);\n\t\t_additiveChanges.set(i, ((S) _resolveApply));\n\t}\n}\nelse\n{\n\tfor (int i = (this.getAdditiveChanges().size() - 1); (i >= 0); i--)\n\t{\n\t\t<%org.eclipse.emf.common.util.EList%><S> _additiveChanges = this.getAdditiveChanges();\n\t\t<%org.eclipse.emf.common.util.EList%><S> _additiveChanges_1 = this.getAdditiveChanges();\n\t\tS _get = _additiveChanges_1.get(i);\n\t\t<%tools.vitruv.framework.change.echange.EChange%> _resolveRevert = _get.resolveRevert(resourceSet);\n\t\t_additiveChanges.set(i, ((S) _resolveRevert));\n\t}\n}'"
	 * @generated
	 */
	void resolveAtomicChanges(ResourceSet resourceSet, boolean applyChange);

} // CompoundAddition

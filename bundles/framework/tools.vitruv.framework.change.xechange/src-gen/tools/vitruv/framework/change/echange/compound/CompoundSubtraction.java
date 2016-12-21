/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.SubtractiveEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtraction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.CompoundSubtraction#getSubtractiveChanges <em>Subtractive Changes</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCompoundSubtraction()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface CompoundSubtraction<T extends Object> extends SubtractiveEChange<T> {
	/**
	 * Returns the value of the '<em><b>Subtractive Changes</b></em>' containment reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.SubtractiveEChange}&lt;T>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subtractive Changes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subtractive Changes</em>' containment reference list.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCompoundSubtraction_SubtractiveChanges()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<SubtractiveEChange<T>> getSubtractiveChanges();

} // CompoundSubtraction

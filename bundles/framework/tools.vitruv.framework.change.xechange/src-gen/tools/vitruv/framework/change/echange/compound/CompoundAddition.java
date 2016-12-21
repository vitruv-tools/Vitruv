/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.echange.AdditiveEChange;

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
public interface CompoundAddition<T extends Object> extends AdditiveEChange<T> {
	/**
	 * Returns the value of the '<em><b>Additive Changes</b></em>' containment reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.AdditiveEChange}&lt;T>.
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
	EList<AdditiveEChange<T>> getAdditiveChanges();

} // CompoundAddition

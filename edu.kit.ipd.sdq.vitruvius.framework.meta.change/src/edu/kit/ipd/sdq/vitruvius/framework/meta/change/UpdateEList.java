/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change;

import org.eclipse.emf.ecore.EStructuralFeature;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEList#getIndex <em>Index</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEList#getUpdate <em>Update</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage#getUpdateEList()
 * @model abstract="true"
 * @generated
 */
public interface UpdateEList<T extends EStructuralFeature> extends EFeatureChange<T> {
    /**
     * Returns the value of the '<em><b>Index</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Index</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Index</em>' attribute.
     * @see #setIndex(int)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage#getUpdateEList_Index()
     * @model default="0" required="true"
     * @generated
     */
    int getIndex();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEList#getIndex <em>Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Index</em>' attribute.
     * @see #getIndex()
     * @generated
     */
    void setIndex(int value);

    /**
     * Returns the value of the '<em><b>Update</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Update</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Update</em>' attribute.
     * @see #setUpdate(Object)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage#getUpdateEList_Update()
     * @model
     * @generated
     */
    Object getUpdate();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEList#getUpdate <em>Update</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Update</em>' attribute.
     * @see #getUpdate()
     * @generated
     */
    void setUpdate(Object value);

} // UpdateEList

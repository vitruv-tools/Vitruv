/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert In EList</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class InsertInEListImpl<T extends Object> extends UpdateSingleEListEntryImpl<T> implements InsertInEList<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected InsertInEListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ListPackage.Literals.INSERT_IN_ELIST;
    }

} //InsertInEListImpl

/**
 */
package tools.vitruvius.framework.change.echange.feature.list.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruvius.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruvius.framework.change.echange.feature.list.ListPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Insert In List EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class InsertInListEChangeImpl<A extends EObject, F extends EStructuralFeature> extends UpdateSingleListEntryEChangeImpl<A, F> implements InsertInListEChange<A, F> {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected InsertInListEChangeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ListPackage.Literals.INSERT_IN_LIST_ECHANGE;
	}

} //InsertInListEChangeImpl

/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.SubtractiveEReferenceChangeImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RemoveRootEObjectImpl<T extends EObject> extends SubtractiveEReferenceChangeImpl<T> implements RemoveRootEObject<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RemoveRootEObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RootPackage.Literals.REMOVE_ROOT_EOBJECT;
    }

} //RemoveRootEObjectImpl

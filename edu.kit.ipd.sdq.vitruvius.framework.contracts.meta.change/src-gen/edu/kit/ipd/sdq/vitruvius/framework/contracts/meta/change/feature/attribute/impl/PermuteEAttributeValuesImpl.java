/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.PermuteListEChangeImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permute EAttribute Values</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PermuteEAttributeValuesImpl<A extends EObject> extends PermuteListEChangeImpl<A, EAttribute> implements PermuteEAttributeValues<A> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PermuteEAttributeValuesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AttributePackage.Literals.PERMUTE_EATTRIBUTE_VALUES;
    }

} //PermuteEAttributeValuesImpl

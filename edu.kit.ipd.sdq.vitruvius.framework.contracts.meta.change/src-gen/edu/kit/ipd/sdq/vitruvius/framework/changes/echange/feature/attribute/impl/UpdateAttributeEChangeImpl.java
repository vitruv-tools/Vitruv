/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.impl;

import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.AttributePackage;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.UpdateAttributeEChange;

import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.impl.FeatureEChangeImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update Attribute EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class UpdateAttributeEChangeImpl<A extends EObject> extends FeatureEChangeImpl<A, EAttribute> implements UpdateAttributeEChange<A> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UpdateAttributeEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AttributePackage.Literals.UPDATE_ATTRIBUTE_ECHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * This is specialized for the more specific type known in this context.
     * @generated
     */
    @Override
    public void setAffectedFeature(EAttribute newAffectedFeature) {
        super.setAffectedFeature(newAffectedFeature);
    }

} //UpdateAttributeEChangeImpl

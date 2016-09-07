/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.PermuteEAttributeValues;
import tools.vitruv.framework.change.echange.feature.list.impl.PermuteListEChangeImpl;

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

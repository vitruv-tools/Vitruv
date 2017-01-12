/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;

import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Explicit Unset EFeature</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ExplicitUnsetEFeatureImpl<A extends EObject, T extends Object> extends CompoundSubtractionImpl<T, SubtractiveAttributeEChange<A, T>> implements ExplicitUnsetEFeature<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExplicitUnsetEFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.EXPLICIT_UNSET_EFEATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<SubtractiveAttributeEChange<A, T>> getSubtractiveChanges() {
		if (subtractiveChanges == null) {
			subtractiveChanges = new EObjectContainmentEList<SubtractiveAttributeEChange<A, T>>(SubtractiveAttributeEChange.class, this, CompoundPackage.EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES);
		}
		return subtractiveChanges;
	}

} //ExplicitUnsetEFeatureImpl

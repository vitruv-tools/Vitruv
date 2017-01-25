/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		EList<SubtractiveAttributeEChange<A, T>> _subtractiveChanges = this.getSubtractiveChanges();
		final Function1<SubtractiveAttributeEChange<A, T>, AtomicEChange> _function = new Function1<SubtractiveAttributeEChange<A, T>, AtomicEChange>() {
			public AtomicEChange apply(final SubtractiveAttributeEChange<A, T> it) {
				return it;
			}
		};
		return XcoreEListExtensions.<SubtractiveAttributeEChange<A, T>, AtomicEChange>map(_subtractiveChanges, _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == CompoundEChange.class) {
			switch (baseOperationID) {
				case CompoundPackage.COMPOUND_ECHANGE___GET_ATOMIC_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == CompoundSubtraction.class) {
			switch (baseOperationID) {
				case CompoundPackage.COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES: return CompoundPackage.EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExplicitUnsetEFeatureImpl

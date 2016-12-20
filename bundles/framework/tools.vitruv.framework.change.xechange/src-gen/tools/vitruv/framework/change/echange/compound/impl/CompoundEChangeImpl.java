/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;

import tools.vitruv.framework.change.echange.impl.EChangeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class CompoundEChangeImpl extends EChangeImpl implements CompoundEChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompoundEChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.COMPOUND_ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.COMPOUND_ECHANGE___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //CompoundEChangeImpl

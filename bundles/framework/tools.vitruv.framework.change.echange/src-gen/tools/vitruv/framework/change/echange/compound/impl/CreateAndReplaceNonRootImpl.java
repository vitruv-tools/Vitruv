/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot;

import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create And Replace Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CreateAndReplaceNonRootImpl<A extends EObject, T extends EObject> extends CreateAndInsertEObjectImpl<T, ReplaceSingleValuedEReference<A, T>> implements CreateAndReplaceNonRoot<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateAndReplaceNonRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.CREATE_AND_REPLACE_NON_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetInsertChange(ReplaceSingleValuedEReference<A, T> newInsertChange, NotificationChain msgs) {
		return super.basicSetInsertChange(newInsertChange, msgs);
	}

} //CreateAndReplaceNonRootImpl

/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;

import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create And Insert Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CreateAndInsertNonRootImpl<A extends EObject, T extends EObject> extends CreateAndInsertEObjectImpl<T, InsertEReference<A, T>> implements CreateAndInsertNonRoot<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateAndInsertNonRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.CREATE_AND_INSERT_NON_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetInsertChange(InsertEReference<A, T> newInsertChange, NotificationChain msgs) {
		return super.basicSetInsertChange(newInsertChange, msgs);
	}

} //CreateAndInsertNonRootImpl

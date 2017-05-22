/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot;

import tools.vitruv.framework.change.echange.root.InsertRootEObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create And Insert Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CreateAndInsertRootImpl<T extends EObject> extends CreateAndInsertEObjectImpl<T, InsertRootEObject<T>> implements CreateAndInsertRoot<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateAndInsertRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.CREATE_AND_INSERT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetInsertChange(InsertRootEObject<T> newInsertChange, NotificationChain msgs) {
		return super.basicSetInsertChange(newInsertChange, msgs);
	}

} //CreateAndInsertRootImpl

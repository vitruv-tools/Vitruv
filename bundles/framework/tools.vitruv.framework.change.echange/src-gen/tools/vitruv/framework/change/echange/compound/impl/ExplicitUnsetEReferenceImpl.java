/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import com.google.common.collect.Iterables;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Explicit Unset EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEReferenceImpl#getChanges <em>Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExplicitUnsetEReferenceImpl<A extends EObject> extends ExplicitUnsetEFeatureImpl<A, EReference> implements ExplicitUnsetEReference<A> {
	/**
	 * The cached value of the '{@link #getChanges() <em>Changes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<EChange> changes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExplicitUnsetEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.EXPLICIT_UNSET_EREFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setAffectedFeature(EReference newAffectedFeature) {
		super.setAffectedFeature(newAffectedFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EChange> getChanges() {
		if (changes == null) {
			changes = new EObjectContainmentEList<EChange>(EChange.class, this, CompoundPackage.EXPLICIT_UNSET_EREFERENCE__CHANGES);
		}
		return changes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EChange> getContainedChanges() {
		return this.getChanges();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AtomicEChange> getAtomicChanges() {
		EList<EChange> _changes = this.getChanges();
		final Function1<EChange, List<AtomicEChange>> _function = new Function1<EChange, List<AtomicEChange>>() {
			public List<AtomicEChange> apply(final EChange it) {
				List<AtomicEChange> _xifexpression = null;
				if ((it instanceof AtomicEChange)) {
					_xifexpression = java.util.Collections.<AtomicEChange>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<AtomicEChange>newArrayList(((AtomicEChange)it)));
				}
				else {
					EList<AtomicEChange> _xifexpression_1 = null;
					if ((it instanceof CompoundEChange)) {
						_xifexpression_1 = ((CompoundEChange)it).getAtomicChanges();
					}
					else {
						throw new IllegalArgumentException();
					}
					_xifexpression = _xifexpression_1;
				}
				return _xifexpression;
			}
		};
		List<List<AtomicEChange>> _map = ListExtensions.<EChange, List<AtomicEChange>>map(_changes, _function);
		Iterable<AtomicEChange> _flatten = Iterables.<AtomicEChange>concat(_map);
		return ECollections.<AtomicEChange>asEList(((AtomicEChange[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(_flatten, AtomicEChange.class)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE__CHANGES:
				return ((InternalEList<?>)getChanges()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE__CHANGES:
				return getChanges();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE__CHANGES:
				getChanges().clear();
				getChanges().addAll((Collection<? extends EChange>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE__CHANGES:
				getChanges().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE__CHANGES:
				return changes != null && !changes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE___GET_CONTAINED_CHANGES:
				return getContainedChanges();
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE___GET_ATOMIC_CHANGES:
				return getAtomicChanges();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExplicitUnsetEReferenceImpl

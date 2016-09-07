/**
 */
package tools.vitruvius.framework.change.echange.compound.impl;

import tools.vitruvius.framework.change.echange.AdditiveEChange;
import tools.vitruvius.framework.change.echange.SubtractiveEChange;
import tools.vitruvius.framework.change.echange.compound.*;
import tools.vitruvius.framework.change.echange.compound.impl.MoveEObjectImpl;
import tools.vitruvius.framework.change.echange.compound.impl.ReplaceInEListImpl;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruvius.framework.change.echange.feature.list.RemoveFromListEChange;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompoundFactoryImpl extends EFactoryImpl implements CompoundFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static CompoundFactory init() {
		try {
			CompoundFactory theCompoundFactory = (CompoundFactory)EPackage.Registry.INSTANCE.getEFactory(CompoundPackage.eNS_URI);
			if (theCompoundFactory != null) {
				return theCompoundFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompoundFactoryImpl();
	}

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public CompoundFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CompoundPackage.MOVE_EOBJECT: return createMoveEObject();
			case CompoundPackage.REPLACE_IN_ELIST: return createReplaceInEList();
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE: return createExplicitUnsetEFeature();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject, B extends EObject, T extends EObject> MoveEObject<A, B, T> createMoveEObject() {
		MoveEObjectImpl<A, B, T> moveEObject = new MoveEObjectImpl<A, B, T>();
		return moveEObject;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromListEChange<A, F> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F> & FeatureEChange<A, F> & AdditiveEChange<T>> ReplaceInEList<A, F, T, R, I> createReplaceInEList() {
		ReplaceInEListImpl<A, F, T, R, I> replaceInEList = new ReplaceInEListImpl<A, F, T, R, I>();
		return replaceInEList;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject, F extends EStructuralFeature, T extends Object, S extends FeatureEChange<A, F> & SubtractiveEChange<T>> ExplicitUnsetEFeature<A, F, T, S> createExplicitUnsetEFeature() {
		ExplicitUnsetEFeatureImpl<A, F, T, S> explicitUnsetEFeature = new ExplicitUnsetEFeatureImpl<A, F, T, S>();
		return explicitUnsetEFeature;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public CompoundPackage getCompoundPackage() {
		return (CompoundPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static CompoundPackage getPackage() {
		return CompoundPackage.eINSTANCE;
	}

} //CompoundFactoryImpl
